package it.polimi.LM39.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * 
 */
public class SocketClient extends AbstractClient implements Runnable{
	private String ip;
	private Integer port;
	private String userName;
	private Scanner scanner;
	private Socket socket;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;
	private ExecutorService executor = Executors.newCachedThreadPool(); 

    /**
     * Default constructor
     * @throws IOException 
     * @throws UnknownHostException 
     */
    public SocketClient(String ip, int port, String userName) throws UnknownHostException, IOException {
    	this.ip = ip;
    	this.port = port;
    	this.userName = userName;
    	socket = new Socket(ip,port);
    	socket.setKeepAlive(true);	
    	socketOut = new ObjectOutputStream(socket.getOutputStream());
    	socketOut.flush();
    	socketIn = new ObjectInputStream(socket.getInputStream());  
    	executor.submit(this);
    }
    @Override
    public void run(){
    	Logger logger = Logger.getLogger(SocketClient.class.getName());
    		System.out.println("started sockethandler");
    		while (true){	
    			scanner = new Scanner(System.in);
    			System.out.println("scanner ready");
    			String string = scanner.next();
    			System.out.println(string);
    			try {
    				socketOut.writeUTF(string);
    				socketOut.flush();
    			}catch (IOException e) {
    				logger.log(Level.SEVERE, "Error instantiating socketstreams", e);
    			} 
    		} 			
    }
}

