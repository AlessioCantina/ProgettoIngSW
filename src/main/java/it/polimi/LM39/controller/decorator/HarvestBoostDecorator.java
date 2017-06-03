package it.polimi.LM39.controller.decorator;

import it.polimi.LM39.server.NetworkPlayer;
import it.polimi.LM39.controller.PlayerBoardHandlerInterface;;

public class HarvestBoostDecorator extends PlayerBoardHandlerDecorator{
	
	Integer harvestBonus = 0;
	NetworkPlayer player;
	
	public HarvestBoostDecorator (PlayerBoardHandlerInterface decoratedPlayerBoard, Integer boost, NetworkPlayer player) {
		super(decoratedPlayerBoard);
		this.harvestBonus = boost;
		this.player = player;
	}

	public void activateHarvest(Integer value,NetworkPlayer player){
		if(this.player == player)
			decoratedPlayerBoard.activateHarvest(value + harvestBonus,player);
		else
			decoratedPlayerBoard.activateHarvest(value,player);
	}

	@Override
	public void activateProduction(Integer value,NetworkPlayer player) {
		decoratedPlayerBoard.activateProduction(value,player);
	}
}
