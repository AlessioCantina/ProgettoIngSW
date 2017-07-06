package it.polimi.LM39.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.LM39.exception.ClientTimedOutException;
import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.server.NetworkPlayer;


/**
 * socket client: enables the socket in/out for the client
 */
public class SocketClient extends AbstractClient implements Runnable{
	private Socket socket;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;
	private Logger logger = Logger.getLogger(SocketClient.class.getName());

	/**
	 * set the client parameters (ip,port and UI) and send username and password to the server
	 * @param ip
	 * @param port
	 * @param userName
	 * @param password
	 * @param UI
	 * @throws UnknownHostException
	 * @throws IOException
	 */
    public SocketClient(String ip, int port, String userName,String password, UserInterface ui) throws IOException {
    	super(ui);
    	socket = new Socket(ip,port);
    	socket.setKeepAlive(true);
    	socketOut = new ObjectOutputStream(socket.getOutputStream());
    	socketOut.flush();
    	socketIn = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream()));  
    	initializeClient(userName,password);
    }
    /**
     * support method for the constructor, sends username and password to the server
     * and receive the move timeout from the server
     * @param userName
     * @param password
     */
    private void initializeClient(String userName,String password){
    	try {
			socketOut.writeUTF(userName);
			socketOut.writeUTF(password);
	    	socketOut.flush();
	    	ui.setMoveTimeout(socketIn.readLong());
		} catch (IOException e1) {
			logger.log(Level.SEVERE, "Can't write on socket");
		}
    }
    /**
     * infinite loop which listen to socket server, when there is no more data on the socket
     * asks client for an input. 
     * 
     */
    @Override
    public void run(){
    	Object objectGrabber;
    	NetworkPlayer player = null;
    		while (!socket.isClosed()){	
    			try{
    				while(socketIn.read() != 0){
    					objectGrabber = socketIn.readObject();
    					if(objectGrabber instanceof NetworkPlayer)
    						player = (NetworkPlayer) objectGrabber;
    					if(objectGrabber instanceof MainBoard){
    						ui.setCurrentMainBoard((MainBoard)objectGrabber);
    					}	
    					if(objectGrabber instanceof Boolean){
    						if((Boolean)objectGrabber)
    							break;
    						ui.printMessage(socketIn.readUTF());
    					}
    				}
    				String response = ui.askClient(player);
					socketOut.writeUTF(response);
					socketOut.flush();
    				if(("timeout").equals(response)){
    					throw new ClientTimedOutException("Client Timedout");
    				}

    			}catch (ClassNotFoundException e) {
    				logger.log(Level.SEVERE, "Object class not found", e);
    			}catch (IOException e) {
    				logger.log(Level.INFO, "Network Error", e);
    				try {
    					Thread.currentThread().join();
    				}catch (InterruptedException e1) {
    					Thread.currentThread().interrupt();
    				}
				} catch (ClientTimedOutException e) {
					logger.log(Level.INFO, "Client timedout. Please reconnect to continue playing");
					break;
				} 			
    		}
    	}
}

