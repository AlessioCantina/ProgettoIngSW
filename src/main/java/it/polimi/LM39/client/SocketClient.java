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
	private String ip;
	private Integer port;
	private String userName;
	private Socket socket;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;
	private long clientTimeout = 1000000;

	public void setClientTimeout(long timeout){
		this.clientTimeout = timeout;
	}

    /*
	 * set the socket properties and initialize the stream
     */
    public SocketClient(String ip, int port, String userName, UserInterface UI) throws UnknownHostException, IOException {
    	super(UI);
    	this.ip = ip;
    	this.port = port;
    	this.userName = userName;
    	socket = new Socket(ip,port);
    	socket.setKeepAlive(true);	
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
    						long moveStartTime = System.currentTimeMillis();
    						socketOut.writeUTF(UI.askClient(player));
    						if(System.currentTimeMillis() - moveStartTime < clientTimeout){
    							socketOut.flush();
    							socket.setSoTimeout(0);
    						}
    						else{
    							UI.printMessage("Client timedout. Please reconnect to play again");
    							socketOut.writeUTF("Client timedout");
    							socketOut.flush();
    							socket.close();
    						}
    				}catch (IOException writeException) {
    						logger.log(Level.SEVERE, "Can't write on socket", writeException);
    				}
    			} catch (ClassNotFoundException e) {
    				logger.log(Level.SEVERE, "Object class not found", e);
				} catch (IOException e) {
					logger.log(Level.SEVERE, "Can't write on socket", e);
				} 			
    		}
    	}
}

