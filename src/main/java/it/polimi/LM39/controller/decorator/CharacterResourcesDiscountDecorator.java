package it.polimi.LM39.controller.decorator;


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
	public void coinsForCharacter(NetworkPlayer player ,Character character) throws NotEnoughResourcesException{
		if(this.player == player){
		if(character.costCoins>=resourcesDiscount.coins)
			player.resources.setCoins(-character.costCoins + resourcesDiscount.coins);
		//if the card cost is lower than the bonus no cost are applied to the player
		}
		//if the bonus is not for the player that is now using this method
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
}
