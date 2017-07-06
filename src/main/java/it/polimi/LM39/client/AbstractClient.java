package it.polimi.LM39.client;

/**
 * abstract class which will be extended by Socket Player
 * provides basic constructor and getter
 *
 */
public abstract class AbstractClient {

    protected UserInterface ui;
    
    public AbstractClient(UserInterface ui){
    	this.ui = ui;
    }
    public UserInterface getUI(){
    	return this.ui;
    }

}