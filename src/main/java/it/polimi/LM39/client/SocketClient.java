package it.polimi.LM39.client;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.server.NetworkPlayer;


/**
 * 
 */
public class SocketClient extends AbstractClient implements Runnable{
	private String ip;
	private Integer port;
	private String userName;
	private Socket socket;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;


    /**
     * Default constructor
     * @throws IOException 
     * @throws UnknownHostException 
     */
    public SocketClient(String ip, int port, String userName, UserInterface UI) throws UnknownHostException, IOException {
    	super(UI);
    	this.ip = ip;
    	this.port = port;
    	this.userName = userName;
    	socket = new Socket(ip,port);
    	socket.setKeepAlive(true);	
    	socket.setSoTimeout(500);
    	socketOut = new ObjectOutputStream(socket.getOutputStream());
    	socketOut.flush();
    	socketIn = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream()));  
    	new Thread(this).start();
    }

    @Override
    public void run(){
    	Logger logger = Logger.getLogger(SocketClient.class.getName());
    	Object test;
    	NetworkPlayer player = null;
    		while (true){	
    			try{
    				while(socketIn.read() != 0){
    					test = socketIn.readObject();
    					if(test instanceof NetworkPlayer)
    						player = (NetworkPlayer) test;
    					if(test instanceof MainBoard)
    						UI.setCurrentMainBoard((MainBoard)test);
    					if(test instanceof Boolean)
                			UI.printMessage(new String(socketIn.readUTF()));   					
    				}
    			}catch (IOException | ClassNotFoundException socketException) {
    				if(socketException instanceof SocketTimeoutException){
    					try {
    						socketOut.writeUTF(UI.askClient(player));
    						socketOut.flush();
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

