package it.polimi.LM39.server;

import it.polimi.LM39.controller.*;
import java.util.*;

/**
 * 
 */
public class Room {
	public final Integer MIN_CLIENT = 2;
	protected static Integer roomCounter;
	private Game game;
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
    public void startRoom(){
    	this.game = new Game(this.getConnectedPlayers());
    }
}