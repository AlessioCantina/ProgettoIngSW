package it.polimi.LM39.server;

import java.util.*;

/**
 * 
 */
public class Server implements ServerInterface{

	public static final Integer ROOM_CAPACITY = 4;
	public static final int SOCKET_PORT = 3333;
	public static final int RMI_PORT = 3334;
    /**
     * Default constructor
     */
    public Server() {
    	players = new HashMap<>();
    	rooms = new ArrayList<>();
    	socketServer = new SocketServer(this);
    	//RMIserver = new RMIserver(RMI_PORT);
    }

    /**
     * 
     */
    private RMIServer rmiServer;
    
    private static Object LOGIN_LOCK = new Object();
    
    private static Object ROOM_LOCK = new Object();

    /**
     * 
     */
    private SocketServer socketServer;

    /**
     * 
     */
    private HashMap<String,NetworkPlayer> players;

    /**
     * 
     */
    private ArrayList<Room> rooms;




	@Override
	public void loginPlayer(String nickName, NetworkPlayer networkPlayer) {
		synchronized(LOGIN_LOCK){
			if (!players.containsKey(nickName)){
				players.put(nickName, networkPlayer);
				networkPlayer.setNickName(nickName);
			}
		}	
	}



	@Override
	public NetworkPlayer getPlayer(String nickName) {
		return players.get(nickName);
	}



	@Override
	public void joinRoom(NetworkPlayer networkPlayer) {
		synchronized(ROOM_LOCK){ //avoid multiple room creation from different thread
			if(Room.roomCounter == 0)
			{
				Room room = new Room(networkPlayer);
				this.rooms.add(room);
			}
			else
			{
				Room room = this.getRoom(Room.roomCounter);
				if(room.getConnectedPlayers() == ROOM_CAPACITY)
				{
					Room newRoom = new Room(networkPlayer);
					this.rooms.add(newRoom);
				}
				else
					room.addPlayer(networkPlayer);
			}
		}
	}
	public Room getRoom(Integer roomNumber){
		return this.rooms.get(roomNumber);
	}
}