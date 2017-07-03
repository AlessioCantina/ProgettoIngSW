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
	    		logger.log(Level.WARNING, "Can't write objects on stream", e);
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
					Thread.currentThread().interrupt();
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
	    	    	this.serverInterface.joinRoom(this);
	    	    	while(!Thread.currentThread().isInterrupted())
	    	    		LOCK.wait();
	    			System.out.println("THREAD UNLOCKED" + Thread.currentThread());
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
	    	}
	    }
	    /*
	     * this method return the client action to the game controller
	     */
	    public String sendMessage(){
	    	synchronized(CLIENT_LOCK){
	    		try {
	    			if(("").equals(clientAction)){
	    				objOutput.writeObject(true);
	    				objOutput.flush();
	    				clientAction = objInput.readUTF();
	    				while(("Client Timedout").equals(clientAction))
	    					CLIENT_LOCK.wait();
	    			}
				} catch (IOException e) {
					logger.log(Level.SEVERE,"Socket closed", e); //TODO handle the ioexception from socket closed
				} catch (InterruptedException e) {
					logger.log(Level.SEVERE,"Can't interrupt the thread", e);
					Thread.currentThread().interrupt();
				}
	    	}
	    	String stringToSet = clientAction;
	    	clientAction = "";
	    	return stringToSet;
	    }
}