package it.polimi.LM39.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
	private static String clientResponse = "";
	private ExecutorService executor = Executors.newCachedThreadPool(); 

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
    	socketOut = new ObjectOutputStream(socket.getOutputStream());
    	socketOut.flush();
    	socketIn = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream()));  
    	executor.submit(this);
    }
    public static void setClientResponse(String response){
    	clientResponse = new String(response);
    	System.out.println("clientresponse" + clientResponse);
    }
    @Override
    public void run(){
    	Logger logger = Logger.getLogger(SocketClient.class.getName());
    	Object test;
    	NetworkPlayer player = null;
    		logger.log(Level.INFO,"started sockethandler");
    		while (true){	
    			try{
    				System.out.println(socketIn.read());
    				if(socketIn.read() != 0){
    					test = socketIn.readObject();
    					if(test instanceof NetworkPlayer)
    						player = (NetworkPlayer) test;
    					else if(test instanceof MainBoard)
    						UI.setCurrentMainBoard((MainBoard)test);
    					else if(test instanceof Boolean){
                			UI.printMessage(player,socketIn.readUTF());
    					}
    				}
    				if(!clientResponse.equals(""))
    				{
        				socketOut.writeUTF(clientResponse);
        				socketOut.flush();
    				}
    			}catch (Exception e) {
    				logger.log(Level.SEVERE, "Error instantiating socketstreams", e);
    			}
    		} 			
    }
}

