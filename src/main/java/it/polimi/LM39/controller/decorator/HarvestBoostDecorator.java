package it.polimi.LM39.controller.decorator;

import it.polimi.LM39.server.NetworkPlayer;

import java.lang.reflect.InvocationTargetException;

import it.polimi.LM39.controller.PlayerBoardHandler;

public class HarvestBoostDecorator extends PlayerBoardHandler{
	
	private PlayerBoardHandler decoratedPlayerBoardHandler;
	private Integer harvestBonus;
	private NetworkPlayer player;
	
	public HarvestBoostDecorator (PlayerBoardHandler decoratedPlayerBoardHandler, Integer boost, NetworkPlayer player) {
		this.decoratedPlayerBoardHandler = decoratedPlayerBoardHandler;
		this.harvestBonus = boost;
		this.player = player;
	}
	
	@Override
	public void activateHarvest(Integer value,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//check if the player who is activating the harvest is the player who has activated the bonus
		if(this.player == player)
			decoratedPlayerBoardHandler.activateHarvest(value + harvestBonus,player);
		else
			//if the player does not have the bonus do a normal activateHarvest
			decoratedPlayerBoardHandler.activateHarvest(value,player);
	}

	@Override
	//the override must be done because the method activateProduction could me decorated by another decorator
	//even if HarvestBoostDecorator does not decorate this method
	public void activateProduction(Integer value,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		decoratedPlayerBoardHandler.activateProduction(value,player);
	}
}
