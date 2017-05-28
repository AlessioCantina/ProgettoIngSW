package it.polimi.LM39.server;

import java.util.*;

/**
 * 
 */
public class Room {
	public static final Integer ROOM_CAPACITY = 4;
	private static Integer roomCounter;
	private ArrayList<InterfacePlayer> players;
    /**
     * Default constructor
     */
    public Room() {
    	players = new ArrayList<InterfacePlayer>();
    	roomCounter++;
    }
    
    public Integer getConnectedPlayers(){
    	return this.players.size();
    }
    public Integer getNumberOfRooms(){
    	return roomCounter;
    }

    public void connect(InterfacePlayer player) {
    	if(this.getConnectedPlayers() == ROOM_CAPACITY){
    		System.out.println("Room has reached max capacity. Creating new room...");
    		Room room = new Room();
    		room.players.add(player);
    	}
    	else
    		this.players.add(player);
    }
}