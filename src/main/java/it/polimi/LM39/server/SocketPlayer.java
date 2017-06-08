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
	    /**
	 * 
	 */
		private static final long serialVersionUID = 1975895874697189786L;
		private transient Socket socket;
	    private transient ServerInterface serverInterface;
	    private transient ObjectInputStream objInput;			//player's interface and I/O streams
	    private transient ObjectOutputStream objOutput;
	    private String message;						//information which will be send to the client
	    private MainBoard mainBoard;
	    private String clientAction;
	    private transient boolean requestedMessage;
	    /*
	     * the constructor initialize the streams and start the thread
	     */
	    public SocketPlayer(ServerInterface serverInterface, Socket socket) throws IOException {
	          this.socket = socket;
	          this.requestedMessage = false;
	          this.serverInterface = serverInterface;
	          this.objOutput = new ObjectOutputStream(this.socket.getOutputStream()); 
	          this.objOutput.flush();	//needed to avoid deadlock
	          this.objInput = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream()));
	    }
	    /*
	     * used from the controller to set what we want to send
	     */
	    public void setMessage(String message,MainBoard mainBoard){
	    	this.message = message;
	    	this.mainBoard = mainBoard;
	    	requestedMessage = true;
	    }
	    public void setMessage(String message){
	    	this.message = message;
	    	requestedMessage = true;
	    }
	    private Boolean messageState(){
	    	if(this.message != null)
	    		return true;
	    	else
	    		return false;
	    }
	    /*
	     * infinite loop which listen the client from input and send the available information to it
	     * it checks if we have something to send to the client and waits for the answer
	     */
	    public void run() {
	    	Logger logger = Logger.getLogger(SocketPlayer.class.getName());
	        this.serverInterface.joinRoom(this);
	        try {
	    		//TODO ask if it is possible to suppress sonarlint bug for infinite loops
	        	while(true){
	        		if(this.messageState()){
	        			objOutput.writeObject(this.mainBoard);
	        			objOutput.writeUTF(this.message);
	        			this.message = null;
	        		}
	        		if(objInput.available() > 0 && requestedMessage == true){
	        			clientAction = objInput.readUTF();
	        			requestedMessage = false;
	        		}
	        		else if(objInput.available() > 0){
	        					objInput.readUTF();
	        					objOutput.writeUTF("Rejected Move");
	        		}
	        		objOutput.flush();
	        		objOutput.reset();      //to send multiple objects of the same type
	        	}
	        }catch (Exception e) {
	            logger.log(Level.SEVERE, "Can't instantiate listener for the player", e);
	        }
	    }
	    /*
	     * this method return the client action to the game controller
	     */
	    public String sendMessage(){
	    	while(this.clientAction == null)
	    		continue;
	    	return this.clientAction;
	    }
}