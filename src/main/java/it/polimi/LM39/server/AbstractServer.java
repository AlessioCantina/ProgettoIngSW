package it.polimi.LM39.server;


/**
 *  abstract class extended by RMI and socket servers
 */
public abstract class AbstractServer{

	private ServerInterface serverController;
    public AbstractServer(ServerInterface serverController){
    	this.serverController = serverController;
    }
    protected ServerInterface getServerController(){
    	return this.serverController;
    }
    public abstract void StartServer(Integer port);
}