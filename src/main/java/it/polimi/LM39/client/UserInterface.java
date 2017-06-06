package it.polimi.LM39.client;

import it.polimi.LM39.model.MainBoard;

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
    
    public abstract void printMessage(String message);

    /**
     * 
     */
    public void showGameWindow() {
        // TODO implement here
    }

}