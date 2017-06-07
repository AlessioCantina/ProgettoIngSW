package it.polimi.LM39.controller.decorator;

import java.io.IOException;

import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.Building;
import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.Character;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.model.Venture;
import it.polimi.LM39.server.NetworkPlayer;

public class ServantsMalusDecorator extends GameHandler{

	private GameHandler decoratedGameHandler;
	private Integer servantsMalus;
	private NetworkPlayer player;
	
	public ServantsMalusDecorator (GameHandler decoratedGameHandler, Integer servantsMalus, NetworkPlayer player) {
		this.decoratedGameHandler = decoratedGameHandler;
		this.servantsMalus = servantsMalus;
		this.player = player;
	}
	
	@Override
	 public Integer addServants(NetworkPlayer player) throws IOException, NotEnoughResourcesException{
		if(this.player == player){
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
		else
			return decoratedGameHandler.addServants(player);
    }

	@Override
	public void addCardResources (CardResources resources, NetworkPlayer player) throws NotEnoughResourcesException, NotEnoughPointsException{
		decoratedGameHandler.addCardResources (resources,player);
	}
	
	@Override
	public void coinsForCharacter(NetworkPlayer player ,Character character) throws NotEnoughResourcesException{
		decoratedGameHandler.coinsForCharacter(player,character);
	}
		
	@Override
	public void resourcesForBuilding(NetworkPlayer player, Building building) throws NotEnoughResourcesException{
		decoratedGameHandler.resourcesForBuilding(player,building);
	}
		
	@Override
	public void resourcesForVenture(NetworkPlayer player ,Venture venture) throws NotEnoughResourcesException{
		decoratedGameHandler.resourcesForVenture(player,venture);
	}
	
	@Override
	public boolean addFamilyMemberToTheMarket(FamilyMember familyMember, Integer position, NetworkPlayer player) throws IOException, NotEnoughResourcesException, NotEnoughPointsException {
		return decoratedGameHandler.addFamilyMemberToTheMarket(familyMember, position, player);
	}
	
	@Override
	public void addCardPoints (CardPoints points, NetworkPlayer player) throws NotEnoughPointsException{
		decoratedGameHandler.addCardPoints(points, player);
	}
}
