package it.polimi.LM39.controller;

import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.model.Player;
import it.polimi.LM39.server.Room;

/**
 * 
 */
public class Game {

    /**
     * Default constructor
     */
    public Game(Integer playerNumber) {
    }

    /**
     * 
     */
    public int DIM_MARKET2or3player;

    /**
     * 
     */
    public int DIM_MARKET4player;

    /**
     * 
     */
    public int DIM_PRODUCTIONandHARVEST2player;

    /**
     * 
     */
    public int DIM_PRODUCTIONandHARVEST3or4player;

    /**
     * 
     */
    public Player[] player = new Player[4];

    /**
     * 
     */
    public Integer timeOutStart;

    /**
     * 
     */
    public Integer timeOutMove;

 
    

    /**
     * 
     */

    /**
     * 
     */
    public Room room;













    /**
     * @return
     */
    public static void main(String[] args) {
        // TODO implement here
    	
    	
    	//code to test the method loadCardsOnTheMainBoard();
    	/*
    	MainBoard mainBoard = new MainBoard();
        GameHandler gameHandler = new GameHandler(mainBoard);
        //calls the gsonReader to populate the hashmaps
        gameHandler.inizializeTheGame();
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