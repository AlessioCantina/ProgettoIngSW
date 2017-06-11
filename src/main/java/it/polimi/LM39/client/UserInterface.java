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
    
    public abstract void printMessage(String message);
    
    
    public abstract void setCurrentMainBoard(MainBoard mainBoard);
    
    public abstract String askClient(NetworkPlayer player);




}