package it.polimi.LM39.controller.decorator;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.Venture;
import it.polimi.LM39.server.NetworkPlayer;

/**
 * this class decorates DecoratedMethods with the decorator of resourcesForVenture
 */
public class VentureResourcesDiscountDecorator extends DecoratedMethodsDecorator{

	private CardResources resourcesDiscount;
	
	public VentureResourcesDiscountDecorator (DecoratedMethods decoratedMethods, CardResources resourcesDiscount) {
		super(decoratedMethods);
		this.resourcesDiscount = resourcesDiscount;
	}

	/**
	 * this method overrides resourcesForVenture to set a resources discount on a venture cost
	 */
	@Override
	public void resourcesForVenture(NetworkPlayer player ,Venture venture) throws NotEnoughResourcesException{
			//creating a CardResources object that is the result of the card costs - the discount  
			CardResources resources = new CardResources();
			if(resourcesDiscount.stones > 0 && resourcesDiscount.woods > 0){
				player.setMessage("Do you want a discount of " + resourcesDiscount.stones + " stones or " + resourcesDiscount.woods + " woods?\n Respond woods or stones" );
	    		String response = player.sendMessage();
	    		if(("stones").equals(response)){
	    			if(venture.costResources.stones>=resourcesDiscount.stones)
	        			resources.stones= venture.costResources.stones - resourcesDiscount.stones;
	        		else
	        			resources.stones = 0;
	    			resources.woods= venture.costResources.woods;
	    		}
	    		else if (("woods").equals(response)){	
		    		if(venture.costResources.woods>=resourcesDiscount.woods)
		    			resources.woods= venture.costResources.woods - resourcesDiscount.woods;
		    		else
		    			resources.woods = 0;
		    		resources.stones= venture.costResources.stones;
	    		}
	    		else{
	    			player.setMessage("You must choose woods or stones");
	    			decoratedMethods.resourcesForVenture(player,venture);
	    			return;
	    		}
			}
			else{
				if(venture.costResources.woods>=resourcesDiscount.woods)
	    			resources.woods= venture.costResources.woods - resourcesDiscount.woods;
	    		else
	    			resources.woods = 0;
				if(venture.costResources.stones>=resourcesDiscount.stones)
	    			resources.stones= venture.costResources.stones - resourcesDiscount.stones;
	    		else
	    			resources.stones = 0;
				}
    		
    		if(venture.costResources.coins>=resourcesDiscount.coins)
    			resources.coins= venture.costResources.coins - resourcesDiscount.coins;
    		else
    			resources.coins=0;
    		if(venture.costResources.servants>=resourcesDiscount.servants)
    			resources.servants= venture.costResources.servants - resourcesDiscount.servants;
    		else
    			resources.servants=0;
			Venture venture2 = new Venture();
			venture2.costResources = resources;
			decoratedMethods.resourcesForVenture(player,venture2);
	}
	 
}
