package it.polimi.LM39.controller.decorator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.controller.PersonalBoardHandler;
import it.polimi.LM39.exception.InvalidActionTypeException;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.Building;
import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.Character;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.model.Venture;
import it.polimi.LM39.server.NetworkPlayer;

public class CardCoinDiscountDecorator extends DecoratedMethods{

	private DecoratedMethods decoratedMethods;
	private GameHandler gameHandler;
	private Integer coinDiscount;
	private NetworkPlayer player;
	
	public CardCoinDiscountDecorator (DecoratedMethods decoratedMethods,GameHandler gameHandler, Integer coinDiscount, NetworkPlayer player) {
		this.decoratedMethods = decoratedMethods;
		this.gameHandler = gameHandler;
		this.coinDiscount = coinDiscount;
		this.player = player;
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
				character2.costCoins -= coinDiscount;
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
	
	@Override
	public boolean activateHarvest(Integer value,NetworkPlayer player,PersonalBoardHandler personalBoardHandler,FamilyMember familyMember) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughResourcesException, NotEnoughPointsException, InvalidActionTypeException {
		return decoratedMethods.activateHarvest(value,player,personalBoardHandler,familyMember);
	}
	
	@Override
	public boolean activateProduction(Integer value,NetworkPlayer player,PersonalBoardHandler personalBoardHandler,FamilyMember familyMember) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughResourcesException, NotEnoughPointsException, IOException, InvalidActionTypeException {
		return decoratedMethods.activateProduction(value,player,personalBoardHandler,familyMember);
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
