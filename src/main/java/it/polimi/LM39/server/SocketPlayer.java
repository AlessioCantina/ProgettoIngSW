package it.polimi.LM39.server;
import it.polimi.LM39.model.MainBoard;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * every player connected to the socket has this object which manipulates connection between server and client
 * this is the server side, so we send objects and strings and receive strings from client
 */
public class SocketPlayer extends NetworkPlayer implements Runnable{

		private transient Logger logger = Logger.getLogger(SocketPlayer.class.getName());
		private static final long serialVersionUID = 1975895874697189786L;
		private transient Socket socket;
	    private transient ServerInterface serverInterface;
	    private transient ObjectInputStream objInput;			//player's interface and I/O streams
	    private transient ObjectOutputStream objOutput;
	    private String message;				//information which will be send to the client
	    private MainBoard mainBoard;
	    private String clientAction;
	    private Boolean requestedMessage;
	    protected transient static Object LOCK = new Object();
	    private transient static Object CLIENT_LOCK = new Object();
	    /*
	     * the constructor initialize the streams and start the thread
	     */
	    public SocketPlayer(ServerInterface serverInterface, Socket socket) throws IOException {
	    	  message = "";
	    	  this.clientAction = "";
	          this.socket = socket;
	          this.requestedMessage = false;
	          this.serverInterface = serverInterface;
	          this.objOutput = new ObjectOutputStream(this.socket.getOutputStream()); 
	          this.objOutput.flush();	//needed to avoid deadlock
	          this.objInput = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream()));
	    }
	    
	    public Socket getSocket(){
	    	return this.socket;
	    }
	    
	    public ObjectOutputStream getOutputStream(){
	    	return this.objOutput;
	    }
	    
	    public ObjectInputStream getInputStream(){
	    	return this.objInput;
	    }
	    
	    public void resetConnection(Socket socket,ObjectOutputStream output, ObjectInputStream input) throws IOException {
	    	this.socket = socket;
	        this.objOutput = output;
	        this.objInput = input;
	    }
	    /*
	     * used from the controller to send mainboard
	     */
	    public void setMessage(MainBoard mainBoard){
	    	this.mainBoard = mainBoard;
	    	try{
        		objOutput.writeObject(this);
        		objOutput.writeObject(this.mainBoard);
        		objOutput.flush();
        		objOutput.reset();
	    	}catch (IOException e) {
	    		logger.log(Level.WARNING, "Player disconnected", e);
	    		disconnectionHandler();
		    }	
	    }
	    /*
	     * synchronized method which avoid deadlock if the controller want to send multiple messages to a client
	     */
	    public void setMessage(String controllerMessage){
	    	synchronized(LOCK){
	    		this.message = controllerMessage;
    			requestedMessage = false;
    			try {
    				objOutput.writeObject(requestedMessage);
    				objOutput.writeUTF(this.message);
    				objOutput.flush();
	    		} catch (IOException e) {
	    			logger.log(Level.WARNING, "Player disconnected");
	    			disconnectionHandler();
	    		}
	    	}
	    }
	    
	    public void disconnectionHandler(){
	    	synchronized(DISCONNECT_LOCK){
	    		try {
	    			socket.close();
	    			while(socket.isClosed())
	    				DISCONNECT_LOCK.wait();
	    			setMessage(this.mainBoard);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} catch (IOException e) {
					logger.log(Level.SEVERE, "Can't close socket", e);
				}
	    	}
	    }


	    /*
	     * wait for the room to start, then unlocks player's threads
	     * 
	     */
	    public void run() {
	    	synchronized(LOCK){
	    		try {
	    			if(this.serverInterface.loginPlayer(objInput.readUTF(), this)){
	    				objOutput.flush();
	    				synchronized(DISCONNECT_LOCK){
	    					DISCONNECT_LOCK.notifyAll();
	    				}
	    			}
	    	    	while(!Thread.currentThread().isInterrupted()){
	    	    		LOCK.wait();
	    	    		break;
	    	    	}
	    			System.out.println("THREAD UNLOCKED" + Thread.currentThread());
    				System.out.println(Room.playerMoveTimeout);
    				objOutput.writeLong(Room.playerMoveTimeout);
				} catch (InterruptedException | IOException e) {
					Thread.currentThread().interrupt();
				}
	    	}
	    }
	    /*
	     * this method return the client action to the game controller
	     */
	    public String sendMessage(){
	    		try {
	    			if(("").equals(clientAction)){	    				
	    				objOutput.writeObject(true);
	    				objOutput.flush();
	    				clientAction = objInput.readUTF();
	    				while(("Client Timedout").equals(clientAction)){
	    					synchronized(CLIENT_LOCK){
	    						CLIENT_LOCK.wait();
	    					}
	    				}	    					
	    			}
				} catch (IOException e) {
					logger.log(Level.WARNING, "Player disconnected");
					disconnectionHandler();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
	    	String stringToSet = clientAction;
	    	clientAction = "";
	    	return stringToSet;
	    }
}