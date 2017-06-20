package it.polimi.LM39.controller.decorator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.controller.PersonalBoardHandler;
import it.polimi.LM39.exception.InvalidActionTypeException;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.Building;
import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.Character;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.model.Venture;
import it.polimi.LM39.server.NetworkPlayer;

public class VentureResourcesDiscountDecorator extends DecoratedMethodsDecorator{

	private GameHandler gameHandler;
	private CardResources resourcesDiscount;
	private NetworkPlayer player;
	
	public VentureResourcesDiscountDecorator (DecoratedMethods decoratedMethods,GameHandler gameHandler, CardResources resourcesDiscount, NetworkPlayer player) {
		super(decoratedMethods);
		this.gameHandler = gameHandler;
		this.resourcesDiscount = resourcesDiscount;
		this.player = player;
	}

	
	@Override
	public void resourcesForVenture(NetworkPlayer player ,Venture venture) throws NotEnoughResourcesException{
			//creating a CardResources object that is the result of the card costs - the bonus  
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
	 
	 /*
	@Override
	public void coinsForCharacter(NetworkPlayer player ,Character character) throws NotEnoughResourcesException{
		decoratedMethods.coinsForCharacter(player,character);
	}
	
	@Override
	public void resourcesForBuilding(NetworkPlayer player, Building building) throws NotEnoughResourcesException{
		decoratedMethods.resourcesForBuilding(player,building);
	}
	
	@Override
	public void addCardResources (CardResources resources, NetworkPlayer player) throws NotEnoughResourcesException, NotEnoughPointsException{
		decoratedMethods.addCardResources (resources,player);
	}
	
	@Override
	public boolean addFamilyMemberToTheMarket(FamilyMember familyMember, Integer position, NetworkPlayer player) throws IOException, NotEnoughResourcesException, NotEnoughPointsException {
		return decoratedMethods.addFamilyMemberToTheMarket(familyMember, position, player);
	}
	
	@Override
	public void addCardPoints (CardPoints points, NetworkPlayer player) throws NotEnoughPointsException{
		decoratedMethods.addCardPoints(points, player);
	}
	
	@Override
	public Integer addServants(NetworkPlayer player) throws IOException, NotEnoughResourcesException{
		return decoratedMethods.addServants(player);
	}
	
	@Override
	public boolean activateHarvest(Integer value,NetworkPlayer player,PersonalBoardHandler personalBoardHandler,FamilyMember familyMember) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughResourcesException, NotEnoughPointsException, InvalidActionTypeException {
		return decoratedMethods.activateHarvest(value,player,personalBoardHandler,familyMember);
	}
	
	@Override
	public boolean activateProduction(Integer value,NetworkPlayer player,PersonalBoardHandler personalBoardHandler,FamilyMember familyMember) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughResourcesException, NotEnoughPointsException, IOException, InvalidActionTypeException {
		return decoratedMethods.activateProduction(value,player,personalBoardHandler,familyMember);
	}
	*/
	
}
