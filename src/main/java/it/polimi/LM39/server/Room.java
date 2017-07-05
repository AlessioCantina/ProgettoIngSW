package it.polimi.LM39.server;

import it.polimi.LM39.controller.*;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 *  class that handle room 
 */
public class Room implements Runnable{
	Logger logger = Logger.getLogger(Room.class.getName());
	public final Integer MIN_CLIENT = 2;
	public final Integer MAX_CLIENT = 4;
	protected static Integer roomCounter = 0;
	private Game game;
	private ArrayList<NetworkPlayer> players;
	private long roomCreationTime;
	private long roomStartTimeout;
	protected static Long playerMoveTimeout;
	private Boolean roomState;	//TODO check if we need something more than a boolean
    /*
     * initialize the room properties
     */
    public Room() {
    	players = new ArrayList<NetworkPlayer>();
    	roomState = false;
    	roomCounter++;
    	try{
    		GsonReader.configLoader(this);
    	}
    	catch(IOException exception){
    		logger.log(Level.SEVERE, "Failed to read file", exception);
    	}
    }
    public long getRoomTimeout(){
    	return this.roomStartTimeout;
    }
    public Boolean getRoomState(){
    	return this.roomState;
    }
    public void setRoomTimeout(Integer roomStartTimeOut){
    	this.roomStartTimeout = roomStartTimeOut*1000L;
    }
    public static void setMoveTimeout(Integer moveTimeout){
    	playerMoveTimeout = moveTimeout*1000L;
    }
    /*
     * thread which measure time elapsed and if there are enough player starts the game
     * 
     */
    public void run(){
    	while(System.currentTimeMillis() - roomCreationTime <= roomStartTimeout && !roomState){
			try {
				System.out.println("Waiting for timeout");
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
    	}
    	this.startRoom();   
    }
    /*
     * it adds a player to a room
     */
    public void addPlayer(NetworkPlayer player){
    	this.players.add(player);
    	System.out.println(getConnectedPlayers());
		if(this.getConnectedPlayers() == MIN_CLIENT){
			roomCreationTime = System.currentTimeMillis();
			new Thread(this).start();
		}
		else if(this.getConnectedPlayers() == MAX_CLIENT)
			this.roomState = true;
    }
    /*
     * return the number of players connected to the room
     */
    public Integer getConnectedPlayers(){
    	return this.players.size();
    }
    /*
     * start the game thread
     */
    public void startRoom(){
    	this.game = new Game(this.getConnectedPlayers(),players);
    	roomState = true;
    	synchronized(SocketPlayer.LOCK){
    		SocketPlayer.LOCK.notifyAll();
    	}
    	new Thread(game).start();
    }

}