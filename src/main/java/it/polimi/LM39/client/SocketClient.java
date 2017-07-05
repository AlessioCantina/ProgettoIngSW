package it.polimi.LM39.client;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.server.NetworkPlayer;


/*
 * socket client: enables the socket in/out for the client
 */
public class SocketClient extends AbstractClient implements Runnable{
	private String userName;
	private Socket socket;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;


    /*
	 * set the socket properties and initialize the stream
     */
    public SocketClient(String ip, int port, String userName, UserInterface UI) throws UnknownHostException, IOException {
    	super(UI);
    	this.userName = userName;
    	socket = new Socket(ip,port);
    	socketOut = new ObjectOutputStream(socket.getOutputStream());
    	socketOut.flush();
    	socketIn = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream()));  
    }

    /*
     * infinite loop which listen to socket server, when there is no more data on the socket
     * asks client for an input. 
     * 
     */
    @Override
    public void run(){
    	Logger logger = Logger.getLogger(SocketClient.class.getName());
    	Object objectGrabber;
    	NetworkPlayer player = null;
    	try {
			socketOut.writeUTF(userName);
	    	socketOut.flush();
	    	UI.setMoveTimeout(socketIn.readLong());
		} catch (IOException e1) {
			logger.log(Level.SEVERE, "Can't write on socket");
		}
    		while (!socket.isClosed()){	
    			try{
    				while(socketIn.read() != 0){
    					objectGrabber = socketIn.readObject();
    					if(objectGrabber instanceof NetworkPlayer)
    						player = (NetworkPlayer) objectGrabber;
    					if(objectGrabber instanceof MainBoard){
    						UI.setCurrentMainBoard((MainBoard)objectGrabber);
    					}	
    					if(objectGrabber instanceof Boolean){
    						if((Boolean)objectGrabber)
    							socket.setSoTimeout(500);
    						UI.printMessage((socketIn.readUTF()));
    					}
    				}
    			}catch (SocketTimeoutException socketException) {
    				try {
    						socketOut.writeUTF(UI.askClient(player));
    						socketOut.flush();
    						socket.setSoTimeout(0);
    				} catch (IOException writeException) {
    					logger.log(Level.SEVERE, "Can't write on socket", writeException);
    				}
    			}catch (ClassNotFoundException e) {
    				logger.log(Level.SEVERE, "Object class not found", e);
    			}catch (IOException e) {
    				logger.log(Level.INFO, "Nickname already choosen", e);
    				try {
    					Thread.currentThread().join();
    				}catch (InterruptedException e1) {
    					Thread.currentThread().interrupt();
    				}
				} 			
    		}
    	}
}

