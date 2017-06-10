package it.polimi.LM39.server;

import it.polimi.LM39.controller.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 *  class that handle room 
 */
public class Room implements Runnable{
	public final Integer MIN_CLIENT = 2;
	public final Integer MAX_CLIENT = 4;
	protected static Integer roomCounter = 0;
	private Game game;
	private ArrayList<NetworkPlayer> players;
	protected long roomCreationTime;
	protected long roomStartTimeout = 15000;
    /*
     * initialize the room properties
     */
    public Room(){
    	players = new ArrayList<NetworkPlayer>();
    	roomCounter++;
    }
    
    public void run(){
    	while(System.currentTimeMillis() - roomCreationTime <= roomStartTimeout){
			try {
				System.out.println("Waiting for timeout");
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	System.out.println("Game starting with " + this.getConnectedPlayers() +" players");
    	this.startRoom();
    }
    /*
     * it adds a player to a room
     */
    public void addPlayer(NetworkPlayer player){
    	this.players.add(player);
		if(this.getConnectedPlayers() == MIN_CLIENT){
			roomCreationTime = System.currentTimeMillis();
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
     * TODO: method which decide which configuration run 
     */
    public void startRoom(){
    	this.game = new Game(this.getConnectedPlayers(),players);
    	synchronized(SocketPlayer.LOCK){
    		SocketPlayer.LOCK.notifyAll();
    	}
    	new Thread(game).start();
    }

}