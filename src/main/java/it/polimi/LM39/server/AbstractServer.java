package it.polimi.LM39.server;

import java.util.*;

/**
 * 
 */
public abstract class AbstractServer {

	private ServerInterface serverController;
    /**
     * Default constructor
     */
    public AbstractServer(ServerInterface serverController){
    	this.serverController = serverController;
    }

    /**
     * 
     */

    protected ServerInterface getServerController(){
    	return this.serverController;
    }
    /**
     * 
     */
    public void StartServer(Integer port) {
    }

}