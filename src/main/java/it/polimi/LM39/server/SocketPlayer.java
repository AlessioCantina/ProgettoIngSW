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

		private transient Logger logger = Logger.getLogger(SocketPlayer.class.getName());
		private static final long serialVersionUID = 1975895874697189786L;
		private transient Socket socket;
	    private transient ServerInterface serverInterface;
	    private transient ObjectInputStream objInput;			//player's interface and I/O streams
	    private transient ObjectOutputStream objOutput;
	    private String message;				//information which will be send to the client
	    private MainBoard mainBoard;
	    protected String clientAction;
	    private Boolean requestedMessage;
	    protected transient static Object LOCK = new Object();
	    protected static Integer disconnectedPlayers = 0;
	    /**
	     * the constructor initialize the streams
	     */
	    public SocketPlayer(ServerInterface serverInterface, Socket socket) throws IOException {
	    	  message = "";
	    	  this.clientAction = "";
	          this.socket = socket;
	          this.socket.setKeepAlive(true);
	          this.requestedMessage = false;
	          this.serverInterface = serverInterface;
	          this.objOutput = new ObjectOutputStream(this.socket.getOutputStream()); 
	          this.objOutput.flush();	//needed to avoid deadlock
	          this.objInput = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream()));
	    }
	    /**
	     * socket getter
	     * @return
	     */
	    public Socket getSocket(){
	    	return this.socket;
	    }
	    /**
	     * output stream getter
	     * @return
	     */
	    public ObjectOutputStream getOutputStream(){
	    	return this.objOutput;
	    }
	    /**
	     * input stream getter
	     * @return
	     */
	    public ObjectInputStream getInputStream(){
	    	return this.objInput;
	    }
	    /**
	     * disconnected players setter
	     * @param playerNumber
	     */
	    public static synchronized void setDisconnectedPlayers(Integer playerNumber){
	    	if(disconnectedPlayers + playerNumber >= 0)
	    		disconnectedPlayers += playerNumber;
	    }

	    /**
	     * reset the player's connection when he reconnects to the server
	     * @param socket
	     * @param output
	     * @param input
	     * @throws IOException
	     */
	    public void resetConnection(Socket socket,ObjectOutputStream output, ObjectInputStream input) throws IOException {
	    	this.socket = socket;
	        this.objOutput = output;
	        this.objInput = input;
	    }
	    /**
	     * used from the controller to set the mainboard to send
	     */
	    @Override
	    public void setMessage(MainBoard mainBoard){
	    	this.mainBoard = mainBoard;
	    	try{
	    		if(!this.getIdleStatus())
	    			objOutput.writeObject(this);
        			objOutput.writeObject(this.mainBoard);
        			objOutput.flush();
        			objOutput.reset();
					if(SocketPlayer.disconnectedPlayers != 0)
						setMessage("There are " + SocketPlayer.disconnectedPlayers + " disconnected players");
	    	}catch (IOException e) {
	    		logger.log(Level.WARNING, "Player disconnected");
	    		disconnectionHandler();
		    }	
	    }
	    /**
	     * used from the controller to set the mainboard to send
	     * synchronized to avoid deadlock if the controller want to send multiple messages to a client
	     */
	    @Override
	    public void setMessage(String controllerMessage){
	    	synchronized(LOCK){
	    		this.message = controllerMessage;
    			requestedMessage = false;
    			try {
    				if(!this.getIdleStatus()){
    					objOutput.writeObject(requestedMessage);    						
    					objOutput.writeUTF(this.message);
    					objOutput.flush();
    				}
	    		} catch (IOException e) {
	    			logger.log(Level.WARNING, "Player disconnected");
	    			disconnectionHandler();
	    		}
	    	}
	    }
	    /**
	     * this method checks if the player closed the game, 
	     * handles the disconnection and wait for the player to reconnect
	     */
	    public void disconnectionHandler(){
	    	synchronized(DISCONNECT_LOCK){
	    		try {
	    			socket.close();
	    			while(socket.isClosed())
	    				DISCONNECT_LOCK.wait();
    				objOutput.writeLong(Room.playerMoveTimeout);
    				objOutput.flush();
	    			setMessage(this.mainBoard);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} catch (IOException e) {
					logger.log(Level.SEVERE, "Can't close socket", e);
				}
	    	}
	    }


	    /**
	     * wait for the room to start, then unlocks player's threads
	     * 
	     */
	    @Override
	    public void run() {
	    	synchronized(LOCK){
	    		try {
	    			String userName = objInput.readUTF();
	    			String password = objInput.readUTF();
	    			if(this.serverInterface.loginPlayer(userName,password,this)){
	    				synchronized(DISCONNECT_LOCK){
	    					DISCONNECT_LOCK.notifyAll();
	    				}
	    			}
	    	    	while(!Thread.currentThread().isInterrupted()){
	    	    		LOCK.wait();
	    	    		break;
	    	    	}
    				objOutput.writeLong(Room.playerMoveTimeout);
    				objOutput.flush();
				} catch (InterruptedException | IOException e) {
					Thread.currentThread().interrupt();
				}
	    	}
	    }
	    /**
	     * this method return the client action to the game controller
	     * if a client exceeds the timeout return timeout
	     */
	    @Override
	    public String sendMessage(){
	    	try {
	    		if(("").equals(clientAction)){	
	    			objOutput.writeObject(true);
	    			objOutput.flush();
	    			clientAction = objInput.readUTF();
	    		}
	    		if(("timeout").equals(clientAction)){
	    			if(!this.getIdleStatus())
	    				setDisconnectedPlayers(+1);
	    			this.setIdleStatus(true);
	    			objOutput.writeObject(true);
	    			objOutput.flush();
	    			return clientAction;
	    		}	    				
			} catch (IOException e) {
				logger.log(Level.WARNING, "Player disconnected");
				disconnectionHandler();
			}
	    	String stringToSet = clientAction;
	    	clientAction = "";
	    	return stringToSet;
	    }
}