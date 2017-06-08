package it.polimi.LM39.client;

/**
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

    /**
     * 
     */
    public void connect() {
        // TODO implement here
    }

}