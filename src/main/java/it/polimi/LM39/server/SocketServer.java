package it.polimi.LM39.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socketserver class, composed by a thread which will listen to the selected port from client requests
 */
public class SocketServer extends AbstractServer{

	private ServerSocket serverSocket;
    public SocketServer(ServerInterface serverInterface) {
    	super(serverInterface);
    }
    /*
     * method called by the server to istantiate the socket server
     * 
     */
    @Override
    public void StartServer(Integer socketPort){
    	try{
    		serverSocket = new ServerSocket(socketPort);
    		new SocketListener().start();
    	} catch(IOException e){
    		System.out.println(e.getMessage());
    	}
    }
    /*
     * listen clients for connect requests
     */
    private class SocketListener extends Thread{
    	@Override
    	public void run(){
    		while(true){
    			try{
    				System.out.println("Socket Listener up");
    				Socket socket = serverSocket.accept();
    				SocketPlayer socketPlayer = new SocketPlayer(getServerController(),socket);
    				new Thread(socketPlayer).start();
    			}catch (IOException e) {
                    System.out.println(e.getMessage());
    		}
    	}
    }
    }
}