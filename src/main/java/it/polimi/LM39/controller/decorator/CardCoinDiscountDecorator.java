package it.polimi.LM39.controller.decorator;

import java.io.IOException;

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

public class CardCoinDiscountDecorator extends GameHandler{

	private GameHandler decoratedGameHandler;
	private Integer coinDiscount;
	private NetworkPlayer player;
	
	public CardCoinDiscountDecorator (GameHandler decoratedGameHandler, Integer coinDiscount, NetworkPlayer player) {
		this.decoratedGameHandler = decoratedGameHandler;
		this.coinDiscount = coinDiscount;
		this.player = player;
	}
	
	@Override
	public void resourcesForBuilding(NetworkPlayer player ,Building building) throws NotEnoughResourcesException{
		if(this.player == player){
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
			building.costResources = resources;
			decoratedGameHandler.resourcesForBuilding(player,building2);
		}
		//if the bonus is not for the player that is now using this method
		decoratedGameHandler.resourcesForBuilding(player,building);
	}
	
	@Override
	public void coinsForCharacter(NetworkPlayer player ,Character character) throws NotEnoughResourcesException{
		if(this.player == player){
			Character character2 = new Character();
			if(character.costCoins>=coinDiscount)
				character2.costCoins -= coinDiscount;
			else
				character2.costCoins = 0;
		decoratedGameHandler.coinsForCharacter(player,character2);
		//if the card cost is lower than the bonus no cost are applied to the player
		}
		//if the bonus is not for the player that is now using this method
		decoratedGameHandler.coinsForCharacter(player,character);
	}
	
	@Override
	public void resourcesForVenture(NetworkPlayer player ,Venture venture) throws NotEnoughResourcesException{
		if(this.player == player){
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
			decoratedGameHandler.resourcesForVenture(player,venture2);
		}
		//if the bonus is not for the player that is now using this method
		decoratedGameHandler.resourcesForVenture(player,venture);
	}
	
	@Override
	public void addCardResources (CardResources resources, NetworkPlayer player) throws NotEnoughResourcesException, NotEnoughPointsException{
		decoratedGameHandler.addCardResources (resources,player);
	}
	
	@Override
	public boolean addFamilyMemberToTheMarket(FamilyMember familyMember, Integer position, NetworkPlayer player) throws IOException, NotEnoughResourcesException, NotEnoughPointsException {
		return decoratedGameHandler.addFamilyMemberToTheMarket(familyMember, position, player);
	}
	
	@Override
	public void addCardPoints (CardPoints points, NetworkPlayer player) throws NotEnoughPointsException{
		decoratedGameHandler.addCardPoints(points, player);
	}
	
	@Override
	public Integer addServants(NetworkPlayer player) throws IOException, NotEnoughResourcesException{
		return decoratedGameHandler.addServants(player);
	}
	
	
	//probably useless code
	/*
	@Override
	public void removeDecoration(Class toRemove, NetworkPlayer player){
		if (this.getClass() == toRemove && this.player == player)
			coinDiscount = 0;
		else
			decoratedGameHandler.removeDecoration(toRemove, player);
	}
	*/
	
	
}