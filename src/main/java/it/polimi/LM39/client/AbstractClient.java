package it.polimi.LM39.client;

/**
 * 
 */
public abstract class AbstractClient {

    private UserInterface clientUI;
    
    public AbstractClient(UserInterface UI){
    	this.clientUI = UI;
    }
    public UserInterface getUI(){
    	return this.clientUI;
    }

    /**
     * 
     */
    public void connect() {
        // TODO implement here
    }

}