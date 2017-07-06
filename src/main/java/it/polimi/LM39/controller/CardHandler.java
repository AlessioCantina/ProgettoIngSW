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
import it.polimi.LM39.server.NetworkPlayer;
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

/**
 * this class handles all the cards effects
 */
public class CardHandler {
	private GameHandler gameHandler;
	private DecoratedMethods decoratedMethods;
	
	/**
	 * constructor 
	 * @param gameHandler
	 * @param decoratedMethods
	 */
	public CardHandler(GameHandler gameHandler,DecoratedMethods decoratedMethods){
		this.gameHandler = gameHandler;
		this.decoratedMethods=decoratedMethods;
	}
	
	/**
	 * here is stored the color of the player with the DoubleResourcesFromDevelopment bonus
	 */
	private static String playerDoubleResourcesFromDevelopment = "";
	
	/**
	 * InstantEffects
	 */
	
	/**
	 * reflection to call the correct method that handles a specific instant effect
	 * @param instantEffect
	 * @param player
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void doInstantEffect(InstantEffect instantEffect,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{
		 Class[] cArg = new Class[2];
		 cArg[0] = instantEffect.getClass();
		 cArg[1] = NetworkPlayer.class;
		 Method lMethod = this.getClass().getMethod("doInstantEffect",cArg);
		 lMethod.invoke(this,instantEffect,player);
	}
 
 
	 /**
	  * reflection for instant effects used by building transformations
	  * @param instantEffect
	  * @param player
	  * @param fakePlayer
	  * @throws SecurityException
	  * @throws IllegalAccessException
	  * @throws IllegalArgumentException
	  * @throws InvocationTargetException
	  * @throws NoSuchMethodException
	  */
	 public void doInstantEffect(InstantEffect instantEffect,NetworkPlayer player, NetworkPlayer fakePlayer) throws  SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException	{
		 Class[] cArg = new Class[3];
		 cArg[0] = instantEffect.getClass();
		 cArg[1] = NetworkPlayer.class;
		 cArg[2] = NetworkPlayer.class;
		 Method lMethod = null;
		try {
			lMethod = this.getClass().getMethod("doInstantEffect",cArg);
		} catch (NoSuchMethodException e) {
			doInstantEffect(instantEffect,player);
			return;
		}
		lMethod.invoke(this,instantEffect,player,fakePlayer);
	 }
 
 
 	/**
 	 * to handle cards that does not have any instant effect
 	 * @param instantEffect
 	 * @param player
 	 * @throws NotEnoughResourcesException
 	 */
 	public void doInstantEffect(NoInstantEffect instantEffect,NetworkPlayer player) throws NotEnoughResourcesException{
 		//do nothing
 	}
 
 	/**
 	 * to handle CoinForCard instant effect
 	 * @param instantEffect
 	 * @param player
 	 * @throws NotEnoughResourcesException
 	 */
	public void doInstantEffect(CoinForCard instantEffect,NetworkPlayer player) throws NotEnoughResourcesException{
		//calculate the coins to receive by multiplying the possessed cards of a specific type by the coin quantity given by the card
		Integer coinQty=(player.personalBoard.getPossessions(instantEffect.cardType).size())*instantEffect.coinQty;
			player.resources.setCoins(coinQty);	
	}
	
	/**
	 * to handle DoublePointsTransformation instant effect
	 * @param instantEffect
	 * @param player
	 * @param fakePlayer
	 * @throws IOException
	 * @throws NotEnoughResourcesException
	 * @throws NotEnoughPointsException
	 * @throws InvalidInputException
	 */
	public void doInstantEffect(DoublePointsTransformation instantEffect,NetworkPlayer player,NetworkPlayer fakePlayer) throws IOException, NotEnoughResourcesException, NotEnoughPointsException, InvalidInputException{
		//ask to the player what exchange he wants to do
		player.setMessage("What exchange do you want to do? 1 or 2");
		//get the player response
		Integer choice = Integer.parseInt(player.sendMessage());
		if (choice==1){
			//subtract the resources from the player
			gameHandler.subCardResources(instantEffect.requestedForTransformation, fakePlayer);
			//add points to the player
			gameHandler.decoratedMethods.addCardPoints(instantEffect.points, fakePlayer);}
		else if(choice==2){
			//subtract the resources from the player
			gameHandler.subCardResources(instantEffect.requestedForTransformation2, fakePlayer);
			//add points to the player
			gameHandler.decoratedMethods.addCardPoints(instantEffect.points2, fakePlayer);}
		else
			throw new InvalidInputException("The exchange must be chosen between 1 and 2");
			
		
	}
	
	/**
	 * to handle DoubleResourcesTransformation instant effect
	 * @param instantEffect
	 * @param player
	 * @param fakePlayer
	 * @throws IOException
	 * @throws NotEnoughResourcesException
	 * @throws InvalidInputException
	 * @throws NotEnoughPointsException
	 */
	public void doInstantEffect(DoubleResourcesTransformation instantEffect,NetworkPlayer player,NetworkPlayer fakePlayer) throws IOException, NotEnoughResourcesException, InvalidInputException, NotEnoughPointsException{
		//ask to the player what exchange he wants to do
				player.setMessage("What exchange do you want to do? 1 or 2");
				//get the player response
				Integer choice = Integer.parseInt(player.sendMessage());
				if (choice==1){
					//subtract the resources from the player
					gameHandler.subCardResources(instantEffect.requestedForTransformation, fakePlayer);
					//add resources to the player
					gameHandler.decoratedMethods.addCardResources(instantEffect.resources, fakePlayer);}
				else if(choice==2){
					//subtract the resources from the player
					gameHandler.subCardResources(instantEffect.requestedForTransformation2, fakePlayer);
					//add resources to the player
					gameHandler.decoratedMethods.addCardResources(instantEffect.resources2, fakePlayer);}
				else
					throw new InvalidInputException("The exchange must be chosen between 1 and 2");
	}
	
	/**
	 * to handle GetCard instant effect
	 * @param instantEffect
	 * @param player
	 * @throws IOException
	 * @throws CardNotFoundException
	 * @throws NotEnoughResourcesException
	 * @throws NotEnoughPointsException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
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
						if((j==0 && ("Territory").equals(instantEffect.cardType) && (instantEffect.cardValue+qtyServants) >= gameHandler.mainBoard.getTowersValue()[i][j])
								|| (j==1 && ("Character").equals(instantEffect.cardType) && (instantEffect.cardValue+qtyServants) >= gameHandler.mainBoard.getTowersValue()[i][j])
								|| (j==2 && ("Building").equals(instantEffect.cardType) && (instantEffect.cardValue+qtyServants) >= gameHandler.mainBoard.getTowersValue()[i][j])
								|| (j==3 && ("Venture").equals(instantEffect.cardType) && (instantEffect.cardValue+qtyServants) >= gameHandler.mainBoard.getTowersValue()[i][j])
								|| (("All").equals(instantEffect.cardType) && (instantEffect.cardValue+qtyServants) >= gameHandler.mainBoard.getTowersValue()[i][j]))
							flag = gameHandler.getCard(gameHandler.cardNameToInteger(cardName), player, j);
						
						if (flag == false){
							player.resources.setServants(qtyServants);
							player.setMessage("You cannot get this card");
							doInstantEffect(instantEffect,player);
							return;
						}
						else{
							gameHandler.mainBoard.getCardNamesOnTheTowers()[i][j] = "";
						}
						
						//avoid useless cycles
						return;
					}
			}
			
	}
	
	/**
	 * to handle GetCardAndPoints instant effect
	 * @param instantEffect
	 * @param player
	 * @throws IOException
	 * @throws NotEnoughPointsException
	 * @throws CardNotFoundException
	 * @throws NotEnoughResourcesException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
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
	
	/**
	 * to handle GetCardAndResources instant effect
	 * @param instantEffect
	 * @param player
	 * @throws IOException
	 * @throws NotEnoughResourcesException
	 * @throws CardNotFoundException
	 * @throws NotEnoughPointsException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
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
	
	/**
	 * to handle GetDiscountedCard instant effect
	 * @param instantEffect
	 * @param player
	 * @throws IOException
	 * @throws CardNotFoundException
	 * @throws NotEnoughResourcesException
	 * @throws NotEnoughPointsException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
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
			String[][] cardNamesOnTheTowers = gameHandler.mainBoard.getCardNamesOnTheTowers();
			//looking for this card on the Towers
			for(int i=0;i<4;i++)
				for(int j=0;j<4;j++)
					if(cardNamesOnTheTowers[i][j].compareToIgnoreCase(cardName) == 0){
						//converting the card name to cardNumber
						Integer cardNumber = gameHandler.cardNameToInteger(cardName);
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
								if(!gameHandler.getCard(cardNumber, player, j))
									//if the player does not have enough resources to get the card it means that costCoins >= instantEffect.cardDiscount.coins
									//so we get the bonus back from the player
									player.resources.setCoins(-instantEffect.cardDiscount.coins);
							}
						else {
							CardResources costResources;
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
								player.setMessage("You cannot get this card");
								return;
							}
							CardResources bonusResources = new CardResources();
							//check the card costs and confront it with the bonus to know how many resources to give to the player as discount
							if(costResources.coins >= instantEffect.cardDiscount.coins)
								bonusResources.coins=instantEffect.cardDiscount.coins;
							else
								bonusResources.coins=costResources.coins;
							if(costResources.stones >= instantEffect.cardDiscount.stones)
								bonusResources.stones=instantEffect.cardDiscount.stones;								
							else
								bonusResources.stones=costResources.stones;
							
							if(costResources.woods >= instantEffect.cardDiscount.woods)
								bonusResources.woods=instantEffect.cardDiscount.woods;								
							else
								bonusResources.woods=costResources.woods;
							
							if(costResources.servants >= instantEffect.cardDiscount.servants)
								bonusResources.servants=instantEffect.cardDiscount.servants;
							else
								bonusResources.servants=costResources.servants;
							
							bonusResources.council = 0;
							//give the discount to the player
							gameHandler.decoratedMethods.addCardResources(bonusResources, player);
							//if the player failed to get the card for lack of resources or not enough space on the PersonalBoard, remove the bonus given
							if(!gameHandler.getCard(cardNumber, player, j)){
								player.resources.setServants(qtyServants);
								gameHandler.subCardResources(bonusResources, player);
							}
							else {
								gameHandler.mainBoard.getCardNamesOnTheTowers()[i][j] = "";
							}
						}
						//avoid useless cycles
						return;
					}
		}
	}
	
	/**
	 * to handle HarvestProductionAction instant effect
	 * @param instantEffect
	 * @param player
	 * @throws IOException
	 * @throws NotEnoughResourcesException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NotEnoughPointsException
	 * @throws InvalidActionTypeException
	 */
	public void doInstantEffect(HarvestProductionAction instantEffect,NetworkPlayer player) throws IOException, NotEnoughResourcesException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughPointsException, InvalidActionTypeException{
		//ask to the player if he wants to add servants to the action
		Integer qtyServants = gameHandler.decoratedMethods.addServants(player);
		//check if the effect is for harvest o production and call the correct method
		if(("Harvest").equals(instantEffect.actionType))
			gameHandler.decoratedMethods.activateHarvest(instantEffect.actionValue + qtyServants, player,gameHandler.personalBoardHandler,new FamilyMember());
		else if(("Production").equals(instantEffect.actionType))
			gameHandler.decoratedMethods.activateProduction(instantEffect.actionValue + qtyServants, player,gameHandler.personalBoardHandler,new FamilyMember());
	}
	
	/**
	 * to handle HarvestProductionAndPoints instant effect
	 * @param instantEffect
	 * @param player
	 * @throws NotEnoughPointsException
	 * @throws IOException
	 * @throws NotEnoughResourcesException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InvalidActionTypeException
	 */
	public void doInstantEffect(HarvestProductionAndPoints instantEffect,NetworkPlayer player) throws NotEnoughPointsException, IOException, NotEnoughResourcesException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidActionTypeException{
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
	
	/**
	 * to handle Points instant effect
	 * @param instantEffect
	 * @param player
	 * @throws NotEnoughPointsException
	 */
	public void doInstantEffect(Points instantEffect,NetworkPlayer player) throws NotEnoughPointsException{
		gameHandler.decoratedMethods.addCardPoints(instantEffect.points, player);
	}
	
	/**
	 * to handle PointsTransformation instant effect
	 * @param instantEffect
	 * @param player
	 * @param fakePlayer
	 * @throws NotEnoughResourcesException
	 * @throws NotEnoughPointsException
	 */
	public void doInstantEffect(PointsTransformation instantEffect,NetworkPlayer player,NetworkPlayer fakePlayer) throws NotEnoughResourcesException, NotEnoughPointsException{
		//check if the player has enough resources
		gameHandler.subCardResources(instantEffect.requestedForTransformation, fakePlayer);
		//add points to the player
		gameHandler.decoratedMethods.addCardPoints(instantEffect.points, fakePlayer);
	}

	/**
	 * to handle Resources instant effect
	 * @param instantEffect
	 * @param player
	 * @throws NotEnoughResourcesException
	 * @throws NotEnoughPointsException
	 */
	public void doInstantEffect(Resources instantEffect,NetworkPlayer player) throws NotEnoughResourcesException, NotEnoughPointsException{
		gameHandler.decoratedMethods.addCardResources(instantEffect.resources, player);
		//double instant bonus if the player has the leader effect DoubleResourcesFromDevelopment 
		if(playerDoubleResourcesFromDevelopment.equals(player.playerColor)){
			CardResources resourcesBonus = instantEffect.resources;
			resourcesBonus.council=0;
			gameHandler.decoratedMethods.addCardResources(resourcesBonus, player);
		}
		
	}
	
	/**
	 * to handle ResourcesAndPoints instant effect
	 * @param instantEffect
	 * @param player
	 * @throws NotEnoughPointsException
	 * @throws NotEnoughResourcesException
	 */
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
	
	/**
	 * to handle ResourcesAndPointsTransformation instant effect
	 * @param instantEffect
	 * @param player
	 * @param fakePlayer
	 * @throws NotEnoughPointsException
	 * @throws NotEnoughResourcesException
	 */
	public void doInstantEffect(ResourcesAndPointsTransformation instantEffect,NetworkPlayer player,NetworkPlayer fakePlayer) throws NotEnoughPointsException, NotEnoughResourcesException{
		//check if the player has enough points for the transformation
		gameHandler.subCardPoints(instantEffect.requestedForTransformation, fakePlayer);
		//making a ResourcesAndPoints effect and calling his method
		ResourcesAndPoints effect = new ResourcesAndPoints();
		effect.points = instantEffect.points;
		effect.resources = instantEffect.resources;
		doInstantEffect(effect,fakePlayer);
	}
	
	/**
	 * to handle ResourcesTransformation instant effect
	 * @param instantEffect
	 * @param player
	 * @param fakePlayer
	 * @throws NotEnoughResourcesException
	 * @throws NotEnoughPointsException
	 */
	public void doInstantEffect(ResourcesTransformation instantEffect,NetworkPlayer player,NetworkPlayer fakePlayer) throws NotEnoughResourcesException, NotEnoughPointsException{
		//checking if the player has enough resources
		gameHandler.subCardResources(instantEffect.requestedForTransformation, fakePlayer);
		//making a Resources effect and calling his method
		Resources resourcesEffect = new Resources();
		resourcesEffect.resources = instantEffect.resources;
		doInstantEffect(resourcesEffect,fakePlayer);
	}
	
	/**
	 * to handle SetFamilyMember instant effect
	 * @param instantEffect
	 * @param player
	 * @throws IOException
	 */
	public void doInstantEffect(SetFamilyMember instantEffect,NetworkPlayer player) throws IOException{
		//the color will be chosen by the user
		player.setMessage("What FamilyMember color do you want to set the value?");
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
	    	default: player.setMessage("Invalid Family Member color");
	    		doInstantEffect(instantEffect,player);
	    		return;
	}
		player.personalMainBoard.setDiceValues(diceValues);
	}

	/**
	 * to handle VictoryForCard instant effect
	 * @param instantEffect
	 * @param player
	 */
	public void doInstantEffect(VictoryForCard instantEffect,NetworkPlayer player){
		//calculate the victory points to receive by multiplying the possessed cards of a specific type by the victory quantity given by card
		Integer victoryQty=(player.personalBoard.getPossessions(instantEffect.cardType).size())*instantEffect.victoryQty;
			player.points.setVictory(victoryQty);
	}
	
	/**
	 * to handle VictoryForMilitary instantr effect
	 * @param instantEffect
	 * @param player
	 */
	public void doInstantEffect(VictoryForMilitary instantEffect,NetworkPlayer player){
		//calculate the victory points to receive by multiplying the possessed cards of a specific type by the victory quantity given by card
		Integer victoryQty=(player.points.getMilitary())*instantEffect.victoryQty;
		player.points.setVictory(victoryQty);
		
	}
	
	
	/**
	 * LeaderRequestedObjects
	 */
	
	/**
	 * reflection to call the correct method that handles a specific LeaderRequestedObjects
	 * @param requestedObject
	 * @param player
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public boolean checkLeaderRequestedObject(LeaderRequestedObjects requestedObject,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{	
		Class[] cArg = new Class[2];
		cArg[0] = requestedObject.getClass();
		cArg[1] = NetworkPlayer.class;
		Method lMethod = this.getClass().getMethod("checkLeaderRequestedObject",cArg);
		return (boolean)lMethod.invoke(this,requestedObject,player);
	}
	
	/**
	 * to handle RequestedCard requested object
	 * @param requestedObject
	 * @param player
	 * @return
	 */
	public boolean checkLeaderRequestedObject(RequestedCard requestedObject,NetworkPlayer player){
		//chech if the leader request all card types
		if (requestedObject.cardType.compareToIgnoreCase("all") == 0){
			if(player.personalBoard.getPossessions("Venture").size() >= requestedObject.cardQty
			&& player.personalBoard.getPossessions("Territory").size() >= requestedObject.cardQty
			&& player.personalBoard.getPossessions("Character").size() >= requestedObject.cardQty
			&& player.personalBoard.getPossessions("Building").size() >= requestedObject.cardQty)
				return true;
		}
		//check if the player has enough card of a type
		else if (player.personalBoard.getPossessions(requestedObject.cardType).size() >= requestedObject.cardQty)
			return true;
		return false;
	}
	
	/**
	 * to handle RequestedCardPointsResources requested object
	 * @param requestedObject
	 * @param player
	 * @return
	 */
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
	
	/**
	 * to handle RequestedPoints requested object
	 * @param requestedObject
	 * @param player
	 * @return
	 */
	public boolean checkLeaderRequestedObject(RequestedPoints requestedObject,NetworkPlayer player){
		if(player.points.getMilitary() >= requestedObject.points.military &&
				player.points.getFaith() >= requestedObject.points.faith &&
					player.points.getVictory() >= requestedObject.points.victory)
			return true;
		return false;
	}
	
	/**
	 * to handle RequestedResources requested object
	 * @param requestedObject
	 * @param player
	 * @return
	 */
	public boolean checkLeaderRequestedObject(RequestedResources requestedObject,NetworkPlayer player){
		if(player.resources.getWoods() >= requestedObject.resources.woods &&
				player.resources.getStones() >= requestedObject.resources.stones &&
				player.resources.getServants() >= requestedObject.resources.servants &&
				player.resources.getCoins() >= requestedObject.resources.coins)
			return true;
		return false;
	}
	
	/**
	 * to handle RequestedSameCard requested object
	 * @param requestedObject
	 * @param player
	 * @return
	 */
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
		
	/**
	 * to handle RequestedTwoCards requested object
	 * @param requestedObject
	 * @param player
	 * @return
	 */
	public boolean checkLeaderRequestedObject(RequestedTwoCards requestedObject,NetworkPlayer player){
		//flag1 and flag2 are false by default
		boolean flag1,flag2;
		RequestedCard requestedCard = new RequestedCard();
		//check the first condition on the requested cards
		requestedCard.cardQty=requestedObject.cardQty;
		requestedCard.cardType=requestedObject.cardType;
		flag1 = checkLeaderRequestedObject(requestedCard,player);
		//check the second condition on the requested cards
		requestedCard.cardQty=requestedObject.cardQty2;
		requestedCard.cardType=requestedObject.cardType2;
		flag2 = checkLeaderRequestedObject(requestedCard,player);
		//the two condition returned true the leader can be used
		return flag1 && flag2;
	}
		
	
	/**
	 * CharacterPermanentEffect
	 */
	
	/**
	 * reflection to call the correct method that handles a specific CharacterPermanentEffect
	 * @param permanentEffect
	 * @param player
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public DecoratedMethods activateCharacter(CharacterPermanentEffect permanentEffect,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{									
		Class[] cArg = new Class[2];
	    cArg[0] = permanentEffect.getClass();
	    cArg[1] = NetworkPlayer.class;
		Method lMethod = this.getClass().getMethod("activateCharacter",cArg);
		return (DecoratedMethods)lMethod.invoke(this,permanentEffect,player);
		}
	
	/**
	 * to handle NoCharacterPermanentEffect permanent effect
	 * @param permanentEffect
	 * @param player
	 * @return
	 */
	public DecoratedMethods activateCharacter(NoCharacterPermanentEffect permanentEffect, NetworkPlayer player){
		//do nothing
		return decoratedMethods;
	}
	
	/**
	 * to handle CardActionDiscount permanent effect
	 * @param permanentEffect
	 * @param player
	 * @return
	 */
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
			default: break;
		}
		for(int j=0;j<4;j++)
			towersValue[j][i]-=permanentEffect.discount;
		player.personalMainBoard.setTowersValue(towersValue);
		return decoratedMethods;
	}
	
	/**
	 * to handle CardActionResourcesDiscount permanent effect
	 * @param permanentEffect
	 * @param player
	 * @return
	 */
	public DecoratedMethods activateCharacter(CardActionResourcesDiscount permanentEffect, NetworkPlayer player){
		//need to check when a player get a card to set the discount like done for the GetDiscountedCard effect
		if(("Character").equals(permanentEffect.cardType)){
			decoratedMethods = new CharacterResourcesDiscountDecorator(decoratedMethods,permanentEffect.resourcesDiscount);
		}
		else if(("Venture").equals(permanentEffect.cardType) ){
			decoratedMethods = new VentureResourcesDiscountDecorator(decoratedMethods,permanentEffect.resourcesDiscount);
		}
		else if(("Building").equals(permanentEffect.cardType)){
			decoratedMethods = new BuildingResourcesDiscountDecorator(decoratedMethods,permanentEffect.resourcesDiscount);
		}
		
		//make a CardActionDiscount effect and call his method
		CardActionDiscount effect = new CardActionDiscount();
		effect.cardType = permanentEffect.cardType;
		effect.discount = permanentEffect.discount;
		activateCharacter(effect,player);
		
		return decoratedMethods;
	}
	
	/**
	 * to handle HarvestProductionBoost permanent effect
	 * @param permanentEffect
	 * @param player
	 * @return
	 */
	public DecoratedMethods activateCharacter(HarvestProductionBoost permanentEffect, NetworkPlayer player){
		//need to check when a player with this effect try to do a Production or Harvest and give him the bonus
		if(("Harvest").equals(permanentEffect.actionType)){
			decoratedMethods = new HarvestBoostDecorator(decoratedMethods,permanentEffect.actionValue);
		}
		else 
			decoratedMethods = new ProductionBoostDecorator(decoratedMethods,permanentEffect.actionValue);
	
		return decoratedMethods;
	}
	
	/**
	 * to handle NoBoardBonuses permanent effect
	 * @param permanentEffect
	 * @param player
	 * @return
	 */
	public DecoratedMethods activateCharacter(NoBoardBonuses permanentEffect, NetworkPlayer player){
		//create an empty towersBonuses and set it to the playerPersonalBoard
		ActionBonus[][] towersBonuses = new ActionBonus[4][4];
		ActionBonus bonus = new ActionBonus();
		bonus.resources = new CardResources();
		bonus.points = new CardPoints();
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				towersBonuses[i][j] = bonus;
		
		player.personalMainBoard.setTowersBonuses(towersBonuses);
		
		return decoratedMethods;
	}
		
	
	/**
	 * ExcommunicationPermanentEffect
	 */
		
	/**
	 * reflection to call the correct method that handles a specific ExcommunicationPermanentEffect
	 * @param permanentEffect
	 * @param player
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public DecoratedMethods activateExcommunication(ExcommunicationPermanentEffect permanentEffect,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{
		Class[] cArg = new Class[2];
	    cArg[0] = permanentEffect.getClass();
	    cArg[1] = NetworkPlayer.class;
		Method lMethod = this.getClass().getMethod("activateExcommunication",cArg);
		return (DecoratedMethods)lMethod.invoke(this,permanentEffect,player);
	}	
	
	/**
	 * to handle CardActionMalus permanent effect
	 * @param permanentEffect
	 * @param player
	 * @return
	 */
	public DecoratedMethods activateExcommunication(CardActionMalus permanentEffect,NetworkPlayer player){
		//make a CardActionDiscount effect that will act in the opposite way giving the discount parameter as a malus, then call his method
		CardActionDiscount effect = new CardActionDiscount();
		effect.cardType = permanentEffect.cardType;
		effect.discount = - permanentEffect.malus;
		activateCharacter(effect,player);
		
		return decoratedMethods;
	}
	
	/**
	 * to handle DiceMalus permanent effect
	 * @param permanentEffect
	 * @param player
	 * @return
	 */
	public DecoratedMethods activateExcommunication(DiceMalus permanentEffect,NetworkPlayer player){
		Integer[] diceValues = player.personalMainBoard.getDiceValues();
		for(int i=0;i<3;i++)
			diceValues[i] -= permanentEffect.malus;
		player.personalMainBoard.setDiceValues(diceValues);
		
		return decoratedMethods;
	}
	
	/**
	 * to handle HarvestProductionMalus permanent effect
	 * @param permanentEffect
	 * @param player
	 * @return
	 */
	public DecoratedMethods activateExcommunication(HarvestProductionMalus permanentEffect,NetworkPlayer player){
		if(("Harvest").equals(permanentEffect.actionType)){
			//if not set to false it would block the decoration
			decoratedMethods = new HarvestBoostDecorator(decoratedMethods,-permanentEffect.malus);
		}
		else {
			//if not set to false it would block the decoration
			decoratedMethods = new ProductionBoostDecorator(decoratedMethods,-permanentEffect.malus);
		}
		
		return decoratedMethods;
	}
	
	/**
	 * to handle MalusForResources permanent effect
	 * @param permanentEffect
	 * @param player
	 * @return
	 */
	public DecoratedMethods activateExcommunication(MalusForResources permanentEffect,NetworkPlayer player){
		Integer victoryMalus = ((player.resources.getCoins() + player.resources.getWoods() + player.resources.getStones() + player.resources.getServants()) / permanentEffect.resourceQty)* permanentEffect.victoryQty;
		if (player.points.getVictory() >= victoryMalus)
			player.points.setVictory(-victoryMalus);
		else
			player.points.setVictory(0);
		
		return decoratedMethods;
	}
	
	/**
	 * to handle MalusForResourcesCost permanent effect
	 * @param permanentEffect
	 * @param player
	 * @return
	 */
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
	
	/**
	 * to handle MalusVictoryForMilitary permanent effect
	 * @param permanentEffect
	 * @param player
	 * @return
	 */
	public DecoratedMethods activateExcommunication(MalusVictoryForMilitary permanentEffect,NetworkPlayer player){
		Integer victoryMalus = (player.points.getMilitary() / permanentEffect.militaryQty)* permanentEffect.victoryQty;
		if (player.points.getVictory() >= victoryMalus)
			player.points.setVictory(-victoryMalus);
		else
			player.points.setVictory(0);
		
		return decoratedMethods;
	}
	
	/**
	 * to handle MilitaryPointsMalus permanent effect
	 * @param permanentEffect
	 * @param player
	 * @return
	 */
	public DecoratedMethods activateExcommunication(MilitaryPointsMalus permanentEffect,NetworkPlayer player){
		decoratedMethods = new MilitaryPointsMalusDecorator(decoratedMethods,permanentEffect.militaryQty);
		
		return decoratedMethods;
	}
	
	/**
	 * to handle NoMarket permanent effect
	 * @param permanentEffect
	 * @param player
	 * @return
	 */
	public DecoratedMethods activateExcommunication(NoMarket permanentEffect,NetworkPlayer player){
		decoratedMethods = new NoMarketDecorator(decoratedMethods);
		return decoratedMethods;
	}
	
	/**
	 * to handle ResourcesMalus permanent effect
	 * @param permanentEffect
	 * @param player
	 * @return
	 */
	public DecoratedMethods activateExcommunication(ResourcesMalus permanentEffect,NetworkPlayer player){
		decoratedMethods = new ResourcesMalusDecorator(decoratedMethods,gameHandler,permanentEffect.resources);
		
		return decoratedMethods;
	}
	
	/**
	 * to handle ServantsMalus permanent effect
	 * @param permanentEffect
	 * @param player
	 * @return
	 */
	public DecoratedMethods activateExcommunication(ServantsMalus permanentEffect,NetworkPlayer player){
		decoratedMethods = new ServantsMalusDecorator(decoratedMethods,permanentEffect.servantsQty);
		return decoratedMethods;
	}
	
	/**
	 * to handle VictoryMalus permanent effect
	 * @param permanentEffect
	 * @param player
	 * @return
	 */
	public DecoratedMethods activateExcommunication(VictoryMalus permanentEffect,NetworkPlayer player){
		Integer victoryMalus = (player.points.getVictory() / permanentEffect.victoryQty)* permanentEffect.victoryMalus;
		player.points.setVictory(-victoryMalus);
		
		return decoratedMethods;
	}
	
	/**
	 * to handle SkipFirstTurn permanent effect
	 * @param permanentEffect
	 * @param player
	 * @return
	 */
	public DecoratedMethods activateExcommunication(SkipFirstTurn permanentEffect,NetworkPlayer player){
		//this excommunication is handled by run() in Game
		return decoratedMethods;
	}
	
	/**
	 * to handle NoVictoryForCard permanent effect
	 * @param permanentEffect
	 * @param player
	 * @return
	 */
	public DecoratedMethods activateExcommunication(NoVictoryForCard permanentEffect,NetworkPlayer player){
		//this excommunication is handled by calculateFinalPoints() in GameHandler
		return decoratedMethods;
	}
	
	
	
	
	/**
	 * LeaderPermanentEffect
	 */
	
	/**
	 * reflection to call the correct method that handles a specific Effect
	 * @param permanentEffect
	 * @param player
	 * @param cardName
	 * @return
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public DecoratedMethods activateLeader(Effect permanentEffect,NetworkPlayer player,String cardName) throws SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException	{
		Class[] cArg = new Class[3];
	    cArg[0] = permanentEffect.getClass();
	    cArg[1] = NetworkPlayer.class;
	    cArg[2] = cardName.getClass();
		Method lMethod;
		try {
			lMethod = this.getClass().getMethod("activateLeader",cArg);
			return (DecoratedMethods)lMethod.invoke(this,permanentEffect,player,cardName);
		} catch (NoSuchMethodException e) {
			Class[] cArg1 = new Class[2];
			cArg1[0] = cArg[0];
			cArg1[1] = cArg[1];
			lMethod = this.getClass().getMethod("doInstantEffect",cArg1);
			addPlayerInstantLeaderCard(cardName,player);
			lMethod.invoke(this,permanentEffect,player);
			return decoratedMethods;
		}
		
	}
	
	/**
	 * to handle AlreadyOccupiedTowerDiscount permanent effect
	 * @param permanentEffect
	 * @param player
	 * @param cardName
	 * @return
	 */
	public DecoratedMethods activateLeader(AlreadyOccupiedTowerDiscount permanentEffect,NetworkPlayer player,String cardName){
		addPlayerPlayedLeaderCard(cardName,player);
		player.personalMainBoard.occupiedTowerCost = 0;
		return decoratedMethods;
	}
	
	/**
	 * to handle CardCoinDiscount permanent effect
	 * @param permanentEffect
	 * @param player
	 * @param cardName
	 * @return
	 */
	public DecoratedMethods activateLeader(CardCoinDiscount permanentEffect,NetworkPlayer player,String cardName){
		addPlayerPlayedLeaderCard(cardName,player);
		decoratedMethods = new CardCoinDiscountDecorator(decoratedMethods,permanentEffect.coinQty);
		return decoratedMethods;
	}
	
	/**
	 * to handle CopyLeaderAbility permanent effect
	 * @param permanentEffect
	 * @param player
	 * @param cardName
	 * @return
	 * @throws CardNotFoundException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public DecoratedMethods activateLeader(CopyLeaderAbility permanentEffect,NetworkPlayer player,String cardName) throws CardNotFoundException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException{
		String response;
		if(player.copiedLeaderCard == ""){
			ArrayList<String> choosableCards = new ArrayList<String>();
			for (String playedCard : player.personalMainBoard.getPlayedLeaderCard())
				if(!player.getPlayerPlayedLeaderCards().contains(playedCard) && !player.getPlayerInstantLeaderCards().contains(playedCard))
					choosableCards.add(playedCard);
			
			if(choosableCards.isEmpty()){
				player.setMessage("There is no card you can copy");
				return decoratedMethods;
			}
			
			player.setMessage("What Leader do you want to copy?");
			for(String name : choosableCards){
				player.setMessage(name);
			}
			response = player.sendMessage();
			if(!choosableCards.contains(response)){
				player.setMessage("You must choose a Leader Card already played by one of your opponents");
				return activateLeader(permanentEffect,player,cardName);
			}
			//add the card to the player copiedLeaderCard attribute to prevent that the player copy more than one effect violating the rule of this effect
			player.copiedLeaderCard = response;
			Leader leader = gameHandler.mainBoard.leaderMap.get(response);
			if(!(leader.effect instanceof InstantEffect))
				//add the card to the played cards list only if the player can copy at least one leader card and the card is not copying an instant effect
				addPlayerPlayedLeaderCard(cardName,player);
			
			activateLeader(leader.effect,player,cardName);
		}
		else{
			Leader leader = gameHandler.mainBoard.leaderMap.get(player.copiedLeaderCard);
			activateLeader(leader.effect,player,cardName);
		}
		
		return decoratedMethods;
	}
	
	/**
	 * to handle DoubleResourcesFromDevelopment permanent effect
	 * this method is synchronized to avoid multiple assignment to the static variable
	 * @param permanentEffect
	 * @param player
	 * @param cardName
	 * @return
	 */
	public synchronized DecoratedMethods activateLeader(DoubleResourcesFromDevelopment permanentEffect,NetworkPlayer player,String cardName) {
		addPlayerPlayedLeaderCard(cardName,player);
		playerDoubleResourcesFromDevelopment = player.playerColor;
		
		return decoratedMethods;
	}
	
	
	/**
	 * to handle NoMilitaryRequirementsForTerritory permanent effect
	 * @param permanentEffect
	 * @param player
	 * @param cardName
	 * @return
	 */
	public DecoratedMethods activateLeader(NoMilitaryRequirementsForTerritory permanentEffect,NetworkPlayer player,String cardName) {
		addPlayerPlayedLeaderCard(cardName,player);
		for(int i=0;i<4;i++)
			player.personalMainBoard.militaryForTerritory[i]=0;
		
		return decoratedMethods;
	}
	
	/**
	 * to handle PlaceFamilyMemberOnOccupiedSpace permanent effect
	 * @param permanentEffect
	 * @param player
	 * @param cardName
	 * @return
	 * @throws InvalidActionTypeException
	 */
	public DecoratedMethods activateLeader(PlaceFamilyMemberOnOccupiedSpace permanentEffect,NetworkPlayer player,String cardName) throws InvalidActionTypeException {
		addPlayerPlayedLeaderCard(cardName,player);
		//empty the market
		for (int i=0;i<4;i++)
			player.personalMainBoard.familyMembersLocation.setFamilyMemberOnTheMarket(new FamilyMember(),i);
		//empty the production and the harvest area
		player.personalMainBoard.familyMembersLocation.changeFamilyMemberOnProductionOrHarvest(new ArrayList<FamilyMember>(), "Harvest");
		player.personalMainBoard.familyMembersLocation.changeFamilyMemberOnProductionOrHarvest(new ArrayList<FamilyMember>(), "Production");
	
		return decoratedMethods;
	}
	
	/**
	 * to handle SetColoredDicesValues permanent effect
	 * @param permanentEffect
	 * @param player
	 * @param cardName
	 * @return
	 */
	public DecoratedMethods activateLeader(SetColoredDicesValues permanentEffect,NetworkPlayer player,String cardName){
		addPlayerPlayedLeaderCard(cardName,player);
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
	
	/**
	 * to handle UncoloredMemberBonus permanent effect
	 * @param permanentEffect
	 * @param player
	 * @param cardName
	 * @return
	 */
	public DecoratedMethods activateLeader(UncoloredMemberBonus permanentEffect,NetworkPlayer player,String cardName){
		addPlayerPlayedLeaderCard(cardName,player);
		Integer[] dices = player.personalMainBoard.getDiceValues();
		dices[3]+=permanentEffect.bonus;
		player.personalMainBoard.setDiceValues(dices);
		
		return decoratedMethods;
	}
	
	/**
	 * to handle VictoryForSupportingTheChurch permanent effect
	 * @param permanentEffect
	 * @param player
	 * @param cardName
	 * @return
	 */
	public DecoratedMethods activateLeader(VictoryForSupportingTheChurch permanentEffect,NetworkPlayer player,String cardName){
		addPlayerPlayedLeaderCard(cardName,player);
		ActionBonus[] faithBonus = player.personalMainBoard.faithBonuses;
		for(int i=0;i<16;i++)
			faithBonus[i].points.victory+=permanentEffect.victoryQty;
		player.personalMainBoard.faithBonuses = faithBonus;
		
		return decoratedMethods;
	}
	
	/**
	 * to add a leader card to the list of the leader cards played by a player
	 * @param cardName
	 * @param player
	 */
	private void addPlayerPlayedLeaderCard(String cardName,NetworkPlayer player){
    	if(!player.getPlayerPlayedLeaderCards().contains(cardName)){
    		player.setPlayerPlayedLeaderCard(cardName);
    		gameHandler.mainBoard.setPlayedLeaderCard(cardName);
    	}
	}
	
	/**
	 * to add a leader card to the list of the leader cards that gives instant bonuses played by a player
	 * @param cardName
	 * @param player
	 */
	private void addPlayerInstantLeaderCard(String cardName, NetworkPlayer player){
		if(!player.getPlayerInstantLeaderCards().contains(cardName))
			player.setPlayerInstantLeaderCard(cardName);
		if(!gameHandler.mainBoard.getPlayedLeaderCard().contains(cardName))
			gameHandler.mainBoard.setPlayedLeaderCard(cardName);
	}
	
	/**
	 * reflection to call the correct method that send to the player a specific InstantEffect info
	 * @param effect
	 * @param player
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void getInfo(InstantEffect effect,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{
		 Class[] cArg = new Class[2];
		 cArg[0] = effect.getClass();
		 cArg[1] = NetworkPlayer.class;
		 Method lMethod = (this.getClass().getMethod("getInfo",cArg));
		 lMethod.invoke(this,effect,player);
	 }
	 
	/**
	 * InstantEffect info
	 */
	
	public void getInfo (NoInstantEffect effect,NetworkPlayer player){
		player.setMessage("This Card does not give any Instant Effect");
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
		player.setMessage("This effect gives you a " + effect.cardType + " card of value " + effect.cardValue + " with a discount of");
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
	
	
	/**
	 * reflection to call the correct method that send to the player a specific LeaderRequestedObjects info
	 * @param effect
	 * @param player
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void getInfo(LeaderRequestedObjects effect,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{
		 Class[] cArg = new Class[2];
		 cArg[0] = effect.getClass();
		 cArg[1] = NetworkPlayer.class;
		 Method lMethod = this.getClass().getMethod("getInfo",cArg);
		 lMethod.invoke(this,effect,player);
	 }
	/**
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
	
	/**
	 * reflection to call the correct method that send to the player a specific CharacterPermanentEffect info
	 * @param effect
	 * @param player
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void getInfo(CharacterPermanentEffect effect,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{
		 Class[] cArg = new Class[2];
		 cArg[0] = effect.getClass();
		 cArg[1] = NetworkPlayer.class;
		 Method lMethod = this.getClass().getMethod("getInfo",cArg);
		 lMethod.invoke(this,effect,player);
	 }
	
	/**
	 * CharacterPermanentEffect info
	 */
	
	public void getInfo (NoCharacterPermanentEffect effect,NetworkPlayer player){
		player.setMessage("This Card does not give any Permanent Effect");
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
	
	/**
	 * reflection to call the correct method that send to the player a specific ExcommunicationPermanentEffect info
	 * @param effect
	 * @param player
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void getInfo(ExcommunicationPermanentEffect effect,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{
		 Class[] cArg = new Class[2];
		 cArg[0] = effect.getClass();
		 cArg[1] = NetworkPlayer.class;
		 Method lMethod = this.getClass().getMethod("getInfo",cArg);
		 lMethod.invoke(this,effect,player);
	 }
	
	/**
	 * ExcommunicationPermanentEffect info
	 */
	
	public void getInfo (NoVictoryForCard malus,NetworkPlayer player){
		player.setMessage("At the end of the game, you do not score points for any of your " + malus.cardType);
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
		player.setMessage("You cannot permanently place your Family Members in the Market action spaces.");
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
	
	/**
	 * reflection to call the correct method that send to the player a specific LeaderPermanentEffect info
	 * @param effect
	 * @param player
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void getInfo(LeaderPermanentEffect effect,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{
		 Class[] cArg = new Class[2];
		 cArg[0] = effect.getClass();
		 cArg[1] = NetworkPlayer.class;	
		 Method lMethod = this.getClass().getMethod("getInfo",cArg);
		 lMethod.invoke(this,effect,player);
	 }
	
	/**
	 * LeaderPermanentEffect info
	 */
	
	public void getInfo (AlreadyOccupiedTowerDiscount effect,NetworkPlayer player){
		player.setMessage("You do not have to spend 3 coins when you place your Family Members in a Tower that is already occupied");
	}
	
	public void getInfo (CardCoinDiscount effect,NetworkPlayer player){
		player.setMessage("When you take Development Cards, you get a discount of " + effect.coinQty + " coins (if the card you are taking has coins in its cost.) This is not a discount on the coins you must spend if you take a Development Card from a Tower that’s already occupied");
	}
	
	public void getInfo (CopyLeaderAbility effect,NetworkPlayer player){
		player.setMessage("Copy the ability of another Leader Card already played by another player. Once you decide the ability to copy, it cannot be changed");
	}
	
	public void getInfo (DoubleResourcesFromDevelopment effect,NetworkPlayer player){
		player.setMessage("Each time you receive wood, stone, coins, or servants as an immediate effect from Development Cards (not from an action space), you receive the resources twice");
	}
	
	public void getInfo (NoMilitaryRequirementsForTerritory effect,NetworkPlayer player){
		player.setMessage("You do not need to satisfy the Military Points requirement when you take Territory Cards");
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
	
	
	/**
	 * reflection to call the correct method that send to the player a specific Effect info
	 * @param permanentEffect
	 * @param player
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public void getInfo(Effect permanentEffect,NetworkPlayer player) throws SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException	{
		Class[] cArg = new Class[2];
	    cArg[0] = permanentEffect.getClass();
	    cArg[1] = NetworkPlayer.class;
		Method lMethod;
		lMethod = this.getClass().getMethod("getInfo",cArg);
		lMethod.invoke(this,permanentEffect,player);
	}
	
	
	/**
	 * to send the resources values to the player
	 * @param resources
	 * @param player
	 */
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
	
	/**
	 * to send the points values to the player
	 * @param points
	 * @param player
	 */
	public void printCardPoints (CardPoints points,NetworkPlayer player){
		if(points.faith>0)
			player.setMessage(points.faith + " Faith Points");
		if(points.military>0)
			player.setMessage(points.military + " Military Points");
		if(points.victory>0)
			player.setMessage(points.victory + " Victory Points");
	}
	
}