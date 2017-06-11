package it.polimi.LM39.client;

/**
 * 
 */
public abstract class AbstractClient {

	protected static long playerMoveTimeout = 1000;
	public void setPlayerMoveTimeout(long playerMoveTime){
		playerMoveTimeout = playerMoveTime;
	}
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