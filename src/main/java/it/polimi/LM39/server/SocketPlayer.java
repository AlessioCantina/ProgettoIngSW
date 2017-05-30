package it.polimi.LM39.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 */
public class SocketPlayer extends NetworkPlayer implements Runnable{
	    private Socket socket;
	    private ServerInterface serverInterface;
	    private ObjectInputStream objInput;
	    private PrintWriter stringOutput;
	    public SocketPlayer(ServerInterface serverInterface, Socket socket) throws IOException {
	          this.socket = socket;
	          this.serverInterface = serverInterface;
	          this.objInput = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream()));
	          this.stringOutput = new PrintWriter(this.socket.getOutputStream(),true); 
	    }
	    public void run() {
	        try {
	        	while(true){
	        		Object obj = objInput.readObject();
	        		String string = objInput.readUTF();
	        		//TODO: metodo che gestisce l'oggetto più la stringa inviata dal server
	        	}
	        }catch (Exception e) {
	            return;
	        }

	    }
}