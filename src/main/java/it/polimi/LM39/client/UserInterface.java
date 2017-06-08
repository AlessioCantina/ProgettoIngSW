package it.polimi.LM39.client;

import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.server.NetworkPlayer;

/**
 * 
 */
public abstract class UserInterface {

    /**
     * Default constructor
     */
    public UserInterface() {
    }

    /**
     * 
     */
    private UIcontroller controller;



    /**
     * 
     */
    public abstract void printMainBoard(MainBoard mainBoard);
    
    public abstract String printMessage(NetworkPlayer player, String message,MainBoard mainBoard);

    /**
     * 
     */
    public void showGameWindow() {
        // TODO implement here
    }

}