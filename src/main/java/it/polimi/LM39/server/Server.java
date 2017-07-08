package it.polimi.LM39.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import it.polimi.LM39.credentials.Hash;

/**
 * server class which starts socket server
 */
public class Server implements ServerInterface{
	
	// network and game constants, locks to handle multiple threads
	public static final Integer ROOM_CAPACITY = 4;
	public static final Integer SOCKET_PORT = 3421;
    private static Object ROOM_LOCK = new Object();
    private SocketServer socketServer;
    private static HashMap<String,NetworkPlayer> players;
    private ArrayList<Room> rooms;   
    
    /**
     * initialize the players hashmap and room arraylist
     * then create the server's object
     */
    public Server() {
    	players = new HashMap<>();
    	rooms = new ArrayList<>();
    	socketServer = new SocketServer(this);
    }
    /**
     * used by controller when the game is finished
     * to allow players to play another game
     * @param playersLogout
     */
    public static void logoutPlayers(ArrayList<NetworkPlayer> playersLogout){
    	for(NetworkPlayer player : playersLogout){
    		SocketPlayer.setDisconnectedPlayers(-1);
    		players.remove(player.getNickName());
    		Hash.unregister(player.getNickName());
    	}
    }
    /**
     * main which start the servers
     */
    public static void main(String[] args){
         Server server = new Server();
         server.startServer();
    }
    /**
     * starts the server's thread
     */
    private void startServer(){
    	System.out.println("SocketServer started");
    	socketServer.startServer(SOCKET_PORT);
    }

    /**
     * login the player
     * add the player to the room
     * add the player to the server's hashmap
     * register the player to the credentials' database
     * checks if the player was already in a game or if the player is already playing
     * @param nickName
     * @param password
     * @param networkPlayer
     */
	@Override
	public Boolean loginPlayer(String nickName,String password,NetworkPlayer networkPlayer) throws IOException {
		//player isn't in a game
		if (!players.containsKey(nickName)){
			this.joinRoom(networkPlayer);
			players.put(nickName, networkPlayer);
			networkPlayer.setNickName(nickName);
			Hash.register(nickName, password);
			return false;
		}
		//player already in a game
		else if(Hash.login(nickName, password)){
				SocketPlayer player = (SocketPlayer)players.get(nickName);
				SocketPlayer newPlayer = (SocketPlayer)networkPlayer;
				//player disconnected during his turn
				if(player.getSocket().isClosed()){
					player.resetConnection(newPlayer.getSocket(), newPlayer.getOutputStream(), newPlayer.getInputStream());
					return true;
				}
				//player idle or disconnected not during his turn
				else{
					//player which try to re-login during his turn
					if(!player.getSocket().isClosed() && !player.getIdleStatus()){
						player.resetConnection(newPlayer.getSocket(), newPlayer.getOutputStream(), newPlayer.getInputStream());
						this.sendTimeout(player.getOutputStream());
						return false;
					}
					//player idle
					player.getSocket().close();
					player.resetConnection(newPlayer.getSocket(), newPlayer.getOutputStream(), newPlayer.getInputStream());
					this.sendTimeout(player.getOutputStream());
					player.clientAction = "";
					player.setIdleStatus(false);
					SocketPlayer.setDisconnectedPlayers(-1);
					return true;
				}
		//login failed
		}else{
			SocketPlayer newPlayer = (SocketPlayer)networkPlayer;
			this.sendTimeout(newPlayer.getOutputStream());
			newPlayer.setMessage("Wrong password, please reconnect");
		}
		return false;
	}
	/**
	 * support method to send the timeout to the player when he reconnects
	 * @param stream
	 * @throws IOException
	 */
	private void sendTimeout(ObjectOutputStream stream) throws IOException{
		stream.writeLong(Room.playerMoveTimeout);
		stream.flush();
	}

	/**
	 * this method create the room if needed and call the method to add the player to the room
	 * the ROOM_LOCK avoid multiple room creation from different thread	 
	 * @param networkPlayer
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
	/**
	 * return the room object using the roomnumber provided	 
	 */
	@Override
	public Room getRoom(Integer roomNumber){
		return this.rooms.get(roomNumber);
	}
}