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
    	Object temp;
    	NetworkPlayer player = null;
    		while (true){	
    			try{
    				while(socketIn.read() != 0){
    					temp = socketIn.readObject();
    					if(temp instanceof NetworkPlayer)
    						player = (NetworkPlayer) temp;
    					if(temp instanceof MainBoard){
    						UI.setCurrentMainBoard((MainBoard)temp);
    					}	
    					if(temp instanceof Boolean){
                			UI.printMessage((socketIn.readUTF()));
                			socket.setSoTimeout(500);
    					}
    				}
    			}catch (IOException | ClassNotFoundException socketException) {
    				if(socketException instanceof SocketTimeoutException){
    					try {
    							socketOut.writeUTF(UI.askClient(player));
    							socketOut.flush();
    							socket.setSoTimeout(0);
    							
    					}catch (IOException writeException) {
    						logger.log(Level.SEVERE, "Can't write on socket", writeException);
    					}
    				}
    				else
    					logger.log(Level.SEVERE, "Socket exception", socketException);
    			} 			
    		}
    }
}

