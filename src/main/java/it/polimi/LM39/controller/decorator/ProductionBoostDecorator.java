package it.polimi.LM39.controller.decorator;

import it.polimi.LM39.controller.PlayerBoardHandler;
import it.polimi.LM39.server.NetworkPlayer;

public class ProductionBoostDecorator extends PlayerBoardHandler{
	
	private PlayerBoardHandler decoratedPlayerBoardHandler;
	private Integer productionBonus;
	private NetworkPlayer player;
	
	public ProductionBoostDecorator (PlayerBoardHandler decoratedPlayerBoardHandler, Integer boost, NetworkPlayer player) {
		this.decoratedPlayerBoardHandler = decoratedPlayerBoardHandler;
		this.productionBonus = boost;
		this.player = player;
	}

	@Override
	public void activateProduction(Integer value,NetworkPlayer player){
		//check if the player who is activating the production is the player who has activated the bonus
		if(this.player == player)
			decoratedPlayerBoardHandler.activateProduction(value + productionBonus,player);
		else
			//if the player does not have the bonus do a normal activateProduction
			decoratedPlayerBoardHandler.activateProduction(value,player);
	}

	@Override
	//the override must be done because the method activateHarvest could me decorated by another decorator
	//even if ProductionBoostDecorator does not decorate this method
	public void activateHarvest(Integer value,NetworkPlayer player) {
		decoratedPlayerBoardHandler.activateProduction(value,player);
	}
}

