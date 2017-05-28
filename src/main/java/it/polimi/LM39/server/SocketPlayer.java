package it.polimi.LM39.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 
 */
public class SocketPlayer extends InterfacePlayer implements Runnable{
	    private Socket socket;
	    private Room room;
	    public SocketPlayer(Socket socket, Room room) {
	          this.socket = socket;
	          this.room = room;
	    }
	    public void run() {
	    	InputStream input;
	        BufferedReader buffReader;
	        DataOutputStream output;
	        room.connect(this);
	        try {
	            input = socket.getInputStream();
	            buffReader = new BufferedReader(new InputStreamReader(input));
	            output = new DataOutputStream(socket.getOutputStream());
	        } catch (IOException e) {
	            return;
	        }
	        while (true) {
	            try {
	                String line = buffReader.readLine();
	                if ((line == null) || line.equalsIgnoreCase("DISCONNECT")) {
	                    socket.close();
	                    return;
	                } else {
	                    output.writeBytes(line + "\n\r");
	                    output.flush();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	                return;
	            }
	        }
	    }
}