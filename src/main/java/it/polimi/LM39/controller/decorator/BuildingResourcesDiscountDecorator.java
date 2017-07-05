package it.polimi.LM39.controller.decorator;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.Building;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.server.NetworkPlayer;

/**
 * this class decorates DecoratedMethods with the decorator of resourcesForBuilding
 */
public class BuildingResourcesDiscountDecorator extends DecoratedMethodsDecorator{

	private CardResources resourcesDiscount;
	
	public BuildingResourcesDiscountDecorator (DecoratedMethods decoratedMethods, CardResources resourcesDiscount) {
		super(decoratedMethods);
		this.resourcesDiscount = resourcesDiscount;
	}
	
	/**
	 * this method override 
	 */
	@Override
	public void resourcesForBuilding(NetworkPlayer player ,Building building) throws NotEnoughResourcesException{
			//creating a CardResources object that is the result of the card costs - the bonus  
			CardResources resources = new CardResources();
			if(resourcesDiscount.stones > 0 && resourcesDiscount.woods > 0){
				player.setMessage("Do you want a discount of " + resourcesDiscount.stones + " stones or " + resourcesDiscount.woods + " woods?\n Respond woods or stones" );
	    		String response = player.sendMessage();
	    		if(("stones").equals(response)){
	    			if(building.costResources.stones>=resourcesDiscount.stones)
	        			resources.stones= building.costResources.stones - resourcesDiscount.stones;
	        		else
	        			resources.stones = 0;
	    			resources.woods= building.costResources.woods;
	    		}
	    		else if (("woods").equals(response)){	
		    		if(building.costResources.woods>=resourcesDiscount.woods)
		    			resources.woods= building.costResources.woods - resourcesDiscount.woods;
		    		else
		    			resources.woods = 0;
		    		resources.stones= building.costResources.stones;
	    		}
	    		else{
	    			player.setMessage("You must choose woods or stones");
	    			decoratedMethods.resourcesForBuilding(player,building);
	    			return;
	    		}
			}
			else{
				if(building.costResources.woods>=resourcesDiscount.woods)
	    			resources.woods= building.costResources.woods - resourcesDiscount.woods;
	    		else
	    			resources.woods = 0;
				if(building.costResources.stones>=resourcesDiscount.stones)
	    			resources.stones= building.costResources.stones - resourcesDiscount.stones;
	    		else
	    			resources.stones = 0;
				}
			if(building.costResources.coins>=resourcesDiscount.coins)
    			resources.coins= building.costResources.coins - resourcesDiscount.coins;
    		else
    			resources.coins = 0;
    		
    		if(building.costResources.servants>=resourcesDiscount.servants)
    			resources.servants= building.costResources.servants - resourcesDiscount.servants;
    		else
    			resources.servants=0;
			Building building2 = new Building();
			building2.costResources = resources;
			decoratedMethods.resourcesForBuilding(player,building2);
	}
	 
}

