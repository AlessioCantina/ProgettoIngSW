package it.polimi.LM39.server;
import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.model.Player;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * every player connected to the socket has this object which manipulates connection between server and client
 * this is the server side, so we send objects and strings and receive strings from client
 */
public class SocketPlayer extends NetworkPlayer implements Runnable{
	    private Socket socket;
	    private ServerInterface serverInterface;
	    private ObjectInputStream objInput;		//change this to something that manipulates string only
	    private ObjectOutputStream stringOutput;
	    private String message;
	    private MainBoard mainBoard;
	    public SocketPlayer(ServerInterface serverInterface, Socket socket) throws IOException {
	          this.socket = socket;
	          this.serverInterface = serverInterface;
	          this.objInput = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream()));
	          this.stringOutput = new ObjectOutputStream(this.socket.getOutputStream()); 
	    }
	    private void setMessages(String message,MainBoard mainBoard){
	    	this.message = message;
	    	this.mainBoard = mainBoard;
	    }
	    public void run() {
	        try {
	        	while(true){
	        		String clientAction = objInput.readUTF();
	        		sendToController(clientAction);
	        	}
	        }catch (Exception e) {
	            return;
	        }

	    }
	    public void sendToClient(String message) throws IOException{
	    	stringOutput.writeObject(this.personalBoard);
	    	stringOutput.writeUTF(message);
	    	stringOutput.flush();
	    }
	    public void sendToClient(MainBoard mainBoard,String message) throws IOException{
	    	stringOutput.writeObject(this.personalBoard);
	    	stringOutput.writeObject(mainBoard);
	    	stringOutput.writeUTF(message);
	    	stringOutput.flush();
	    }
	    public String sendToController(String clientAction){
	    	return clientAction;
	    }
	    public void receiveFromController(String message, MainBoard mainBoard){
	    	setMessages(message,mainBoard);
	    }
}