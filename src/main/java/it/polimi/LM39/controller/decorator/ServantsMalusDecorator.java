package it.polimi.LM39.controller.decorator;

import java.io.IOException;
import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.server.NetworkPlayer;

public class ServantsMalusDecorator extends DecoratedMethodsDecorator{

	private Integer servantsMalus;
	
	public ServantsMalusDecorator (DecoratedMethods decoratedMethods, Integer servantsMalus) {
		super(decoratedMethods);
		this.servantsMalus = servantsMalus;
	}
	
	@Override
	 public Integer addServants(NetworkPlayer player) throws IOException, NotEnoughResourcesException{
			player.setMessage("Do you want to add servants? yes or no \n You have an Excommunication so if you add "+ servantsMalus + " your action will increase by 1");
	    	String response = player.sendMessage();
	    	response = GameHandler.checkResponse(response, player);
	    	if(("yes").equals(response)){
	    		player.setMessage("How many?");
	    		Integer qty = Integer.parseInt(player.sendMessage());
	    		player.resources.setServants(-qty);
	    		return (qty/servantsMalus);
	    	}
	    	else if (("no").equals(response))
	    		return 0;
	    	//unreachable code
	    	return null;
    }

}
