package it.polimi.LM39.server;


/**
 *  abstract class extended by socket server
 */
public abstract class AbstractServer{

	private ServerInterface serverController;
    public AbstractServer(ServerInterface serverController){
    	this.serverController = serverController;
    }
    protected ServerInterface getServerController(){
    	return this.serverController;
    }
    /**
     * start the server
     * @param port
     */
    public abstract void startServer(Integer port);
}