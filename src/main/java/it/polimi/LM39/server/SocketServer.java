package it.polimi.LM39.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 */
public class SocketServer extends AbstractServer{

	private Integer port;
	private ServerSocket serverSocket;
    public SocketServer(ServerInterface serverInterface) {
    	super(serverInterface);
    }
    
    @Override
    public void startServer(){
    	try{
    		serverSocket = new ServerSocket(port);
    		new SocketListener().start();
    	} catch(IOException e){
    		System.out.println(e.getMessage());
    	}
    }
    
    private class SocketListener extends Thread{
    	@Override
    	public void run(){
    		while(true){
    			try{
    				Socket socket = serverSocket.accept();
    				SocketPlayer socketPlayer = new SocketPlayer(getServerController(),socket);
    			}catch (IOException e) {
                    System.out.println(e.getMessage());
    		}
    	}
    }
    }
}