package it.polimi.LM39.server;
import it.polimi.LM39.model.MainBoard;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * every player connected to the socket has this object which manipulates connection between server and client
 * this is the server side, so we send objects and strings and receive strings from client
 */
public class SocketPlayer extends NetworkPlayer implements Runnable{
	/*
	 * 
	 */
		private transient Logger logger = Logger.getLogger(SocketPlayer.class.getName());
		private static final long serialVersionUID = 1975895874697189786L;
		private transient Socket socket;
	    private transient ServerInterface serverInterface;
	    private transient ObjectInputStream objInput;			//player's interface and I/O streams
	    private transient ObjectOutputStream objOutput;
	    private static String message;				//information which will be send to the client
	    private static MainBoard mainBoard;
	    private String clientAction;
	    private transient boolean requestedMainboard;
	    private transient boolean requestedMessage;
	    protected static transient Object LOCK = new Object();
	    private static transient Object CLIENT_LOCK = new Object();
    	
	    /*
	     * the constructor initialize the streams and start the thread
	     */
	    public SocketPlayer(ServerInterface serverInterface, Socket socket) throws IOException {
	    	  message = "";
	    	  this.clientAction = "";
	          this.socket = socket;
	          this.requestedMainboard = false;
	          this.requestedMessage = false;
	          this.serverInterface = serverInterface;
	          this.objOutput = new ObjectOutputStream(this.socket.getOutputStream()); 
	          this.objOutput.flush();	//needed to avoid deadlock
	          this.objInput = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream()));
	          
	    }
	    /*
	     * used from the controller to set what we want to send
	     */
	    public void setMessage(MainBoard mainboard){
	    		mainBoard = mainboard;
	    		requestedMainboard = true;
	    }
	    public void setMessage(String controllerMessage){
	    	synchronized(LOCK){
	    		message = controllerMessage;
	    		requestedMessage = true;
	    		try {
	    			LOCK.wait();
	    		} catch (InterruptedException e) {
	    			Thread.currentThread().interrupt();
	    		}
	    	}
	    }
	    /*
	     * infinite loop which listen the client from input and send the available information to it
	     * it checks if we have something to send to the client and waits for the answer
	     */
	    public void run() {
	    	synchronized(LOCK){
	    		try {
	    	    	this.serverInterface.joinRoom(this);
					LOCK.wait();
	    			System.out.println("THREAD UNLOCKED" + Thread.currentThread());
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
	    	}
	    	while(true){
	    		if(requestedMainboard){
	    			sendMainBoardToClient();
	    		}
	    		if(requestedMessage){
	    			sendMessageToClient();
	    		}
	    		receiveFromClient();
	    	}
	    }
	    private synchronized void sendMainBoardToClient(){
	    		try{
        			System.out.println("Invio mainboard");
        			objOutput.writeObject(this);
        			objOutput.writeObject(mainBoard);
        			objOutput.flush();
        			objOutput.reset();
        			requestedMainboard = false;
	    		}catch (Exception e) {
	    			logger.log(Level.WARNING, "Can't write objects on stream", e);
		        }	    			
	    	}
	    private void sendMessageToClient(){
	    	System.out.println("Invio Messaggio");
	    	synchronized(LOCK){
	    		Boolean enableMessage = true;
	    		try{
	    			objOutput.writeObject(enableMessage);
	    			objOutput.writeUTF(message);
	    			objOutput.flush();
	    			requestedMessage = false;
	    		}catch (Exception e){
	    			logger.log(Level.WARNING, "Can't write strings on stream", e);
	    		}
	    		LOCK.notifyAll();
	    	}
		}
	    private void receiveFromClient(){
	    	synchronized(CLIENT_LOCK){
    		try{
    			if(objInput.available() > 0){
    				clientAction = objInput.readUTF();
    				System.out.println(clientAction);
    				CLIENT_LOCK.notifyAll();
    			}	    		
	    	}catch (Exception e) {
	    		logger.log(Level.WARNING, "Can't read input on stream", e);
	        }
	    	}
	    }
	    /*
	     * this method return the client action to the game controller
	     */
	    public String sendMessage(){
	    	synchronized(CLIENT_LOCK){
	    		while(("").equals(clientAction)){
	    			try {
						CLIENT_LOCK.wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
	    		}
	    	    	
	    	String stringToSet = clientAction;
	    	clientAction = "";
	    	return stringToSet;
	    	}	
	    }
}