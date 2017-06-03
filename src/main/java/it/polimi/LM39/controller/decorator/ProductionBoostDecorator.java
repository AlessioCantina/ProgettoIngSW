package it.polimi.LM39.controller.decorator;

import it.polimi.LM39.controller.PlayerBoardHandlerInterface;
import it.polimi.LM39.server.NetworkPlayer;

public class ProductionBoostDecorator extends PlayerBoardHandlerDecorator{
	
	Integer productionBonus = 0;
	NetworkPlayer player;
	
	public ProductionBoostDecorator (PlayerBoardHandlerInterface decoratedPlayerBoard, Integer boost, NetworkPlayer player) {
		super(decoratedPlayerBoard);
		this.productionBonus = boost;
		this.player = player;
	}

	public void activateProduction(Integer value,NetworkPlayer player){
		if(this.player == player)
			decoratedPlayerBoard.activateProduction(value + productionBonus,player);
		else
			decoratedPlayerBoard.activateProduction(value,player);
	}

	@Override
	public void activateHarvest(Integer value,NetworkPlayer player) {
		decoratedPlayerBoard.activateProduction(value,player);
	}
}

