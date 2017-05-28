package it.polimi.LM39.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 */
public class SocketServer extends AbstractServer{

	private Integer port;
    public SocketServer(Integer port) {
    	this.port = port;
    }

    public void startSocketServer() {
    	ServerSocket serverSocket = null;
    	Socket socket = null;
    	Room room = new Room();
    	try{
    		serverSocket = new ServerSocket(port);
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	}
    	while(true){
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            new SocketPlayer(socket,room).run();
    	}
    }



}