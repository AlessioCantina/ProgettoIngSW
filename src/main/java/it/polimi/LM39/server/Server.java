package it.polimi.LM39.server;

import java.io.IOException;
import java.util.*;

import it.polimi.LM39.credentials.Hash;



/**
 * server class which starts both rmi and socket servers
 */
public class Server implements ServerInterface{
	
	/* network and game constants */
	public static final Integer ROOM_CAPACITY = 4;
	public static final Integer SOCKET_PORT = 3421;
	/* lock to handle multiple thread */ 
    private static Object ROOM_LOCK = new Object();
    private SocketServer socketServer;
    private static HashMap<String,NetworkPlayer> players;
    private ArrayList<Room> rooms;   
    
    /*
     * initialize the players hashmap and room arraylist
     * then it create the server's object
     */
    public Server() {
    	players = new HashMap<>();
    	rooms = new ArrayList<>();
    	socketServer = new SocketServer(this);
    }
    
    public static void logoutPlayers(ArrayList<NetworkPlayer> playersLogout){
    	for(NetworkPlayer player : playersLogout){
    		SocketPlayer.setDisconnectedPlayers(-1);
    		players.remove(player.getNickName());
    		Hash.unregister(player.getNickName());
    	}
    }
    /*
     * main which start the servers
     */
    public static void main(String[] args){
         Server server = new Server();
         server.startServer();
    }
    /*
     * starts the thread
     */
    private void startServer(){
    	System.out.println("SocketServer started");
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
			SocketPlayer newPlayer = (SocketPlayer)networkPlayer;
			if(player.getSocket().isClosed())
				player.resetConnection(newPlayer.getSocket(), newPlayer.getOutputStream(), newPlayer.getInputStream());
			if(player.getIdleStatus()){
				player.getSocket().close();
				player.resetConnection(newPlayer.getSocket(), newPlayer.getOutputStream(), newPlayer.getInputStream());
				player.getOutputStream().writeLong(Room.playerMoveTimeout);
				player.getOutputStream().flush();
				player.clientAction = "";
				player.setIdleStatus(false);
				SocketPlayer.setDisconnectedPlayers(-1);
			}
		return true;
		}else{
			SocketPlayer newPlayer = (SocketPlayer)networkPlayer;
			newPlayer.getOutputStream().writeLong(Room.playerMoveTimeout);
			newPlayer.getOutputStream().flush();
			newPlayer.setMessage("Wrong password, please reconnect");
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


	/*
	 * this method create the room if needed and call the method to add the player to the room
	 * the ROOM_LOCK avoid multiple room creation from different thread	 
	 */
	@Override
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
		}
	}
	/*
	 * return the room object using the roomnumber provided	 
	 */
	public Room getRoom(Integer roomNumber){
		return this.rooms.get(roomNumber);
	}
}