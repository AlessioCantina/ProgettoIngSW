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

public class VentureResourcesDiscountDecorator extends GameHandler{

	private GameHandler decoratedGameHandler;
	private CardResources resourcesDiscount;
	private NetworkPlayer player;
	
	public VentureResourcesDiscountDecorator (GameHandler decoratedGameHandler, CardResources resourcesDiscount, NetworkPlayer player) {
		this.decoratedGameHandler = decoratedGameHandler;
		this.resourcesDiscount = resourcesDiscount;
		this.player = player;
	}


	@Override
	public boolean getVentureCard(Venture venture,NetworkPlayer player,Integer cardNumber) throws IOException{
    	ArrayList<Integer> possessedVentures = player.personalBoard.getPossessions("Venture");
    	Integer choice = 0;
		if (possessedVentures.size()<6){
	    	if(venture.costMilitary!=0 && (venture.costResources.coins!=0 || venture.costResources.woods!=0 || venture.costResources.stones!=0 || venture.costResources.servants!=0)) {
	    		//ask to the player what payment he wants to do
	    		player.setMessage("What payment do you want to do? 1 or 2");
	    		//get the player response
	    		choice = Integer.parseInt(player.sendMessage());
	    	}
	    	if(venture.costMilitary==0 || choice == 2){
	    		CardResources resources = new CardResources();
	    		if(venture.costResources.coins>=resourcesDiscount.coins)
	    			resources.coins= venture.costResources.coins - resourcesDiscount.coins;
	    		if(venture.costResources.stones>=resourcesDiscount.stones)
	    			resources.stones= venture.costResources.stones - resourcesDiscount.stones;
	    		if(venture.costResources.woods>=resourcesDiscount.woods)
	    			resources.woods= venture.costResources.woods - resourcesDiscount.woods;
	    		if(venture.costResources.servants>=resourcesDiscount.servants)
	    			resources.servants= venture.costResources.servants - resourcesDiscount.servants;
	    		try {
	    			subCardResources(resources,player);
	    		} catch (NotEnoughResourcesException e1) {
	    			e1.printStackTrace();
	    			player.setMessage("You don't have enough resources!");
	    			return false;
				}
	    	}
	    
	    	else if((venture.costMilitary!=0 || choice ==1) && (player.points.getMilitary() >= venture.neededMilitary)){
	    		try {
	    			player.points.setMilitary(-venture.costMilitary);
	    		} catch (NotEnoughPointsException e) {
	    			e.printStackTrace();
	    			player.setMessage("You don't have enough military points");
	    			return false;
	    		}
	    	}
	    	else{
	    		player.setMessage("You don't have enough military points");
    			return false;
    			}
	    
	    	possessedVentures.add(cardNumber);
	    	player.personalBoard.setPossessions(possessedVentures,"Venture");
	    	player.points.setFinalVictory(venture.finalVictory);
	    	ventureHandler.doInstantEffect(venture.instant, player);
	    	return true;
	   }
		else
			player.setMessage("You can't have more than 6 ventures!");
    	return false;
    }
	
	
	@Override
	public void resourcesForVenture(NetworkPlayer player ,Venture venture) throws NotEnoughResourcesException{
		if(this.player == player){
			//creating a CardResources object that is the result of the card costs - the bonus  
			CardResources resources = new CardResources();
    		if(venture.costResources.coins>=resourcesDiscount.coins)
    			resources.coins= venture.costResources.coins - resourcesDiscount.coins;
    		if(venture.costResources.stones>=resourcesDiscount.stones)
    			resources.stones= venture.costResources.stones - resourcesDiscount.stones;
    		if(venture.costResources.woods>=resourcesDiscount.woods)
    			resources.woods= venture.costResources.woods - resourcesDiscount.woods;
    		if(venture.costResources.servants>=resourcesDiscount.servants)
    			resources.servants= venture.costResources.servants - resourcesDiscount.servants;
			subCardResources(resources,player);
		}
		//if the bonus is not for the player that is now using this method
		decoratedGameHandler.resourcesForVenture(player,venture);
	}
	 
	 
	@Override
	public void coinsForCharacter(NetworkPlayer player ,Character character) throws NotEnoughResourcesException{
		decoratedGameHandler.coinsForCharacter(player,character);
	}
	
	@Override
	public void resourcesForBuilding(NetworkPlayer player, Building building) throws NotEnoughResourcesException{
		decoratedGameHandler.resourcesForBuilding(player,building);
	}
}
