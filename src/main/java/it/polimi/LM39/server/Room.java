package it.polimi.LM39.server;

import it.polimi.LM39.controller.*;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  class that handle a single room
 */
public class Room implements Runnable{
	/**
	 * logger, timeouts, room state and constants 
	 */
	Logger logger = Logger.getLogger(Room.class.getName());
	public static final Integer MIN_CLIENT = 2;
	public static final Integer MAX_CLIENT = 4;
	protected static Integer roomCounter = 0;
	private ArrayList<NetworkPlayer> players;
	private long roomCreationTime;
	private long roomStartTimeout;
	private Boolean roomState;	
    /**
     * initialize the room properties
     */
    public Room() {
    	players = new ArrayList<NetworkPlayer>();
    	roomState = false;
    	roomCounter++;
    	try{
    		roomStartTimeout = GsonReader.timeoutLoader(0) *1000L;	//0 to load room start timeout
    	}
    	catch(IOException exception){
    		logger.log(Level.SEVERE, "Failed to read file", exception);
    	}
    }
    /**
     * room timeout getter
     * @return
     */
    public long getRoomTimeout(){
    	return this.roomStartTimeout;
    }
    /**
     * room state getter (started or not)
     * @return
     */
    public Boolean getRoomState(){
    	return this.roomState;
    }
    /**
     * thread which measure time elapsed and start the game after the timeout expires
     * 
     */
    @Override
    public void run(){
		System.out.println("Room started. Waiting for timeout");
    	while(System.currentTimeMillis() - roomCreationTime <= roomStartTimeout && !roomState){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
    	}
    	if(!roomState)
    		this.startRoom();   
    }
    /**
     * add the player to the current room
     * @param player
     */
    public void addPlayer(NetworkPlayer player){
    	this.players.add(player);
		if(this.getConnectedPlayers() == MIN_CLIENT){
			roomCreationTime = System.currentTimeMillis();
			new Thread(this).start();
		}
		if(this.getConnectedPlayers() == MAX_CLIENT)
			this.startRoom();
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
    	for(NetworkPlayer player : players)
    		player.setIdleStatus(false);
    	Game game = new Game(this.getConnectedPlayers(),players);
    	roomState = true;
    	synchronized(SocketPlayer.LOCK){
    		SocketPlayer.LOCK.notifyAll();
    	}
    	new Thread(game).start();
    }

}