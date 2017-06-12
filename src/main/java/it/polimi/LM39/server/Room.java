package it.polimi.LM39.server;

import it.polimi.LM39.controller.*;
import java.util.*;

/*
 *  class that handle room 
 */
public class Room implements Runnable{
	public final Integer MIN_CLIENT = 2;
	public final Integer MAX_CLIENT = 4;
	protected static Integer roomCounter = 0;
	private Game game;
	private ArrayList<NetworkPlayer> players;
	private long roomCreationTime;
	private static long roomStartTimeout;
	private static long playerMoveTimeout;
	private Boolean roomState;	//TODO check if we need something more than a boolean
    /*
     * initialize the room properties
     */
    public Room(){
    	players = new ArrayList<NetworkPlayer>();
    	roomState = false;
    	roomCounter++;
    }
    public Boolean getRoomState(){
    	return this.roomState;
    }
    public static void setRoomTimeout(long roomStartTimeOut){
    	roomStartTimeout = roomStartTimeOut;
    }
    public static void setPlayerMoveTimeout(long playerMoveTimeOut){
    	playerMoveTimeout = playerMoveTimeOut;
    }
    /*
     * thread which measure time elapsed and if there are enough player starts the game
     * 
     */
    public void run(){
    	System.out.println("systemcurrenttime " + System.currentTimeMillis());
    	System.out.println("roomstarttimeout " + roomStartTimeout);
    	while(System.currentTimeMillis() - roomCreationTime <= roomStartTimeout){
			try {
				System.out.println("Waiting for timeout");
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
    	}
    	if(this.getConnectedPlayers() < 4)
    		this.startRoom();
    	System.out.println("Game starting with " + this.getConnectedPlayers() +" players");
    }
    /*
     * it adds a player to a room
     */
    public void addPlayer(NetworkPlayer player){
    	this.players.add(player);
		if(this.getConnectedPlayers() == MIN_CLIENT){
			roomCreationTime = System.currentTimeMillis();
			System.out.println("roomcreationtime " + roomCreationTime);
			new Thread(this).start();
		}
		else if(this.getConnectedPlayers() == MAX_CLIENT)
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
    	this.game = new Game(this.getConnectedPlayers(),players);
    	synchronized(SocketPlayer.LOCK){
    		SocketPlayer.LOCK.notifyAll();
    	}
    	roomState = true;
    	new Thread(game).start();
    }

}