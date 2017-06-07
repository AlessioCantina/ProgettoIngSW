package it.polimi.LM39.controller;

import java.util.ArrayList;

import it.polimi.LM39.exception.FailedToReadFileException;
import it.polimi.LM39.exception.FailedToRegisterEffectException;
import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.model.Player;
import it.polimi.LM39.server.NetworkPlayer;
import it.polimi.LM39.server.Room;

/**
 * 
 */
public class Game {

    /**
     * Default constructor
     */
    public Game(Integer playerNumber, ArrayList<NetworkPlayer> players) {
    	this.playerNumber = playerNumber;
    	this.players = players;
    	gameHandler = new GameHandler();
    }
    
    private GameHandler gameHandler;
    private int playerNumber;

    /**
     * 
     */
    private ArrayList<NetworkPlayer> players = new ArrayList<NetworkPlayer>();

    /**
     * 
     */
    public Integer timeOutStart;

    /**
     * 
     */
    public Integer timeOutMove;

    public void initialize() throws FailedToReadFileException, FailedToRegisterEffectException{
    	if(playerNumber > 2)
    		gameHandler.harvestAndProductionSize = 4;
    	else
    		gameHandler.harvestAndProductionSize = 1;
    	if(playerNumber > 3)
    		gameHandler.marketSize = 4;
    	else
    		gameHandler.marketSize = 2;
    	gameHandler.setPeriod(1);
    	gameHandler.setRound(1);
    	gameHandler.initializeTheGame();
    }
    
    public void chooseAnAction(NetworkPlayer player){
    	
    }

   //TODO handle SkipFirstTurn Excommunication
   //TODO players choose leader card
   //TODO players choose Personal Bonus Tile










    /**
     * @return
     */
    public static void main(String[] args) {
        // TODO implement here
    	
    	
    	//code to test the method loadCardsOnTheMainBoard();
    	/*
    	MainBoard mainBoard = new MainBoard();
        GameHandler gameHandler = new GameHandler();
        gameHandler.mainBoard = mainBoard;
        //calls the gsonReader to populate the hashmaps
        gameHandler.initializeTheGame();
        gameHandler.loadCardsOnTheMainBoard();
        String[][] cards = gameHandler.cardNameOnTheMainBoard();
        for(int i=0;i<4;i++){
            for (int j=0; j<4; j++){
            	System.out.println(cards[j][i]);
            }
            }
        System.out.println();
        gameHandler.setRound(2);
        gameHandler.loadCardsOnTheMainBoard();
        cards = gameHandler.cardNameOnTheMainBoard();
        for(int i=0;i<4;i++){
            for (int j=0; j<4; j++){
            	System.out.println(cards[j][i]);
            }
            } */
    } 

}