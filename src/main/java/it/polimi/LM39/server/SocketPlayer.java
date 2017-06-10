package it.polimi.LM39.server;
import it.polimi.LM39.model.MainBoard;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * every player connected to the socket has this object which manipulates connection between server and client
 * this is the server side, so we send objects and strings and receive strings from client
 */
public class SocketPlayer extends NetworkPlayer implements Runnable{
	/*
	 * 
	 */
		private static final long serialVersionUID = 1975895874697189786L;
		private transient Socket socket;
	    private transient ServerInterface serverInterface;
	    private transient ObjectInputStream objInput;			//player's interface and I/O streams
	    private transient ObjectOutputStream objOutput;
	    private String message;				//information which will be send to the client
	    private MainBoard mainBoard;
	    private String clientAction;
	    private transient boolean requestedMainboard;
	    private transient boolean requestedMessage;
	    private static transient Object LOCK = new Object();
    	
	    /*
	     * the constructor initialize the streams and start the thread
	     */
	    public SocketPlayer(ServerInterface serverInterface, Socket socket) throws IOException {
	    	  this.message = "";
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
	    public void setMessage(MainBoard mainBoard){
	    	this.mainBoard = mainBoard;
	    	requestedMainboard = true;
	    }
	    public void setMessage(String message){
	    	synchronized(LOCK){
	    		this.message = new String(message);
	    		requestedMessage = true;
	    		try {
	    			LOCK.wait();
	    		} catch (InterruptedException e) {
	    			e.printStackTrace();
	    		}
	    	}
	    }
	    /*
	     * infinite loop which listen the client from input and send the available information to it
	     * it checks if we have something to send to the client and waits for the answer
	     */
	    public void run() {

	    	this.serverInterface.joinRoom(this);
	    	while(true){
	    		if(this.requestedMessage){
	    			this.sendMessageToClient();
	    		}
	    		if(this.requestedMainboard){
	    			this.sendMainBoardToClient();
	    		}
	    		this.receiveFromClient();
	    	}
	    }
	    private void sendMainBoardToClient(){
	    		try{
        			System.out.println("Invio mainboard");
        			objOutput.writeObject(this);
        			objOutput.writeObject(this.mainBoard);
        			objOutput.flush();
        			requestedMainboard = false;
	    		}catch (Exception e) {
	    			e.printStackTrace();
		        }
	    			
	    	}
	    private void sendMessageToClient(){
	    	synchronized(LOCK){
	    		Boolean enableMessage = true;
	    		try{
	    			objOutput.writeObject(enableMessage);
	    			objOutput.writeUTF(this.message);
	    			objOutput.flush();
	    			this.requestedMessage = false;
	    		}catch (Exception e){
	    			e.printStackTrace();
	    		}
	    		LOCK.notifyAll();
	    	}
		}
	    private void receiveFromClient(){
    		try{
    			if(objInput.available() > 0){
    				clientAction = objInput.readUTF();
    				System.out.println(clientAction);
    			}	    		
	    	}catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    /*
	     * this method return the client action to the game controller
	     */
	    public String sendMessage(){
	    	while(clientAction.equals("")){
	    		try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	String stringToSet = new String(clientAction);
	    	clientAction = "";
	    	return stringToSet;
	    }
}