package it.polimi.LM39.server;

import java.io.IOException;
import it.polimi.LM39.exception.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * socketserver class, composed by a thread which will listen to the selected port from client requests
 */
public class SocketServer extends AbstractServer implements Runnable{

	Logger logger = Logger.getLogger(SocketServer.class.getName());
	private ServerSocket serverSocket;
    public SocketServer(ServerInterface serverInterface) {
    	super(serverInterface);
    }
    /*
     * method called by the server to istantiate the socket server
     * 
     */
    @Override
    public void StartServer(Integer socketPort) throws ServerStartException{
    	try{
    		serverSocket = new ServerSocket(socketPort);
    		new Thread(this).start();
    	} catch(Exception e){
    		logger.log(Level.SEVERE, "Can't launch socket server", e);
    	}
    }
    /*
     * listen clients for connect requests
     */
    @Override
	public void run(){  	
		while(true){
			try{
				Socket socket = serverSocket.accept();
				SocketPlayer socketPlayer = new SocketPlayer(getServerController(),socket);
				System.out.println("Socket Listener up");
				new Thread(socketPlayer).start();
			}catch (IOException e) {
                logger.log(Level.SEVERE, "Can't instantiate SocketListener", e);
		}
	}
    }
}

