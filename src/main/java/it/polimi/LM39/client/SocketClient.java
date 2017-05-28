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
    	executor.submit(new SocketHandler(this.socket));
    }
    public void sendMessage() {
    	String message = scanner.nextLine();
		PrintWriter socketOut;
		try {
			socketOut = new PrintWriter(socket.getOutputStream());
			socketOut.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    public void sendMainboard(MainBoard mainBoard){		//can be extended to all objects
		try {
	         FileOutputStream fileOut = new FileOutputStream("mainboard.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(mainBoard);
	         out.close();
	         fileOut.close();
	         System.out.println("Serialized data is saved in mainboard.ser");
	      }catch(IOException i) {
	         i.printStackTrace();
	      }    	
    }
}