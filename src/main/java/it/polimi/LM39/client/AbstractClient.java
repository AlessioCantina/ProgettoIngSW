package it.polimi.LM39.client;

/**
 * abstract class which will be extended by CLI and GUI
 * provides basic constructor and getter
 *
 */
public abstract class AbstractClient {

    protected UserInterface UI;
    
    public AbstractClient(UserInterface UI){
    	this.UI = UI;
    }
    public UserInterface getUI(){
    	return this.UI;
    }

}