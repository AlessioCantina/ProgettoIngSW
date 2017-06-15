package it.polimi.LM39.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import it.polimi.LM39.controller.decorator.*;
import it.polimi.LM39.exception.*;
import it.polimi.LM39.model.Effect;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.model.Leader;
import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.server.NetworkPlayer;
import it.polimi.LM39.model.PlayerResources;
import it.polimi.LM39.model.characterpermanenteffect.*;
import it.polimi.LM39.model.excommunicationpermanenteffect.*;
import it.polimi.LM39.model.instanteffect.*;
import it.polimi.LM39.model.leaderpermanenteffect.*;
import it.polimi.LM39.model.leaderobject.*;
import it.polimi.LM39.model.ActionBonus;
import it.polimi.LM39.model.Building;
import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.Venture;
import it.polimi.LM39.model.Character;

public class CardHandler {
	private GameHandler gameHandler;
	private DecoratedMethods decoratedMethods;
	//TODO fix long throws with logger
	public CardHandler(GameHandler gameHandler,DecoratedMethods decoratedMethods){
		this.gameHandler = gameHandler;
		this.decoratedMethods=decoratedMethods;
	}
	
	private NetworkPlayer playerDoubleResourcesFromDevelopment;
	
	/*
	 * InstantEffect
	 */
	
 public void doInstantEffect(InstantEffect instantEffect,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{
	 Class[] cArg = new Class[2];
	 cArg[0] = instantEffect.getClass();
	 cArg[1] = NetworkPlayer.class;
	 Method lMethod = (this.getClass().getMethod("doInstantEffect",cArg));
	 lMethod.invoke(this,instantEffect,player);
 }

 	public void doInstantEffect(NoInstantEffect instantEffect,NetworkPlayer player) throws NotEnoughResourcesException{
 		//do nothing
 	}
 
	public void doInstantEffect(CoinForCard instantEffect,NetworkPlayer player) throws NotEnoughResourcesException{
		//calculate the coin to receive by multiplying the possessed cards of a specific type by the coin quantity given by card
		Integer coinQty=(player.personalBoard.getPossessions(instantEffect.cardType).size())*instantEffect.coinQty;
			player.resources.setCoins(coinQty);	
	}
	
	public void doInstantEffect(DoublePointsTransformation instantEffect,NetworkPlayer player) throws IOException, NotEnoughResourcesException, NotEnoughPointsException, InvalidInputException{
		//ask to the player what exchange he wants to do
		player.setMessage("What exchange do you want to do? 1 or 2");
		//get the player response
		Integer choice = Integer.parseInt(player.sendMessage());
		if (choice==1){
			//subtract the resources from the player
			gameHandler.subCardResources(instantEffect.requestedForTransformation, player);
			//add points to the player
			gameHandler.decoratedMethods.addCardPoints(instantEffect.points, player);}
		else if(choice==2){
			//subtract the resources from the player
			gameHandler.subCardResources(instantEffect.requestedForTransformation2, player);
			//add points to the player
			gameHandler.decoratedMethods.addCardPoints(instantEffect.points2, player);}
		else
			throw new InvalidInputException("The exchange must be chosen between 1 and 2");
			
		
	}
	
	public void doInstantEffect(DoubleResourcesTransformation instantEffect,NetworkPlayer player) throws IOException, NotEnoughResourcesException, InvalidInputException, NotEnoughPointsException{
		//ask to the player what exchange he wants to do
				player.setMessage("What exchange do you want to do? 1 or 2");
				//get the player response
				Integer choice = Integer.parseInt(player.sendMessage());
				if (choice==1){
					//subtract the resources from the player
					gameHandler.subCardResources(instantEffect.requestedForTransformation, player);
					//add resources to the player
					gameHandler.decoratedMethods.addCardResources(instantEffect.resources, player);}
				else if(choice==2){
					//subtract the resources from the player
					gameHandler.subCardResources(instantEffect.requestedForTransformation2, player);
					//add resources to the player
					gameHandler.decoratedMethods.addCardResources(instantEffect.resources2, player);}
				else
					throw new InvalidInputException("The exchange must be chosen between 1 and 2");
	}
	
	public void doInstantEffect(GetCard instantEffect,NetworkPlayer player) throws IOException, CardNotFoundException, NotEnoughResourcesException, NotEnoughPointsException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		// ask to the player if he wants to use this effect
		getInfo(instantEffect,player);
		player.setMessage("Do you want to use this effect? yes or no");
		String response = player.sendMessage();
		response = GameHandler.checkResponse(response, player);
		if(("yes").equals(response)){
			// ask to the player what card he wants
			player.setMessage("What card do you want?");
			//get the player response
			String cardName = player.sendMessage();
			Integer qtyServants = gameHandler.decoratedMethods.addServants(player);
			
			String[][] CardNamesOnTheTowers = player.personalMainBoard.getCardNamesOnTheTowers();
			//looking for this card on the Towers
			boolean flag = false;
			for(int i=0;i<4;i++)
				for(int j=0;j<4;j++)
					if((CardNamesOnTheTowers[i][j]).compareToIgnoreCase(cardName) == 0){
						if(j==0 && ("Territory").equals(instantEffect.cardType) && (instantEffect.cardValue+qtyServants) >= gameHandler.mainBoard.getTowersValue()[i][j])
							flag = gameHandler.getCard(gameHandler.cardNameToInteger(cardName), player, j);
						else if(j==1 && ("Character").equals(instantEffect.cardType) && (instantEffect.cardValue+qtyServants) >= gameHandler.mainBoard.getTowersValue()[i][j])
							flag = gameHandler.getCard(gameHandler.cardNameToInteger(cardName), player, j);
						else if(j==2 && ("Building").equals(instantEffect.cardType) && (instantEffect.cardValue+qtyServants) >= gameHandler.mainBoard.getTowersValue()[i][j])
							flag = gameHandler.getCard(gameHandler.cardNameToInteger(cardName), player, j);
						else if(j==3 && ("Venture").equals(instantEffect.cardType) && (instantEffect.cardValue+qtyServants) >= gameHandler.mainBoard.getTowersValue()[i][j])
							flag = gameHandler.getCard(gameHandler.cardNameToInteger(cardName), player, j);
						else if(("All").equals(instantEffect.cardType) && (instantEffect.cardValue+qtyServants) >= gameHandler.mainBoard.getTowersValue()[i][j])
							flag = gameHandler.getCard(gameHandler.cardNameToInteger(cardName), player, j);
						if (flag == false){
							player.resources.setServants(qtyServants);
							player.setMessage("You can't get this card");
						}
						else{
							gameHandler.mainBoard.getCardNamesOnTheTowers()[i][j] = "";
						}
						
						//avoid useless cicles
						return;
					}
			}
			
	}
	
	public void doInstantEffect(GetCardAndPoints instantEffect,NetworkPlayer player) throws IOException, NotEnoughPointsException, CardNotFoundException, NotEnoughResourcesException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//making a GetCard effect and calling his method
		GetCard effect = new GetCard();
		effect.cardType=instantEffect.cardType;
		effect.cardValue=instantEffect.cardValue;
		doInstantEffect(effect,player);
		//making a Resources effect and calling his method
		Points pointsEffect = new Points();
		pointsEffect.points=instantEffect.points;
		doInstantEffect(pointsEffect,player);
	}
	
	public void doInstantEffect(GetCardAndResources instantEffect,NetworkPlayer player) throws IOException, NotEnoughResourcesException, CardNotFoundException, NotEnoughPointsException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//making a GetCard effect and calling his method
		GetCard effect = new GetCard();
		effect.cardType=instantEffect.cardType;
		effect.cardValue=instantEffect.cardValue;
		doInstantEffect(effect,player);
		//making a Resources effect and calling his method
		Resources resourcesEffect = new Resources();
		resourcesEffect.resources=instantEffect.resources;
		doInstantEffect(resourcesEffect,player);
	}
	
	public void doInstantEffect(GetDiscountedCard instantEffect,NetworkPlayer player) throws IOException, CardNotFoundException, NotEnoughResourcesException, NotEnoughPointsException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		// ask to the player if he wants to use this effect
		getInfo(instantEffect,player);
		player.setMessage("Do you want to use this effect? yes or no");
		String response = player.sendMessage();
		response = GameHandler.checkResponse(response, player);
		if(("yes").equals(response)){
			// ask to the player what card he wants
			player.setMessage("What card do you want?");
			String cardName = player.sendMessage();
			Integer qtyServants = gameHandler.decoratedMethods.addServants(player);
			//converting the card name to cardNumber
			Integer cardNumber = gameHandler.cardNameToInteger(cardName);
			Integer[][] CardsOnTheTowers = player.personalMainBoard.getCardsOnTheTowers();
			//looking for this card on the Towers
			for(int i=0;i<4;i++)
				for(int j=0;j<4;j++)
					if(CardsOnTheTowers[i][j]==cardNumber){
						//if the card is a Territory the discount get lost
						if(j==0 && (("Territory").equals(instantEffect.cardType) || ("All").equals(instantEffect.cardType)) && (instantEffect.cardValue+qtyServants) >= gameHandler.mainBoard.getTowersValue()[i][j])
							gameHandler.getCard(cardNumber, player, j);
						//if the card is Character the discount is only on coins
						else if(j==1 && (("Character").equals(instantEffect.cardType) || ("All").equals(instantEffect.cardType)) && (instantEffect.cardValue+qtyServants) >= gameHandler.mainBoard.getTowersValue()[i][j]){
								Character character = gameHandler.mainBoard.characterMap.get(cardNumber);
								Integer costCoins = character.costCoins;
								if(costCoins >= instantEffect.cardDiscount.coins)
									player.resources.setCoins(instantEffect.cardDiscount.coins);
								else
									player.resources.setCoins(costCoins);
								gameHandler.getCard(cardNumber, player, j);
							}
						else {
							CardResources costResources = new CardResources();
							//if the card is a Building
							if(j==2 && (("Building").equals(instantEffect.cardType) || ("All").equals(instantEffect.cardType)) && (instantEffect.cardValue+qtyServants) >= gameHandler.mainBoard.getTowersValue()[i][j]){
								Building building = gameHandler.mainBoard.buildingMap.get(cardNumber);
								costResources = building.costResources;
							}
							//if the card is a Venture
							else if(j==3 && (("Venture").equals(instantEffect.cardType) || ("All").equals(instantEffect.cardType)) && (instantEffect.cardValue+qtyServants) >= gameHandler.mainBoard.getTowersValue()[i][j]){
								Venture venture = gameHandler.mainBoard.ventureMap.get(cardNumber);
								costResources = venture.costResources;
						    }
							else {
								player.resources.setServants(qtyServants);
								player.setMessage("You can't get this card");
							}
							CardResources bonusResources = new CardResources();
							//check the card costs and confront it with the bonus to know how many resources to give to the player as discount
							if(costResources.coins >= instantEffect.cardDiscount.coins)
								bonusResources.coins=costResources.coins;
							else
								bonusResources.coins=instantEffect.cardDiscount.coins;
							
							if(costResources.stones >= instantEffect.cardDiscount.stones)
								bonusResources.stones=costResources.stones;
							else
								bonusResources.stones=instantEffect.cardDiscount.stones;
							
							if(costResources.woods >= instantEffect.cardDiscount.woods)
								bonusResources.woods=costResources.woods;
							else
								bonusResources.woods=instantEffect.cardDiscount.woods;
							
							if(costResources.servants >= instantEffect.cardDiscount.servants)
								bonusResources.servants=costResources.servants;
							else
								bonusResources.servants=instantEffect.cardDiscount.servants;
							bonusResources.council = 0;
							//give the discount to the player
							gameHandler.decoratedMethods.addCardResources(bonusResources, player);
							//if the player failed to get the card for lack of resources or not enough space on the PersonalBoard, remove the bonus given
							if(!gameHandler.getCard(cardNumber, player, j))
								gameHandler.subCardResources(bonusResources, player);
							else {
								gameHandler.mainBoard.getCardNamesOnTheTowers()[i][j] = "";
							}
						}
						//avoid useless cicles
						return;
					}
		}
	}
	
	public void doInstantEffect(HarvestProductionAction instantEffect,NetworkPlayer player) throws IOException, NotEnoughResourcesException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughPointsException{
		//ask to the player if he wants to add servants to the action
		Integer qtyServants = gameHandler.decoratedMethods.addServants(player);
		//check if the effect is for harvest o production and call the correct method
		if(("Harvest").equals(instantEffect.actionType))
			gameHandler.personalBoardHandler.activateHarvest(instantEffect.actionValue + qtyServants, player);
		else if(("Production").equals(instantEffect.actionType))
			gameHandler.personalBoardHandler.activateProduction(instantEffect.actionValue + qtyServants, player);
	}
	
	public void doInstantEffect(HarvestProductionAndPoints instantEffect,NetworkPlayer player) throws NotEnoughPointsException, IOException, NotEnoughResourcesException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//making an HarvestProductionAction effect and calling his method
		HarvestProductionAction effect = new HarvestProductionAction();
		effect.actionType = instantEffect.actionType;
		effect.actionValue = instantEffect.actionValue;
		doInstantEffect(effect,player);
		//making a Points effect and calling his method
		Points pointsEffect = new Points();
		pointsEffect.points = instantEffect.points;
		doInstantEffect(pointsEffect,player);
	}
	
	public void doInstantEffect(Points instantEffect,NetworkPlayer player) throws NotEnoughPointsException{
		gameHandler.decoratedMethods.addCardPoints(instantEffect.points, player);
	}
	
	public void doInstantEffect(PointsTransformation instantEffect,NetworkPlayer player) throws NotEnoughResourcesException, NotEnoughPointsException{
		//check if the player has enough resources
		gameHandler.subCardResources(instantEffect.requestedForTransformation, player);
		//add points to the player
		gameHandler.decoratedMethods.addCardPoints(instantEffect.points, player);
	}

	public void doInstantEffect(Resources instantEffect,NetworkPlayer player) throws NotEnoughResourcesException, NotEnoughPointsException{
		gameHandler.decoratedMethods.addCardResources(instantEffect.resources, player);
		//double instant bonus if the player has the leader effect DoubleResourcesFromDevelopment 
		if(this.playerDoubleResourcesFromDevelopment==player){
			CardResources resourcesBonus = instantEffect.resources;
			resourcesBonus.council=0;
			gameHandler.decoratedMethods.addCardResources(resourcesBonus, player);
		}
		
	}
	
	public void doInstantEffect(ResourcesAndPoints instantEffect,NetworkPlayer player) throws NotEnoughPointsException, NotEnoughResourcesException{
		//making a Resources effect and calling his method
		Resources resourcesEffect = new Resources();
		resourcesEffect.resources = instantEffect.resources;
		doInstantEffect(resourcesEffect,player);
		//making a Points effect and calling his method
		Points pointsEffect = new Points();
		pointsEffect.points = instantEffect.points;
		doInstantEffect(pointsEffect,player);
	}
	
	public void doInstantEffect(ResourcesAndPointsTransformation instantEffect,NetworkPlayer player) throws NotEnoughPointsException, NotEnoughResourcesException{
		//check if the player has enough points for the transformation
		gameHandler.subCardPoints(instantEffect.requestedForTransformation, player);
		//making a ResourcesAndPoints effect and calling his method
		ResourcesAndPoints effect = new ResourcesAndPoints();
		effect.points = instantEffect.points;
		effect.resources = instantEffect.resources;
		doInstantEffect(effect,player);
	}
	
	public void doInstantEffect(ResourcesTransformation instantEffect,NetworkPlayer player) throws NotEnoughResourcesException, NotEnoughPointsException{
		//checking if the player has enough resources
		gameHandler.subCardResources(instantEffect.requestedForTransformation, player);
		//making a Resources effect and calling his method
		Resources resourcesEffect = new Resources();
		resourcesEffect.resources = instantEffect.resources;
		doInstantEffect(resourcesEffect,player);
	}
	
	public void doInstantEffect(SetFamilyMember instantEffect,NetworkPlayer player) throws IOException, InvalidActionTypeException{
		//the color will be chosen by the user
		player.setMessage("What FamilyMemebr color do you want to set the value?");
		//get the FamilyMember color from the player
		String color = player.sendMessage();
		//make a copy of diceValues then set the desired FamilyMember to the value of the bonus
		Integer[] diceValues = player.personalMainBoard.getDiceValues();
		switch(color){
	    	case "black": diceValues[0] = instantEffect.familyMemberValue;
	    		break;
	    	case "white": diceValues[1] = instantEffect.familyMemberValue;
	    		break;
	    	case "orange": diceValues[2] = instantEffect.familyMemberValue;
	    		break;
	    	default: throw new InvalidActionTypeException("Invalid FamilyMember color!");
	}
		player.personalMainBoard.setDiceValues(diceValues);
	}
	
	public void doInstantEffect(VictoryForCard instantEffect,NetworkPlayer player){
		//calculate the victory points to receive by multiplying the possessed cards of a specific type by the victory quantity given by card
		Integer victoryQty=(player.personalBoard.getPossessions(instantEffect.cardType).size())*instantEffect.victoryQty;
			player.points.setVictory(victoryQty);
	}
	
	public void doInstantEffect(VictoryForMilitary instantEffect,NetworkPlayer player){
		//calculate the victory points to receive by multiplying the possessed cards of a specific type by the victory quantity given by card
		Integer victoryQty=(player.points.getMilitary())*instantEffect.victoryQty;
		player.points.setVictory(victoryQty);
		
	}
	
	
	/*
	 * LeaderRequestedObjects
	 */
	
	public boolean checkLeaderRequestedObject(LeaderRequestedObjects requestedObject,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{	
		Class[] cArg = new Class[2];
		cArg[0] = requestedObject.getClass();
		cArg[1] = NetworkPlayer.class;
		Method lMethod = (this.getClass().getMethod("checkLeaderRequestedObject",cArg));
		return (boolean)lMethod.invoke(this,requestedObject,player);
	}
	
	public boolean checkLeaderRequestedObject(RequestedCard requestedObject,NetworkPlayer player){
		//check if the player has enough card of a type
		if (player.personalBoard.getPossessions(requestedObject.cardType).size() >= requestedObject.cardQty)
			return true;
		return false;
	}
	
	public boolean checkLeaderRequestedObject(RequestedCardPointsResources requestedObject,NetworkPlayer player){
		//making a RequestedCard effect to call his specific method
		RequestedCard requestedCard = new RequestedCard();
		requestedCard.cardQty = requestedObject.cardQty;
		requestedCard.cardType = requestedObject.cardType;
		boolean flag1 = checkLeaderRequestedObject(requestedCard, player);
		//making a RequestedPoints effect to call his specific method
		RequestedPoints requestedPoints = new RequestedPoints();
		requestedPoints.points = requestedObject.points;
		boolean flag2 = checkLeaderRequestedObject(requestedPoints, player);
		//making a RequestedResources effect to call his specific method
		RequestedResources requestedResources = new RequestedResources();
		requestedResources.resources = requestedObject.resources;
		boolean flag3 = checkLeaderRequestedObject(requestedResources, player);
		// if all the methods returned true the card can be activated
		return (flag1 && flag2 && flag3);
	}
	
	public boolean checkLeaderRequestedObject(RequestedPoints requestedObject,NetworkPlayer player){
		if(player.points.getMilitary() >= requestedObject.points.military &&
				player.points.getFaith() >= requestedObject.points.faith &&
					player.points.getVictory() >= requestedObject.points.victory)
			return true;
		return false;
	}
	
	public boolean checkLeaderRequestedObject(RequestedResources requestedObject,NetworkPlayer player){
		if(player.resources.getWoods() >= requestedObject.resources.woods &&
				player.resources.getStones() >= requestedObject.resources.stones &&
				player.resources.getServants() >= requestedObject.resources.servants &&
				player.resources.getCoins() >= requestedObject.resources.coins)
			return true;
		return false;
	}
	
	public boolean checkLeaderRequestedObject(RequestedSameCard requestedObject,NetworkPlayer player){
		//flag is false by default
		boolean flag;
		RequestedCard requestedCard = new RequestedCard();
		requestedCard.cardQty = requestedObject.cardQty;
		//check if the player has enough card of any type
		String[] cardTypeArray = {"Territory","Character","Building","Venture"};
		for(int i=0;i<4;i++){
			requestedCard.cardType = cardTypeArray[i];
			flag = checkLeaderRequestedObject(requestedCard,player);
			if(flag == true)
				return flag;
		}
		return false;
	}
		
	public boolean checkLeaderRequestedObject(RequestedTwoCards requestedObject,NetworkPlayer player){
		//flag1 and flag2 are false by default
		boolean flag1,flag2;
		RequestedCard requestedCard = new RequestedCard();
		//check the first condition on the requeted cards
		requestedCard.cardQty=requestedObject.cardQty;
		requestedCard.cardType=requestedObject.cardType;
		flag1 = checkLeaderRequestedObject(requestedCard,player);
		//check the second condition on the requeted cards
		requestedCard.cardQty=requestedObject.cardQty2;
		requestedCard.cardType=requestedObject.cardType2;
		flag2 = checkLeaderRequestedObject(requestedCard,player);
		//the two condition returned true the leader can be used
		return (flag1 && flag2);
	}
		
	
	/*
	 * CharacterPermanentEffect
	 */
	
	public DecoratedMethods activateCharacter(CharacterPermanentEffect permanentEffect,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{									
		Class[] cArg = new Class[2];
	    cArg[0] = permanentEffect.getClass();
	    cArg[1] = NetworkPlayer.class;
		Method lMethod = (this.getClass().getMethod("activateCharacter",cArg));
		return (DecoratedMethods)lMethod.invoke(this,permanentEffect,player);
		}
	
	public void activateCharacter(NoCharacterPermanentEffect permanentEffect, NetworkPlayer player){
		//do nothing
	}
	
	public DecoratedMethods activateCharacter(CardActionDiscount permanentEffect, NetworkPlayer player){
		//reducing the towersValue by the discount the effect gives
		Integer[][] towersValue = player.personalMainBoard.getTowersValue();
		int i = 0;
		switch(permanentEffect.cardType){
	    	case "Territory": i=0;
	    		break;
	    	case "Character": i=1;
	    		break;
	    	case "Building": i=2;
	    		break;
	    	case "Venture": i=3;
				break;
		}
		for(int j=0;j<4;j++)
			towersValue[j][i]-=permanentEffect.discount;
		player.personalMainBoard.setTowersValue(towersValue);
		
		return decoratedMethods;
	}
	
	public DecoratedMethods activateCharacter(CardActionResourcesDiscount permanentEffect, NetworkPlayer player){
		//need to check when a player get a card to set the discount like done for the GetDiscountedCard effect
		if(("Character").equals(permanentEffect.cardType) && player.decoratorHandler.characterResourcesDiscountDecorator == false){
			player.decoratorHandler.characterResourcesDiscountDecorator=true;
			decoratedMethods = new CharacterResourcesDiscountDecorator(decoratedMethods,gameHandler,permanentEffect.resourcesDiscount,player);
		}
		else if(("Venture").equals(permanentEffect.cardType) && player.decoratorHandler.ventureResourcesDiscountDecorator == false){
			player.decoratorHandler.ventureResourcesDiscountDecorator = true;
			decoratedMethods = new VentureResourcesDiscountDecorator(decoratedMethods,gameHandler,permanentEffect.resourcesDiscount,player);
		}
		else if(("Building").equals(permanentEffect.cardType) && player.decoratorHandler.buildingResourcesDiscountDecorator == false){
			System.out.println("decorating a building");
			player.decoratorHandler.buildingResourcesDiscountDecorator = true;
			decoratedMethods = new BuildingResourcesDiscountDecorator(decoratedMethods,gameHandler,permanentEffect.resourcesDiscount,player);
			System.out.println("gameHandler dentro l'effect " + gameHandler);
		}
		
		//make a CardActionDiscount effect and call his method
		CardActionDiscount effect = new CardActionDiscount();
		effect.cardType = permanentEffect.cardType;
		effect.discount = permanentEffect.discount;
		activateCharacter(effect,player);
		
		return decoratedMethods;
	}
	
	
	public DecoratedMethods activateCharacter(HarvestProductionBoost permanentEffect, NetworkPlayer player){
		//need to check when a player with this effect try to do a Production or Harvest and give him the bonus
		if(("Harvest").equals(permanentEffect.actionType) && player.decoratorHandler.harvestBoostDecorator == false){
			player.decoratorHandler.harvestBoostDecorator = true;
			gameHandler.personalBoardHandler = new HarvestBoostDecorator(gameHandler.personalBoardHandler,permanentEffect.actionValue,player);
		}
		else if (player.decoratorHandler.productionBoostDecorator == false){
			player.decoratorHandler.productionBoostDecorator = true;
			gameHandler.personalBoardHandler = new ProductionBoostDecorator(gameHandler.personalBoardHandler,permanentEffect.actionValue,player);} 
	
		return decoratedMethods;
	}
	
	public DecoratedMethods activateCharacter(NoBoardBonuses permanentEffect, NetworkPlayer player){
		//create an empty towersBonuses and set it to the playerPersonalBoard
		ActionBonus[][] towersBonuses = new ActionBonus[4][4];
		player.personalMainBoard.setTowersBonuses(towersBonuses);
		
		return decoratedMethods;
	}
		
	
	/*
	 * ExcommunicationPermanentEffect
	 */
		
	public DecoratedMethods activateExcommunication(ExcommunicationPermanentEffect permanentEffect,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{
		Class[] cArg = new Class[2];
	    cArg[0] = permanentEffect.getClass();
	    cArg[1] = NetworkPlayer.class;
		Method lMethod = (this.getClass().getMethod("activateExcommunication",cArg));
		return (DecoratedMethods)lMethod.invoke(this,permanentEffect,player);
}
	
	public DecoratedMethods activateExcommunication(CardActionMalus permanentEffect,NetworkPlayer player){
		//make a CardActionDiscount effect that will act in the opposite way giving the discount parameter as a malus, then call his method
		CardActionDiscount effect = new CardActionDiscount();
		effect.cardType = permanentEffect.cardType;
		effect.discount = - permanentEffect.malus;
		activateCharacter(effect,player);
		
		return decoratedMethods;
	}
	
	public DecoratedMethods activateExcommunication(DiceMalus permanentEffect,NetworkPlayer player){
		Integer[] diceValues = player.personalMainBoard.getDiceValues();
		for(int i=0;i<3;i++)
			diceValues[i] -= permanentEffect.malus;
		player.personalMainBoard.setDiceValues(diceValues);
		
		return decoratedMethods;
	}
	
	public DecoratedMethods activateExcommunication(HarvestProductionMalus permanentEffect,NetworkPlayer player){
		if(("Harvest").equals(permanentEffect.actionType) && player.decoratorHandler.harvestMalus == false){
			//if not set to false it would block the decoration
			player.decoratorHandler.productionBoostDecorator = false;
			player.decoratorHandler.harvestMalus = true;
			gameHandler.personalBoardHandler = new HarvestBoostDecorator(gameHandler.personalBoardHandler,-permanentEffect.malus,player);
		}
		else if (player.decoratorHandler.productionMalus == false){
			//if not set to false it would block the decoration
			player.decoratorHandler.productionBoostDecorator = false;
			player.decoratorHandler.productionMalus = true;
			gameHandler.personalBoardHandler = new ProductionBoostDecorator(gameHandler.personalBoardHandler,-permanentEffect.malus,player);
		}
		
		return decoratedMethods;
	}
	
	public DecoratedMethods activateExcommunication(MalusForResources permanentEffect,NetworkPlayer player){
		Integer victoryMalus = ((player.resources.getCoins() + player.resources.getWoods() + player.resources.getStones() + player.resources.getServants()) / permanentEffect.resourceQty)* permanentEffect.victoryQty;
		if (player.points.getVictory() >= victoryMalus)
			player.points.setVictory(-victoryMalus);
		else
			player.points.setVictory(0);
		
		return decoratedMethods;
	}
	
	public DecoratedMethods activateExcommunication(MalusForResourcesCost permanentEffect,NetworkPlayer player){
		ArrayList<Integer> buildings = player.personalBoard.getPossessions("Building");
		Integer victoryMalus = 0;
		for (Integer i : buildings)
			victoryMalus=gameHandler.mainBoard.buildingMap.get(i).costResources.woods + gameHandler.mainBoard.buildingMap.get(i).costResources.stones;
		victoryMalus = (victoryMalus / permanentEffect.resourceQty)* permanentEffect.victoryQty;
		if (player.points.getVictory() >= victoryMalus)
			player.points.setVictory(-victoryMalus);
		else
			player.points.setVictory(0);
		
		return decoratedMethods;
	}
	
	public DecoratedMethods activateExcommunication(MalusVictoryForMilitary permanentEffect,NetworkPlayer player){
		Integer victoryMalus = (player.points.getMilitary() / permanentEffect.militaryQty)* permanentEffect.victoryQty;
		if (player.points.getVictory() >= victoryMalus)
			player.points.setVictory(-victoryMalus);
		else
			player.points.setVictory(0);
		
		return decoratedMethods;
	}
	
	public DecoratedMethods activateExcommunication(MilitaryPointsMalus permanentEffect,NetworkPlayer player){
		if (player.decoratorHandler.militaryPointsMalusDecorator == false){
			player.decoratorHandler.militaryPointsMalusDecorator = true;
			decoratedMethods = new MilitaryPointsMalusDecorator(decoratedMethods,gameHandler,permanentEffect.militaryQty ,player);
		}
		
		return decoratedMethods;
	}
	
	public DecoratedMethods activateExcommunication(NoMarket permanentEffect,NetworkPlayer player){
		if(player.decoratorHandler.noMarketDecorator == false){
			player.decoratorHandler.noMarketDecorator=true;
			decoratedMethods = new NoMarketDecorator(decoratedMethods,gameHandler,player);
		}
		
		return decoratedMethods;
	}
	
	public DecoratedMethods activateExcommunication(ResourcesMalus permanentEffect,NetworkPlayer player){
		if(player.decoratorHandler.resourcesMalusDecorator == false){
			player.decoratorHandler.resourcesMalusDecorator = true;
			decoratedMethods = new ResourcesMalusDecorator(decoratedMethods,gameHandler,permanentEffect.resources,player);
		}
		
		return decoratedMethods;
	}
	
	public DecoratedMethods activateExcommunication(ServantsMalus permanentEffect,NetworkPlayer player){
		if(player.decoratorHandler.servantsMalusDecorator == false){
			player.decoratorHandler.servantsMalusDecorator = true;
			decoratedMethods = new ServantsMalusDecorator(decoratedMethods,gameHandler,permanentEffect.servantsQty,player);
		}
		
		return decoratedMethods;
	}
	
	public DecoratedMethods activateExcommunication(VictoryMalus permanentEffect,NetworkPlayer player){
		Integer victoryMalus = (player.points.getVictory() / permanentEffect.victoryQty)* permanentEffect.victoryMalus;
		player.points.setVictory(-victoryMalus);
		
		return decoratedMethods;
	}
	
	
	
	/*
	 * LeaderPermanentEffect
	 */
	
	public DecoratedMethods activateLeader(Effect permanentEffect,NetworkPlayer player,String cardName) throws SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException	{
		Class[] cArg = new Class[3];
	    cArg[0] = permanentEffect.getClass();
	    cArg[1] = NetworkPlayer.class;
	    cArg[2] = cardName.getClass();
		Method lMethod;
		try {
			lMethod = (this.getClass().getMethod("activateLeader",cArg));
		} catch (NoSuchMethodException e) {
			Class[] cArg1 = new Class[2];
			cArg1[0] = cArg[0];
			cArg1[1] = cArg[1];
			lMethod = (this.getClass().getMethod("doInstantEffect",cArg1));
		}
		return (DecoratedMethods)lMethod.invoke(this,permanentEffect,player);
}
	
	public DecoratedMethods activateLeader(AlreadyOccupiedTowerDiscount permanentEffect,NetworkPlayer player,String cardName){
		player.setPlayerPlayedLeaderCards(cardName);
		player.personalMainBoard.occupiedTowerCost = 0;
		
		return decoratedMethods;
	}
	
	public DecoratedMethods activateLeader(CardCoinDiscount permanentEffect,NetworkPlayer player,String cardName){
		if(player.decoratorHandler.cardCoinDiscountDecorator == false){
			player.setPlayerPlayedLeaderCards(cardName);
			player.decoratorHandler.cardCoinDiscountDecorator = true;
			decoratedMethods = new CardCoinDiscountDecorator(decoratedMethods,gameHandler,permanentEffect.coinQty,player);
		}
		
		return decoratedMethods;
	}
	
	public DecoratedMethods activateLeader(CopyLeaderAbility permanentEffect,NetworkPlayer player,String cardName) throws CardNotFoundException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException{
		if(player.copiedLeaderCard == null){
			player.setPlayerPlayedLeaderCards(cardName);
			boolean flag = true;
			ArrayList<String> list = new ArrayList<String>();
			for (String playedCard : player.personalMainBoard.getPlayedLeaderCard()){
				for (String playerPlayedCArd : player.getPlayerPlayedLeaderCards()){
					if(playedCard.equals(playerPlayedCArd))
						flag = false;
					}
			if(flag==true)
				list.add(playedCard);
			flag=true;
			}
			player.setMessage("What Leader do you want to copy?");
			ArrayList<String> nameList = new ArrayList<String>();
			for(String i : list){
				player.setMessage(gameHandler.mainBoard.leaderMap.get(i).cardName);
				nameList.add(gameHandler.mainBoard.leaderMap.get(i).cardName);
				}
			String response = player.sendMessage();
			flag = false;
			Integer cardNumber = 0;
			for (String name : nameList)
				if(name.equals(response)){
					cardNumber = gameHandler.cardNameToInteger(response);
					flag = true;}
			if(flag==false){
				player.setMessage("You must choose a Leader Card already played by one of your opponents");
				return activateLeader(permanentEffect,player,cardName);
			}
			//add the card to the player copiedLeaderCard attribute to prevent that the player copy more than one effect violating the rule of this effect
			player.copiedLeaderCard = cardNumber;
			Leader leader = gameHandler.mainBoard.leaderMap.get(cardNumber);
			activateLeader(leader.effect,player,cardName);
		}
		else{
			Leader leader = gameHandler.mainBoard.leaderMap.get(player.copiedLeaderCard);
			activateLeader(leader.effect,player,cardName);
		}
		
		return decoratedMethods;
	}
	
	public DecoratedMethods activateLeader(DoubleResourcesFromDevelopment permanentEffect,NetworkPlayer player,String cardName) {
		player.setPlayerPlayedLeaderCards(cardName);
		playerDoubleResourcesFromDevelopment = player;
		
		return decoratedMethods;
	}
	
	public DecoratedMethods activateLeader(NoMilitaryRequirementsForTerritory permanentEffect,NetworkPlayer player,String cardName) {
		player.setPlayerPlayedLeaderCards(cardName);
		for(int i=0;i<4;i++)
			player.personalMainBoard.militaryForTerritory[i]=0;
		
		return decoratedMethods;
	}
	
	public DecoratedMethods activateLeader(PlaceFamilyMemberOnOccupiedSpace permanentEffect,NetworkPlayer player,String cardName) throws InvalidActionTypeException {
		player.setPlayerPlayedLeaderCards(cardName);
		//empty the market
		for (int i=0;i<4;i++)
			player.personalMainBoard.familyMembersLocation.setFamilyMemberOnTheMarket(new FamilyMember(),i);
		//empty the production and the harvest area
		player.personalMainBoard.familyMembersLocation.changeFamilyMemberOnProductionOrHarvest(new ArrayList<FamilyMember>(), "Harvest");
		player.personalMainBoard.familyMembersLocation.changeFamilyMemberOnProductionOrHarvest(new ArrayList<FamilyMember>(), "Production");
	
		return decoratedMethods;
	}
	
	public DecoratedMethods activateLeader(SetColoredDicesValues permanentEffect,NetworkPlayer player,String cardName){
		player.setPlayerPlayedLeaderCards(cardName);
		Integer[] dices = player.personalMainBoard.getDiceValues();
		if(permanentEffect.boostOrSet==true){
			for(int i=0;i<3;i++)
				dices[i]+=permanentEffect.diceValue;
		}
		else {
			for(int i=0;i<3;i++)
				dices[i]=permanentEffect.diceValue;
		}
		player.personalMainBoard.setDiceValues(dices);
		
		return decoratedMethods;
	}
	
	public DecoratedMethods activateLeader(UncoloredMemberBonus permanentEffect,NetworkPlayer player,String cardName){
		player.setPlayerPlayedLeaderCards(cardName);
		Integer[] dices = player.personalMainBoard.getDiceValues();
		dices[3]+=permanentEffect.bonus;
		player.personalMainBoard.setDiceValues(dices);
		
		return decoratedMethods;
	}
	
	public DecoratedMethods activateLeader(VictoryForSupportingTheChurch permanentEffect,NetworkPlayer player,String cardName){
		player.setPlayerPlayedLeaderCards(cardName);
		ActionBonus[] faithBonus = player.personalMainBoard.faithBonuses;
		for(int i=0;i<16;i++)
			faithBonus[i].points.victory+=permanentEffect.victoryQty;
		player.personalMainBoard.faithBonuses = faithBonus;
		
		return decoratedMethods;
	}
	

	
	public void getInfo(InstantEffect effect,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{
		 Class[] cArg = new Class[2];
		 cArg[0] = effect.getClass();
		 cArg[1] = NetworkPlayer.class;
		 Method lMethod = (this.getClass().getMethod("getInfo",cArg));
		 lMethod.invoke(this,effect,player);
	 }
	 
	/*
	 * InstantEffect info
	 */
	
	public void getInfo (NoInstantEffect effect,NetworkPlayer player){
		player.setMessage("This Card doesn't give any Instant Effect");
	}
	
	public void getInfo (CoinForCard effect,NetworkPlayer player){
		player.setMessage("This Instant Effect gives " + effect.coinQty + " coins for " + effect.cardType + " cards");
	}
	
	public void getInfo (DoublePointsTransformation effect,NetworkPlayer player){
		player.setMessage("The Transformation 1 gives you ");
		printCardPoints(effect.points,player);
		player.setMessage(" for "); 
		printCardResources(effect.requestedForTransformation,player);
		
		player.setMessage("The Transformation 2 gives you ");
		printCardPoints(effect.points2,player);
		player.setMessage(" for "); 
		printCardResources(effect.requestedForTransformation2,player);
	}
	
	public void getInfo (DoubleResourcesTransformation effect,NetworkPlayer player){
		player.setMessage("The Transformation 1 gives you ");
		printCardResources(effect.resources,player);
		player.setMessage(" for "); 
		printCardResources(effect.requestedForTransformation,player);
		
		player.setMessage("The Transformation 2 gives you ");
		printCardResources(effect.resources2,player);
		player.setMessage(" for "); 
		printCardResources(effect.requestedForTransformation2,player);
	}
	
	public void getInfo (GetCard effect,NetworkPlayer player){
		if(("All").equals(effect.cardType))
			player.setMessage("This effect gives you any cards of value " + effect.cardValue);
		else
			player.setMessage("This effect gives you a " + effect.cardType + " card of value " + effect.cardValue);
	}
	
	public void getInfo (GetCardAndPoints effect,NetworkPlayer player){
		if(("All").equals(effect.cardType))
			player.setMessage("This effect gives you any cards of value " + effect.cardValue);
		else
			player.setMessage("This effect gives you a " + effect.cardType + " card of value " + effect.cardValue);
		player.setMessage("and ");
		printCardPoints(effect.points,player);
	}
	
	public void getInfo (GetCardAndResources effect,NetworkPlayer player){
		if(("All").equals(effect.cardType))
			player.setMessage("This effect gives you any cards of value " + effect.cardValue);
		else
			player.setMessage("This effect gives you a " + effect.cardType + " card of value " + effect.cardValue);
		player.setMessage("and ");
		printCardResources(effect.resources,player);
	}
	
	public void getInfo (GetDiscountedCard effect,NetworkPlayer player){
		player.setMessage("This effect gives you a " + effect.cardType + " card of value " + effect.cardValue + " with a dsicount of");
		printCardResources(effect.cardDiscount,player);
	}
	
	public void getInfo (HarvestProductionAction effect,NetworkPlayer player){
		player.setMessage("This effect gives you a " + effect.actionType + " action of value " + effect.actionValue);
	}
	
	public void getInfo (HarvestProductionAndPoints effect,NetworkPlayer player){
		player.setMessage("This effect gives you a " + effect.actionType + " action of value " + effect.actionValue);
		player.setMessage("and ");
		printCardPoints(effect.points,player);
	}
	
	public void getInfo (Points effect,NetworkPlayer player){
		player.setMessage("This Instant Effect gives you");
		printCardPoints(effect.points,player);
	}
	
	public void getInfo (PointsTransformation effect,NetworkPlayer player){
		player.setMessage("This Transformation gives you ");
		printCardPoints(effect.points,player);
		player.setMessage(" for "); 
		printCardResources(effect.requestedForTransformation,player);
	}
	
	public void getInfo (Resources effect,NetworkPlayer player){
		player.setMessage("This Instant Effect gives you");
		printCardResources(effect.resources,player);
	}
	
	public void getInfo (ResourcesAndPoints effect,NetworkPlayer player){
		player.setMessage("This Instant Effect gives you");
		printCardResources(effect.resources,player);
		printCardPoints(effect.points,player);
	}
	
	public void getInfo (ResourcesAndPointsTransformation effect,NetworkPlayer player){
		player.setMessage("This Transformation gives you");
		printCardResources(effect.resources,player);
		printCardPoints(effect.points,player);
		player.setMessage("for");
		printCardPoints(effect.requestedForTransformation,player);
	}
	
	public void getInfo (ResourcesTransformation effect,NetworkPlayer player){
		player.setMessage("This Transformation gives you");
		printCardResources(effect.resources,player);
		player.setMessage("for");
		printCardResources(effect.requestedForTransformation,player);
	}
	
	public void getInfo (SetFamilyMember effect,NetworkPlayer player){
		player.setMessage("This effect set a colored family member you chose to this value: " + effect.familyMemberValue);
	}
	
	public void getInfo (VictoryForCard effect,NetworkPlayer player){
		player.setMessage("This Instant Effect gives you " + effect.victoryQty + " Victory Points for each " + effect.cardType + " card you have");
	}
	
	public void getInfo (VictoryForMilitary effect,NetworkPlayer player){
		player.setMessage("This Instant Effect gives you " + effect.victoryQty + " Victory Points for each " + effect.militaryQty + " Military Points you have");
	}
	
	
	public void getInfo(LeaderRequestedObjects effect,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{
		 Class[] cArg = new Class[2];
		 cArg[0] = effect.getClass();
		 cArg[1] = NetworkPlayer.class;
		 Method lMethod = (this.getClass().getMethod("getInfo",cArg));
		 lMethod.invoke(this,effect,player);
	 }
	/*
	 * LeaderObject info
	 */
	
	public void getInfo (RequestedCard requested,NetworkPlayer player){
		player.setMessage("To activate this Leader you need " + requested.cardQty + " " + requested.cardType + " cards");
	}
	
	public void getInfo (RequestedCardPointsResources requested,NetworkPlayer player){
		player.setMessage("To activate this Leader you need " + requested.cardQty + " " + requested.cardType + " cards and");
		printCardResources(requested.resources,player);
		printCardPoints(requested.points,player);
	}
	
	public void getInfo (RequestedPoints requested,NetworkPlayer player){
		player.setMessage("To activate this Leader you need ");
		printCardPoints(requested.points,player);
	}
	
	public void getInfo (RequestedResources requested,NetworkPlayer player){
		player.setMessage("To activate this Leader you need ");
		printCardResources(requested.resources,player);
	}
	
	public void getInfo (RequestedSameCard requested,NetworkPlayer player){
		player.setMessage("To activate this Leader you need " + requested.cardQty + " cards of the same type");
	}
	
	public void getInfo (RequestedTwoCards requested,NetworkPlayer player){
		player.setMessage("To activate this Leader you need " + requested.cardQty + " " + requested.cardType + " cards and " + requested.cardQty2 + " " + requested.cardType2 + " cards");
	}
	
	
	public void getInfo(CharacterPermanentEffect effect,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{
		 Class[] cArg = new Class[2];
		 cArg[0] = effect.getClass();
		 cArg[1] = NetworkPlayer.class;
		 Method lMethod = (this.getClass().getMethod("getInfo",cArg));
		 lMethod.invoke(this,effect,player);
	 }
	
	/*
	 * CharacterPermanentEffect info
	 */
	
	public void getInfo (NoCharacterPermanentEffect effect,NetworkPlayer player){
		player.setMessage("This Card doesn't give any Permanent Effect");
	}
	
	
	public void getInfo (CardActionDiscount bonus,NetworkPlayer player){
		player.setMessage("This permanent effect gives you a discount of " + bonus.discount + " in action value to get a " + bonus.cardType + " card");
	}
	
	public void getInfo (CardActionResourcesDiscount bonus,NetworkPlayer player){
		player.setMessage("This permanent effect gives you a discount of " + bonus.discount + " in action value and a discount on the resources cost of ");
		printCardResources(bonus.resourcesDiscount,player);
		player.setMessage("to get a " + bonus.cardType + " card ");
	}
	
	public void getInfo (HarvestProductionBoost bonus,NetworkPlayer player){
		player.setMessage("This permanent effect gives you a boost of " + bonus.actionValue + " in action value on a " + bonus.actionType + " action");
	}
	
	public void getInfo (NoBoardBonuses bonus,NetworkPlayer player){
		player.setMessage("This permanent effect blocks all the bonuses on the Towers action spaces");
	}
	
	
	public void getInfo(ExcommunicationPermanentEffect effect,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{
		 Class[] cArg = new Class[2];
		 cArg[0] = effect.getClass();
		 cArg[1] = NetworkPlayer.class;
		 Method lMethod = (this.getClass().getMethod("getInfo",cArg));
		 lMethod.invoke(this,effect,player);
	 }
	/*
	 * ExcommunicationPermanentEffect info
	 */
	public void getInfo (NoVictoryForCard malus,NetworkPlayer player){
		player.setMessage("At the end of the game, you don’t score points for any of your " + malus.cardType);
	}
	public void getInfo (CardActionMalus malus,NetworkPlayer player){
		player.setMessage("Each time you take a " + malus.cardType + " card (through the action space or as a Card effect), your action receives a " +  -malus.malus + " reduction");
	}
	
	public void getInfo (DiceMalus malus,NetworkPlayer player){
		player.setMessage("All your colored Family Members receive a " + malus.malus + " reduction");
	}
	
	public void getInfo (HarvestProductionMalus malus,NetworkPlayer player){
		player.setMessage("Each time you perform a " + malus.actionType + " action (through the action space or as a Card effect), its value is decreased by " +  malus.malus);
	}
	
	public void getInfo (MalusForResources malus,NetworkPlayer player){
		player.setMessage("At the end of the game, you lose " + malus.victoryQty + " Victory Points for each " + malus.resourceQty + " resources (wood, stone, coin, servant) in your supply on your Personal Board.");
	}
	
	public void getInfo (MalusForResourcesCost malus,NetworkPlayer player){
		player.setMessage("At the end of the game, you lose " + malus.victoryQty + " Victory Points for each " + malus.resourceQty + " wood and stone on your Building Cards’ costs.");
	}
	
	public void getInfo (MalusVictoryForMilitary malus,NetworkPlayer player){
		player.setMessage("At the end of the game, you lose " + malus.victoryQty + " Victory Points for each " + malus.militaryQty + " Military Points you have.");
	}
	
	public void getInfo (MilitaryPointsMalus malus,NetworkPlayer player){
		player.setMessage("Each time you gain Military Points (from action spaces or from your Cards), gain " + malus.militaryQty + " fewer Military Points.");
	}
	
	public void getInfo (NoMarket malus,NetworkPlayer player){
		player.setMessage("You can’t permanently place your Family Members in the Market action spaces.");
	}
	
	public void getInfo (ResourcesMalus malus,NetworkPlayer player){
		if(malus.resources.woods>0)
			player.setMessage("Each time you receive woods or stones (from action spaces or from your Cards), you receive fewer wood or stone. In these quantities:");
		else if(malus.resources.coins>0)
			player.setMessage("Each time you receive coins (from action spaces or from your Cards) you receive fewer coins. In these quantities:");
		else
			player.setMessage("Each time you receive servants (from action spaces or from your Cards) you receive fewer servants. In these quantities:");
		printCardResources(malus.resources,player);
	}
	
	public void getInfo (ServantsMalus malus,NetworkPlayer player){
		player.setMessage("You have to spend " + malus.servantsQty + " servants to increase your action value by 1");
	}
	
	public void getInfo (SkipFirstTurn malus,NetworkPlayer player){
		player.setMessage("Each round, you skip your first turn. When all players have taken all their turns, you may still place your last Family Member");
	}
	
	public void getInfo (VictoryMalus malus,NetworkPlayer player){
		player.setMessage("At the end of the game, before the Final Scoring, you lose " + malus.victoryMalus + " Victory Points for every " + malus.victoryQty + " Victory Points you have");
	}
	
	public void getInfo(LeaderPermanentEffect effect,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{
		 Class[] cArg = new Class[2];
		 cArg[0] = effect.getClass();
		 cArg[1] = NetworkPlayer.class;	
		 Method lMethod = (this.getClass().getMethod("getInfo",cArg));
		 lMethod.invoke(this,effect,player);
	 }
	
	/*
	 * LeaderPermanentEffect info
	 */
	
	public void getInfo (AlreadyOccupiedTowerDiscount effect,NetworkPlayer player){
		player.setMessage("You don’t have to spend 3 coins when you place your Family Members in a Tower that is already occupied");
	}
	
	public void getInfo (CardCoinDiscount effect,NetworkPlayer player){
		player.setMessage("When you take Development Cards, you get a discount of " + effect.coinQty + " coins (if the card you are taking has coins in its cost.) This is not a discount on the coins you must spend if you take a Development Card from a Tower that’s already occupied");
	}
	
	public void getInfo (CopyLeaderAbility effect,NetworkPlayer player){
		player.setMessage("Copy the ability of another Leader Card already played by another player. Once you decide the ability to copy, it can’t be changed");
	}
	
	public void getInfo (DoubleResourcesFromDevelopment effect,NetworkPlayer player){
		player.setMessage("Each time you receive wood, stone, coins, or servants as an immediate effect from Development Cards (not from an action space), you receive the resources twice");
	}
	
	public void getInfo (NoMilitaryRequirementsForTerritory effect,NetworkPlayer player){
		player.setMessage("You don’t need to satisfy the Military Points requirement when you take Territory Cards");
	}
	
	public void getInfo (PlaceFamilyMemberOnOccupiedSpace effect,NetworkPlayer player){
		player.setMessage("You can place your Family Members in occupied action spaces");
	}
	
	public void getInfo (SetColoredDicesValues effect,NetworkPlayer player){
		if (effect.boostOrSet==true)
			player.setMessage("Your colored Family Members have a bonus of " + effect.diceValue + " on their value. (You can increase their value by spending servants or if you have Character Cards with this effect)");
		else
			player.setMessage("Your colored Family Members have a value of " + effect.diceValue + ", regardless of their related dice. (You can increase their value by spending servants or if you have Character Cards with this effect)");
	}
	
	public void getInfo (UncoloredMemberBonus effect,NetworkPlayer player){
		player.setMessage("Your uncolored Family Member has a bonus of " + effect.bonus + " on its value. (You can increase its value by spending servants or if you have Character Cards with this effect.)");
	}
	
	public void getInfo (VictoryForSupportingTheChurch effect,NetworkPlayer player){
		player.setMessage("You gain " + effect.victoryQty + " additional Victory Points when you support the Church in a Vatican Report phase.");
	}
	
	
	/*
	 * Leader Cards 
	 */
	
	public void getInfo(Effect permanentEffect,NetworkPlayer player) throws SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException	{
		Class[] cArg = new Class[2];
	    cArg[0] = permanentEffect.getClass();
	    cArg[1] = NetworkPlayer.class;
		Method lMethod;
		lMethod = (this.getClass().getMethod("getInfo",cArg));
		lMethod.invoke(this,permanentEffect,player);
	}
	
	
	//methods needed for the getInfo
	public void printCardResources (CardResources resources,NetworkPlayer player){
		if(resources.coins>0)
			player.setMessage(resources.coins + " coins");
		if(resources.woods>0)
			player.setMessage(resources.woods + " woods");
		if(resources.stones>0)
			player.setMessage(resources.stones + " stones");
		if(resources.servants>0)
			player.setMessage(resources.servants + " servants");
		if(resources.council>0)
			player.setMessage(resources.council + " Council Favor");
	}
	
	public void printCardPoints (CardPoints points,NetworkPlayer player){
		if(points.faith>0)
			player.setMessage(points.faith + " Faith Points");
		if(points.military>0)
			player.setMessage(points.military + " Military Points");
		if(points.victory>0)
			player.setMessage(points.victory + " Victory Points");
	}
	
	 
	
	//TODO getInfo methods
	
	/*
	
	//probably useless code
	//reflection to call the correct method getEffect
    public Effect getEffect(Effect effect)
	{
	try{
		Class[] cArg = new Class[1];
        cArg[0] = effect.getClass();
		Method method = (getClass().getMethod("getEffect",cArg));
		return (Effect)method.invoke(this,effect);
	}catch(Exception e){
		e.printStackTrace();
		return null;}
	}

    //overload of getEffect that returns the Effect casted at his dynamic type
    
    //InstantEffect
	public HarvestProductionAction getEffect(HarvestProductionAction effect)
	{
		return (HarvestProductionAction)effect;
	}
	
	public HarvestProductionAndPoints getEffect(HarvestProductionAndPoints effect)
	{
		return (HarvestProductionAndPoints)effect;
	}
	
	public Resources getEffect(Resources effect)
	{
		return (Resources)effect;
	}
	
	public ResourcesAndPoints getEffect(ResourcesAndPoints effect)
	{
		return (ResourcesAndPoints)effect;
	}
	
	public ResourcesAndPointsTransformation getEffect(ResourcesAndPointsTransformation effect)
	{
		return (ResourcesAndPointsTransformation)effect;
	}
	
	public ResourcesTransformation getEffect(ResourcesTransformation effect)
	{
		return (ResourcesTransformation)effect;
	}
	
	public DoubleResourcesTransformation getEffect(DoubleResourcesTransformation effect)
	{
		return (DoubleResourcesTransformation)effect;
	}
	
	public Points getEffect(Points effect)
	{
		return (Points)effect;
	}
	
	public PointsTransformation getEffect(PointsTransformation effect)
	{
		return (PointsTransformation)effect;
	}
	
	public DoublePointsTransformation getEffect(DoublePointsTransformation effect)
	{
		return (DoublePointsTransformation)effect;
	}
	
	public VictoryForCard getEffect(VictoryForCard effect)
	{
		return (VictoryForCard)effect;
	}
	
	public GetCard getEffect(GetCard effect)
	{
		return (GetCard)effect;
	}
	
	public GetDiscountedCard getEffect(GetDiscountedCard effect)
	{
		return (GetDiscountedCard)effect;
	}
	
	public GetCardAndResources getEffect(GetCardAndResources effect)
	{
		return (GetCardAndResources)effect;
	}
	
	public GetCardAndPoints getEffect(GetCardAndPoints effect)
	{
		return (GetCardAndPoints)effect;
	}
	
	public CoinForCard getEffect(CoinForCard effect)
	{
		return (CoinForCard)effect;
	}
	
	public VictoryForMilitary getEffect(VictoryForMilitary effect)
	{
		return (VictoryForMilitary) effect;
	}
	
	public SetFamilyMember getEffect(SetFamilyMember effect)
	{
		return (SetFamilyMember)effect;
	}

//LeaderPermannetEffect
	public UncoloredMemberBonus getEffect(UncoloredMemberBonus effect)
	{
		return (UncoloredMemberBonus)effect;
	}
	
	public AlreadyOccupiedTowerDiscount getEffect(AlreadyOccupiedTowerDiscount effect)
	{
		return (AlreadyOccupiedTowerDiscount)effect;
	}
	
	public VictoryForSupportingTheChurch getEffect(VictoryForSupportingTheChurch effect)
	{
		return (VictoryForSupportingTheChurch)effect;
	}
	
	public SetColoredDicesValues getEffect(SetColoredDicesValues effect)
	{
		return (SetColoredDicesValues)effect;
	}
	
	public CopyLeaderAbility getEffect(CopyLeaderAbility effect)
	{
		return (CopyLeaderAbility)effect;
	}
	
	public DoubleResourcesFromDevelopment getEffect(DoubleResourcesFromDevelopment effect)
	{
		return (DoubleResourcesFromDevelopment)effect;
	}
	
	public PlaceFamilyMemberOnOccupiedSpace getEffect(PlaceFamilyMemberOnOccupiedSpace effect)
	{
		return (PlaceFamilyMemberOnOccupiedSpace)effect;
	}
	
	public NoMilitaryRequirmentsForTerritory getEffect(NoMilitaryRequirmentsForTerritory effect)
	{
		return (NoMilitaryRequirmentsForTerritory)effect;
	}
	
	public CardCoinDiscount getEffect(CardCoinDiscount effect)
	{
		return (CardCoinDiscount)effect;
	}
	
//CharacterPermanentEffect
	
	public HarvestProductionBoost getEffect(HarvestProductionBoost effect)
	{
		return (HarvestProductionBoost)effect;
	}
	
	public NoBoardBonuses getEffect(NoBoardBonuses effect)
	{
		return (NoBoardBonuses)effect;
	}
	
	public CardActionResourcesDiscount getEffect(CardActionResourcesDiscount effect)
	{
		return (CardActionResourcesDiscount)effect;
	}
	
	public CardActionDiscount getEffect(CardActionDiscount effect)
	{
		return (CardActionDiscount)effect;
	}
	
//NoEffect
	
	public NoEffect getEffect(NoEffect effect)
	{
		return (NoEffect)effect;
	}
	*/
}
