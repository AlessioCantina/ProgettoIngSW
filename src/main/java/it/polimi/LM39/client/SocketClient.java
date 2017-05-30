package it.polimi.LM39.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.*;

import it.polimi.LM39.model.MainBoard;


/**
 * 
 */
public class SocketClient extends AbstractClient {
	private String ip;
	private Integer port;
	private String userName;
	private Scanner scanner;
	private Socket socket;
	private PrintWriter socketOut;
	private ObjectInputStream socketIn;
	private static ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * Default constructor
     * @throws IOException 
     * @throws UnknownHostException 
     */
    public SocketClient(String ip, int port, String userName) throws UnknownHostException, IOException {
    	this.ip = ip;
    	this.port = port;
    	this.userName = userName;
    	scanner = new Scanner(System.in);
    	socket = new Socket(ip,port);
    	socket.setKeepAlive(true);	
    	socketOut = new PrintWriter(socket.getOutputStream());
    	socketIn = new ObjectInputStream(socket.getInputStream());
    	executor.submit(new SocketHandler(this.socket));
    }
    public void sendMessage() {
    	String message = scanner.nextLine();	//print to cli available actions then grab the user's choice
		try {
			socketOut = new PrintWriter(socket.getOutputStream());
			socketOut.println(message);
			socketOut.flush();
			socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 }