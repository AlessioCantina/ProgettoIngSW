package it.polimi.LM39.controller.decorator;

import it.polimi.LM39.server.NetworkPlayer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import it.polimi.LM39.controller.PersonalBoardHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;

public class HarvestBoostDecorator extends PersonalBoardHandler{
	
	private PersonalBoardHandler decoratedPlayerBoardHandler;
	private Integer harvestBonus;
	private NetworkPlayer player;
	
	public HarvestBoostDecorator (PersonalBoardHandler decoratedPlayerBoardHandler, Integer boost, NetworkPlayer player) {
		this.decoratedPlayerBoardHandler = decoratedPlayerBoardHandler;
		this.harvestBonus = boost;
		this.player = player;
	}
	
	@Override
	public void activateHarvest(Integer value,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughResourcesException, NotEnoughPointsException{
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
	public void activateProduction(Integer value,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughResourcesException, NotEnoughPointsException, IOException {
		decoratedPlayerBoardHandler.activateProduction(value,player);
	}
}
