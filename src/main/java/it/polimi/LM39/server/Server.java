package it.polimi.LM39.server;

import java.util.*;

import it.polimi.LM39.exception.FailedToInstantiateServerException;
import it.polimi.LM39.exception.ServerStartException;

/**
 * server class which starts both rmi and socket servers
 */
public class Server implements ServerInterface{
	/* network and game constants */
	public static final Integer ROOM_CAPACITY = 4;
	public static final Integer SOCKET_PORT = 3421;
	public static final Integer RMI_PORT = 3334;
	/* lock to handle multiple thread */
    private static Object LOGIN_LOCK = new Object();  
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
    public static void main(String[] args) throws FailedToInstantiateServerException {
     try {
            Server server = new Server();
            server.StartServer();
        } catch (Exception e) {
            throw new FailedToInstantiateServerException(e);
        }
    }
    /*
     * starts the thread
     */
    private void StartServer() throws ServerStartException{
    	System.out.println("SocketServer Creato");
    	socketServer.StartServer(SOCKET_PORT);
    }

    /*
     * add player to the hashmap TODO test if the lock is working from different machines
     * 
     */
	@Override
	public void loginPlayer(String nickName, NetworkPlayer networkPlayer) {
		synchronized(LOGIN_LOCK){
			if (!players.containsKey(nickName)){
				players.put(nickName, networkPlayer);
				networkPlayer.setNickName(nickName);
			}
		}	
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
				if(room.getConnectedPlayers() == ROOM_CAPACITY)
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