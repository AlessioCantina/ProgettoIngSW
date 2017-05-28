package it.polimi.LM39.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SocketHandler implements Runnable{
	private Socket socket;
	
	public SocketHandler(Socket socket){
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			Scanner input = new Scanner(socket.getInputStream());
			while (true){		//need to implement a socket heartbeat where server informs client of his state
				String inputLine = input.nextLine();
				System.out.println(inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 	
	}

}

