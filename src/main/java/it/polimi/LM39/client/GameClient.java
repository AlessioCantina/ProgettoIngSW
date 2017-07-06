package it.polimi.LM39.client;


/**
 * 
 */
public class GameClient extends UIcontroller {

    /**
     * Default constructor
     */
    public GameClient(UserInterface ui, AbstractClient client) {
    	this.ui = ui;
    	this.client = client;
    }

    /**
     * 
     */
    private UserInterface ui;


    /**
     * 
     */
    private AbstractClient client;

}