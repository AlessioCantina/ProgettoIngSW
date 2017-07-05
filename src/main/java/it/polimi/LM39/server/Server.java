package it.polimi.LM39.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

import it.polimi.LM39.credentials.Hash;



/**
 * server class which starts both rmi and socket servers
 */
public class Server implements ServerInterface{
	
	/* network and game constants */
	public static final Integer ROOM_CAPACITY = 4;
	public static final Integer SOCKET_PORT = 3421;
	public static final Integer RMI_PORT = 3334;
	/* lock to handle multiple thread */ 
    private static Object ROOM_LOCK = new Object();
    private SocketServer socketServer;
    private HashMap<String,NetworkPlayer> players;
    private ArrayList<Room> rooms;   
    private RMIServer rmiServer;  //TODO rmi
    
    /*
     * initialize the players hashmap and room arraylist
     * then it create the server's object
     */
    public Server() {
    	players = new HashMap<>();
    	rooms = new ArrayList<>();
    	socketServer = new SocketServer(this);
    }
    /*
     * main which start the servers TODO: rmi
     */
    public static void main(String[] args){
         Server server = new Server();
         server.StartServer();
    }
    /*
     * starts the thread
     */
    private void StartServer(){
    	System.out.println("SocketServer Creato");
    	socketServer.StartServer(SOCKET_PORT);
    }

    /*
     * add player to the hashmap
     * 
     */
	@Override
	public Boolean loginPlayer(String nickName,String password,NetworkPlayer networkPlayer) throws IOException {
		if (!players.containsKey(nickName)){
			this.joinRoom(networkPlayer);
			players.put(nickName, networkPlayer);
			networkPlayer.setNickName(nickName);
			Hash.register(nickName, password);
			return false;
		}
		else if(Hash.login(nickName, password)){
			SocketPlayer player = ((SocketPlayer)players.get(nickName));
			if(player.getSocket().isClosed()){
				SocketPlayer newPlayer = (SocketPlayer)networkPlayer;
				player.resetConnection(newPlayer.getSocket(), newPlayer.getOutputStream(), newPlayer.getInputStream());
			}
			if(player.getIdleStatus()){
				player.getSocket().close();
				SocketPlayer newPlayer = (SocketPlayer)networkPlayer;
				player.resetConnection(newPlayer.getSocket(), newPlayer.getOutputStream(), newPlayer.getInputStream());
				player.getOutputStream().writeLong(Room.playerMoveTimeout);
				player.getOutputStream().flush();
				player.clientAction = "";
				player.setIdleStatus(false);
				SocketPlayer.disconnectedPlayers--;
			}
		return true;
		}else{
			((SocketPlayer)networkPlayer).getOutputStream().writeLong(Room.playerMoveTimeout);
			((SocketPlayer)networkPlayer).getOutputStream().flush();
			((SocketPlayer)networkPlayer).setMessage("Wrong password, please reconnect");
		}
		return false;
	}

	/*
	 * return the networkplayer object using the nickname
	 */
	@Override
	public NetworkPlayer getPlayer(String nickName) {
		return players.get(nickName);
	}

	@Override
	/*
	 * this method create the room if needed and call the method to add the player to the room
	 * the ROOM_LOCK avoid multiple room creation from different thread	 
	 */
	public void joinRoom(NetworkPlayer networkPlayer) {
		synchronized(ROOM_LOCK){ 
			if(Room.roomCounter == 0)
			{
				Room room = new Room();
				this.rooms.add(room);
				room.addPlayer(networkPlayer);
			}
			else
			{
				Room room = this.getRoom(Room.roomCounter - 1);
				if(room.getConnectedPlayers() == ROOM_CAPACITY || room.getRoomState())
				{
					Room newRoom = new Room();
					this.rooms.add(newRoom);
					newRoom.addPlayer(networkPlayer);
				}
				else{
					room.addPlayer(networkPlayer);
				}
			}
			ROOM_LOCK.notifyAll();
		}
	}
	/*
	 * return the room object using the roomnumber provided	 
	 */
	public Room getRoom(Integer roomNumber){
		return this.rooms.get(roomNumber);
	}
}