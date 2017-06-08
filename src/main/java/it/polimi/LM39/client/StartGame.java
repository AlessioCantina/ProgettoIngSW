package it.polimi.LM39.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import it.polimi.LM39.model.MainBoard;

public class StartGame {

	public static void main (String[] args) throws NumberFormatException, IOException{
		int uiChoice;
		int connectionChoice;
		String ip;
		int port;
		String userName;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Welcome to Lorenzo il Magnifico! \nSelect the User Interface");
		do{
		System.out.println("1 - Command Line Interface");
		System.out.println("2 - Graphic User Interface");
		uiChoice = Integer.parseInt(input.readLine());
		}while(uiChoice != 1 && uiChoice != 2);
		do{
			System.out.println("Select the connection method:");
			System.out.println("1 - Socket");
			connectionChoice = Integer.parseInt(input.readLine());
		}while(connectionChoice != 1);
		System.out.println("Write the server IP:");
		ip = input.readLine();
		System.out.println("Write the server Port:");
		port = Integer.parseInt(input.readLine());
		System.out.println("Choose a username:");
		userName = input.readLine();
		if(uiChoice == 1 && connectionChoice == 1){
			CLI cli = new CLI();
			new SocketClient(ip,port,userName,cli);
		}
	}
}
