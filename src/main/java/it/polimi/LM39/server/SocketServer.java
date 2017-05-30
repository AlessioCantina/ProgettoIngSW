package it.polimi.LM39.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 */
public class SocketServer extends AbstractServer{

	private ServerSocket serverSocket;
    public SocketServer(ServerInterface serverInterface) {
    	super(serverInterface);
    }
    
    @Override
    public void StartServer(Integer socketPort){
    	try{
    		serverSocket = new ServerSocket(socketPort);
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
    				System.out.println("Socket Listener UP");
    				Socket socket = serverSocket.accept();
    				SocketPlayer socketPlayer = new SocketPlayer(getServerController(),socket);
    			}catch (IOException e) {
                    System.out.println(e.getMessage());
    		}
    	}
    }
    }
}