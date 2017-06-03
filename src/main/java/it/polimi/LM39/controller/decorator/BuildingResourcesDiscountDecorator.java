package it.polimi.LM39.controller.decorator;

import java.io.IOException;
import java.util.ArrayList;

import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.Building;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.Character;
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
	public boolean getBuildingCard(Building building,NetworkPlayer player,Integer cardNumber) throws IOException{
		if(this.player == player){
		ArrayList<Integer> possessedBuildings = player.personalBoard.getPossessions("Building");
		if (possessedBuildings.size()<6){
			CardResources resources = new CardResources();
	    		if(building.costResources.coins>=resourcesDiscount.coins)
	    			resources.coins= building.costResources.coins - resourcesDiscount.coins;
	    		if(building.costResources.stones>=resourcesDiscount.stones)
	    			resources.stones= building.costResources.stones - resourcesDiscount.stones;
	    		if(building.costResources.woods>=resourcesDiscount.woods)
	    			resources.woods= building.costResources.woods - resourcesDiscount.woods;
	    		if(building.costResources.servants>=resourcesDiscount.servants)
	    			resources.servants= building.costResources.servants - resourcesDiscount.servants;
	    			
				try {
					subCardResources(resources,player);
					addCardPoints(building.instantBonuses,player);
				} catch (NotEnoughResourcesException | NotEnoughPointsException e) {
					e.printStackTrace();
					return false;
				}
	    		possessedBuildings.add(cardNumber);
	    		player.personalBoard.setPossessions(possessedBuildings,"Building");
	    		return true;
	    }
		else
			player.setMessage("You can't have more than 6 buildings!");
    	return false;
    }
		else
			return decoratedGameHandler.getBuildingCard(building,player,cardNumber);
	}
	
	@Override
	public boolean getCharacterCard(Character character,NetworkPlayer player,Integer cardNumber) throws IOException{
		return decoratedGameHandler.getCharacterCard(character,player,cardNumber);
	}
	
	@Override
	public boolean getVentureCard(Venture venture,NetworkPlayer player,Integer cardNumber) throws IOException{
		return decoratedGameHandler.getVentureCard(venture,player,cardNumber);
	}
}

