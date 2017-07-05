package it.polimi.LM39.controller.decorator;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.Character;
import it.polimi.LM39.server.NetworkPlayer;

public class CharacterResourcesDiscountDecorator extends DecoratedMethodsDecorator{
	
	private CardResources resourcesDiscount;
	
	public CharacterResourcesDiscountDecorator (DecoratedMethods decoratedMethods, CardResources resourcesDiscount) {
		super(decoratedMethods);
		this.resourcesDiscount = resourcesDiscount;
	}
	
	
	@Override
	public void coinsForCharacter(NetworkPlayer player ,Character character) throws NotEnoughResourcesException{
			Character character2 = new Character();
			if(character.costCoins>=resourcesDiscount.coins)
				character2.costCoins = character.costCoins - resourcesDiscount.coins;
			else
				character2.costCoins = 0;
			decoratedMethods.coinsForCharacter(player,character2);
	}
	
}
