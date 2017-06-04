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

public class BuildingResourcesDiscountDecorator extends GameHandler{

	private GameHandler decoratedGameHandler;
	private CardResources resourcesDiscount;
	private NetworkPlayer player;
	
	public BuildingResourcesDiscountDecorator (GameHandler decoratedGameHandler, CardResources resourcesDiscount, NetworkPlayer player) {
		this.decoratedGameHandler = decoratedGameHandler;
		this.resourcesDiscount = resourcesDiscount;
		this.player = player;
	}
	
	
	@Override
	public void resourcesForBuilding(NetworkPlayer player ,Building building) throws NotEnoughResourcesException{
		if(this.player == player){
			//creating a CardResources object that is the result of the card costs - the bonus  
			CardResources resources = new CardResources();
    		if(building.costResources.coins>=resourcesDiscount.coins)
    			resources.coins= building.costResources.coins - resourcesDiscount.coins;
    		else
    			resources.coins = 0;
    		if(building.costResources.stones>=resourcesDiscount.stones)
    			resources.stones= building.costResources.stones - resourcesDiscount.stones;
    		else
    			resources.stones = 0;
    		if(building.costResources.woods>=resourcesDiscount.woods)
    			resources.woods= building.costResources.woods - resourcesDiscount.woods;
    		else
    			resources.woods = 0;
    		if(building.costResources.servants>=resourcesDiscount.servants)
    			resources.servants= building.costResources.servants - resourcesDiscount.servants;
    		else
    			resources.servants=0;
			Building building2 = new Building();
			building.costResources = resources;
			decoratedGameHandler.resourcesForBuilding(player,building2);
		}
		//if the bonus is not for the player that is now using this method
		decoratedGameHandler.resourcesForBuilding(player,building);
	}
	 
	 
	@Override
	public void coinsForCharacter(NetworkPlayer player ,Character character) throws NotEnoughResourcesException{
		decoratedGameHandler.coinsForCharacter(player,character);
	}
	
	@Override
	 public void resourcesForVenture(NetworkPlayer player ,Venture venture) throws NotEnoughResourcesException{
		decoratedGameHandler.resourcesForVenture(player,venture);
	}
	
	@Override
	public void addCardResources (CardResources resources, NetworkPlayer player) throws NotEnoughResourcesException{
		decoratedGameHandler.addCardResources (resources,player);
	}
	
	@Override
	public boolean addFamilyMemberToTheMarket(FamilyMember familyMember, Integer position, NetworkPlayer player) throws IOException, NotEnoughResourcesException, NotEnoughPointsException {
		return decoratedGameHandler.addFamilyMemberToTheMarket(familyMember, position, player);
	}
	
	@Override
	public void addCardPoints (CardPoints points, NetworkPlayer player) throws NotEnoughPointsException{
		decoratedGameHandler.addCardPoints(points, player);
	}
	
	@Override
	public Integer addServants(NetworkPlayer player) throws IOException, NotEnoughResourcesException{
		return decoratedGameHandler.addServants(player);
	}
	
}

