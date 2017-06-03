package it.polimi.LM39.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.*;

import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.server.Server;


/**
 * 
 */
public class SocketClient extends AbstractClient {
	private String ip;
	private Integer port;
	private String userName;
	private Scanner scanner;
	private Socket socket;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;

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
    	new SocketHandler().start();
    }
    private class SocketHandler extends Thread {
    	
    	@Override
    	public void run() {		//output thread
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
    				throw new RuntimeException(e);
    			} 
    		} 			
    	}
    }
}
