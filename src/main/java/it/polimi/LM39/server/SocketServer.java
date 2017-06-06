package it.polimi.LM39.server;

import java.io.IOException;
import it.polimi.LM39.exception.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * socketserver class, composed by a thread which will listen to the selected port from client requests
 */
public class SocketServer extends AbstractServer implements Runnable{

	private ServerSocket serverSocket;
	private ExecutorService executor = Executors.newCachedThreadPool();
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
    		executor.submit(this);
    	} catch(Exception e){
    		throw new ServerStartException(e);
    	}
    }
    /*
     * listen clients for connect requests
     */
    @Override
	public void run(){
    	Logger logger = Logger.getLogger(SocketServer.class.getName());
		while(true){
			try{
				System.out.println("Socket Listener up");
				Socket socket = serverSocket.accept();
				SocketPlayer socketPlayer = new SocketPlayer(getServerController(),socket);
				new Thread(socketPlayer).start();
			}catch (IOException e) {
                logger.log(Level.SEVERE, "Can't instantiate SocketListener", e);
		}
	}
    }
}

