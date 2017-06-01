package it.polimi.LM39.server;

import it.polimi.LM39.controller.*;
import java.util.*;

/**
 *  class that handle room 
 */
public class Room {
	public final Integer MIN_CLIENT = 2;
	protected static Integer roomCounter = 0;
	private Game game;
	private Integer roomPeriod;
	private ArrayList<NetworkPlayer> players;
    /*
     * initialize the room properties
     */
    public Room(){
    	players = new ArrayList<NetworkPlayer>();
    	roomPeriod = 1;
    	roomCounter++;
    }
    /*
     * it adds a player to a room
     */
    public void addPlayer(NetworkPlayer player){
    	this.players.add(player);
    }
    /*
     * return the number of players connected to the room
     */
    public Integer getConnectedPlayers(){
    	return this.players.size();
    }
    /*
     * TODO: method which decide which configuration run 
     */
    public void startRoom(){
    	this.game = new Game(this.getConnectedPlayers());
    }
    /*
     * this method depends on the period of the game: when the game start it will randomize 
     * the play order, in the next rounds it will put the order following the council palace.
     * When a player have no member on council palace it will follow the relative order
     */
    public void setPlayOrder(){
    	//TODO
    }
}