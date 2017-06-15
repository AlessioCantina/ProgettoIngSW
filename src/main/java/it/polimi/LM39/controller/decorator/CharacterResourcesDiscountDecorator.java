package it.polimi.LM39.controller.decorator;


import java.io.IOException;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.Building;
import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.Character;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.model.Venture;
import it.polimi.LM39.server.NetworkPlayer;

public class CharacterResourcesDiscountDecorator extends DecoratedMethods{
	private DecoratedMethods decoratedMethods;
	private GameHandler gameHandler;
	private CardResources resourcesDiscount;
	private NetworkPlayer player;
	
	public CharacterResourcesDiscountDecorator (DecoratedMethods decoratedMethods,GameHandler gameHandler, CardResources resourcesDiscount, NetworkPlayer player) {
		this.gameHandler = gameHandler;
		this.resourcesDiscount = resourcesDiscount;
		this.player = player;
	}
	
	
	@Override
	public void coinsForCharacter(NetworkPlayer player ,Character character) throws NotEnoughResourcesException{
		if(this.player == player){
			Character character2 = new Character();
			if(character.costCoins>=resourcesDiscount.coins)
				character2.costCoins -= resourcesDiscount.coins;
			else
				character2.costCoins = 0;
			decoratedMethods.coinsForCharacter(player,character2);
			//if the card cost is lower than the bonus no cost are applied to the player
		}
		else
			//if the bonus is not for the player that is now using this method
			decoratedMethods.coinsForCharacter(player,character);
	}
	
	@Override
	public void resourcesForBuilding(NetworkPlayer player, Building building) throws NotEnoughResourcesException{
		decoratedMethods.resourcesForBuilding(player,building);
	}
	
	@Override
	 public void resourcesForVenture(NetworkPlayer player ,Venture venture) throws NotEnoughResourcesException{
		decoratedMethods.resourcesForVenture(player,venture);
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

}
