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
	    private static String message;				//information which will be send to the client
	    private static MainBoard mainBoard;
	    private String clientAction;
	    private transient static boolean requestedMainboard;
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
	          requestedMainboard = false;
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
	    public void setMessage(String messages){
	    	synchronized(LOCK){
	    		message = new String(messages);
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
	    	synchronized(LOCK){
	    		try {
	    	    	this.serverInterface.joinRoom(this);
					LOCK.wait();
	    			System.out.println("THREAD UNLOCKED" + Thread.currentThread());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}
	    	System.out.println(Thread.activeCount());
	    	while(true){
	    		if(requestedMessage){
	    			sendMessageToClient();
	    		}
	    		if(requestedMainboard){
	    			sendMainBoardToClient();
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
        			requestedMainboard = false;
	    		}catch (Exception e) {
	    			e.printStackTrace();
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
	    			e.printStackTrace();
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
	            e.printStackTrace();
	        }
	    	}
	    }
	    /*
	     * this method return the client action to the game controller
	     */
	    public String sendMessage(){
	    	synchronized(CLIENT_LOCK){
	    		while(clientAction.equals("")){
	    			try {
						CLIENT_LOCK.wait();
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
}