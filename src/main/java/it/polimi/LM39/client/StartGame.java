package it.polimi.LM39.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * game starter for the client, ask for IP,port,User Interface and connection and then instantiate the right client type
 */


public class StartGame {
	public static void main (String[] args) throws NumberFormatException, IOException{
		String ip;
		int port;
		SocketClient socketClient;
		String userName;
		String password;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Welcome to Lorenzo il Magnifico! \n");
		System.out.println("Write the server IP:");
		ip = input.readLine();
		System.out.println("Write the server Port:");
		port = Integer.parseInt(input.readLine());
		System.out.println("Choose a username:");
		userName = input.readLine();	
		System.out.println("Choose a password:");
		password = input.readLine();	
		CLI cli = new CLI();
		socketClient = new SocketClient("localhost",3421,userName,password,cli);	
		new Thread(socketClient).start();		
	}
}
