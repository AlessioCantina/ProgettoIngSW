package it.polimi.LM39.server;

import java.util.*;

/**
 * 
 */
public class Room {
	protected static Integer roomCounter;
	private ArrayList<NetworkPlayer> players;
    /**
     * Default constructor
     */
    public Room(NetworkPlayer player){
    	players = new ArrayList<NetworkPlayer>();
    	this.addPlayer(player);
    	roomCounter++;
    }
    public void addPlayer(NetworkPlayer player){
    	this.players.add(player);
    }
    
    public Integer getConnectedPlayers(){
    	return this.players.size();
    }

}