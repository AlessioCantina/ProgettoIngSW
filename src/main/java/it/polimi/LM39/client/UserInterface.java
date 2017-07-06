package it.polimi.LM39.client;

import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.server.NetworkPlayer;

/**
 * abstract class extended by CLI which provides basic methods that will be used by socketclient
 */
public interface UserInterface {

    public abstract void setMoveTimeout(Long timeout);
    
    public abstract void printMessage(String message);
        
    public abstract void setCurrentMainBoard(MainBoard mainBoard);
    
    public abstract String askClient(NetworkPlayer player);

}