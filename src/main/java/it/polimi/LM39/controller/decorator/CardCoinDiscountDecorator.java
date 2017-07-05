package it.polimi.LM39.controller.decorator;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.Building;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.Character;
import it.polimi.LM39.model.Venture;
import it.polimi.LM39.server.NetworkPlayer;

public class CardCoinDiscountDecorator extends DecoratedMethodsDecorator{

	private Integer coinDiscount;
	
	public CardCoinDiscountDecorator (DecoratedMethods decoratedMethods, Integer coinDiscount) {
		super(decoratedMethods);
		this.coinDiscount = coinDiscount;
	}
	
	@Override
	public void resourcesForBuilding(NetworkPlayer player ,Building building) throws NotEnoughResourcesException{
			//creating a CardResources object that is the result of the card costs - the bonus  
			CardResources resources = new CardResources();
    		if(building.costResources.coins>=coinDiscount)
    			resources.coins= building.costResources.coins - coinDiscount;
    		else
    			resources.coins = 0;
    		resources.stones= building.costResources.stones;
    		resources.woods= building.costResources.woods;
    		resources.servants= building.costResources.servants;
    		Building building2 = new Building();
			building2.costResources = resources;
			decoratedMethods.resourcesForBuilding(player,building2);
	}
	
	@Override
	public void coinsForCharacter(NetworkPlayer player ,Character character) throws NotEnoughResourcesException{
			Character character2 = new Character();
			if(character.costCoins>=coinDiscount)
				character2.costCoins = character.costCoins - coinDiscount;
			else
				character2.costCoins = 0;
			decoratedMethods.coinsForCharacter(player,character2);
		//if the card cost is lower than the bonus no cost are applied to the player
	}
	
	@Override
	public void resourcesForVenture(NetworkPlayer player ,Venture venture) throws NotEnoughResourcesException{
			//creating a CardResources object that is the result of the card costs - the bonus  
			CardResources resources = new CardResources();
    		if(venture.costResources.coins>=coinDiscount)
    			resources.coins= venture.costResources.coins - coinDiscount;
    		else
    			resources.coins=0;
    		resources.stones= venture.costResources.stones;
    		resources.woods= venture.costResources.woods;
    		resources.servants= venture.costResources.servants;
    		Venture venture2 = new Venture();
			venture2.costResources = resources;
			decoratedMethods.resourcesForVenture(player,venture2);
	}
	
}
