package it.polimi.LM39.controller.decorator;

import java.io.IOException;
import java.util.ArrayList;

import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.Building;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.Character;
import it.polimi.LM39.model.Venture;
import it.polimi.LM39.server.NetworkPlayer;

public class CharacterResourcesDiscountDecorator extends GameHandler{

	private GameHandler decoratedGameHandler;
	private CardResources resourcesDiscount;
	private NetworkPlayer player;
	
	public CharacterResourcesDiscountDecorator (GameHandler decoratedGameHandler, CardResources resourcesDiscount, NetworkPlayer player) {
		this.decoratedGameHandler = decoratedGameHandler;
		this.resourcesDiscount = resourcesDiscount;
		this.player = player;
	}
	
	
	@Override
	public boolean getCharacterCard(Character character,NetworkPlayer player,Integer cardNumber) throws IOException{
		if(this.player == player){
	    	ArrayList<Integer> possessedCharacters = player.personalBoard.getPossessions("Character");
			if (possessedCharacters.size()<6){
				if(character.costCoins>=resourcesDiscount.coins){	
					try {
						player.resources.setCoins(-character.costCoins + resourcesDiscount.coins);
					} catch (NotEnoughResourcesException e) {
						e.printStackTrace();
						return false;
					}
				}
		    		possessedCharacters.add(cardNumber);
	    			player.personalBoard.setPossessions(possessedCharacters,"Character");
	    			characterHandler.doInstantEffect(character.instantBonuses, player);
	    			characterHandler.activate(character.permanentEffect, player);
	    			return true;
	    	}
			else
				player.setMessage("You can't have more than 6 characters!");
	    	return false;
	    }
		
		else
			return decoratedGameHandler.getCharacterCard(character,player,cardNumber);
	}
	
	@Override
	public boolean getBuildingCard(Building building,NetworkPlayer player,Integer cardNumber) throws IOException{
		return decoratedGameHandler.getBuildingCard(building,player,cardNumber);
	}
	
	@Override
	public boolean getVentureCard(Venture venture,NetworkPlayer player,Integer cardNumber) throws IOException{
		return decoratedGameHandler.getVentureCard(venture,player,cardNumber);
	}
}
