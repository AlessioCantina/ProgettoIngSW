package it.polimi.LM39.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * game starter for the client, ask for IP,port, and start the game
 */


public class StartGame {
	public static void main (String[] args) throws NumberFormatException, IOException{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Welcome to Lorenzo il Magnifico! \n");
		System.out.println("Write the server IP:");
		String ip = input.readLine();
		int port = Integer.parseInt(input.readLine());
		System.out.println("Choose a username:");
		String userName = input.readLine();	
		System.out.println("Choose a password:");
		String password = input.readLine();	
		CLI cli = new CLI();
		SocketClient socketClient = new SocketClient(ip,port,userName,password,cli);	
		new Thread(socketClient).start();		
	}
}
