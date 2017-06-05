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
import it.polimi.LM39.model.NoEffect;
import it.polimi.LM39.server.NetworkPlayer;
import it.polimi.LM39.model.PlayerResources;
import it.polimi.LM39.model.characterpermanenteffect.*;
import it.polimi.LM39.model.excommunicationpermanenteffect.*;
import it.polimi.LM39.model.instanteffect.*;
import it.polimi.LM39.model.leaderpermanenteffect.*;
import it.polimi.LM39.model.leaderobject.*;
import it.polimi.LM39.model.ActionBonus;
import it.polimi.LM39.model.Building;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.Venture;
import it.polimi.LM39.model.Character;

public class CardHandler {
	private GameHandler gameHandler;
	
	public CardHandler(GameHandler gameHandler){
		this.gameHandler = gameHandler;
	}
	
	private NetworkPlayer playerDoubleResourcesFromDevelopment;
	
	/*
	 * InstantEffect
	 */
	
 void doInstantEffect(InstantEffect instantEffect,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{
	 Class[] cArg = new Class[2];
	 cArg[0] = instantEffect.getClass();
	 cArg[1] = player.getClass();
	 Method lMethod = (this.getClass().getMethod("doInstantEffect",cArg));
	 lMethod.invoke(instantEffect,player);
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
			gameHandler.addCardPoints(instantEffect.points, player);}
		else if(choice==2){
			//subtract the resources from the player
			gameHandler.subCardResources(instantEffect.requestedForTransformation2, player);
			//add points to the player
			gameHandler.addCardPoints(instantEffect.points2, player);}
		else
			throw new InvalidInputException("The exchange must be chosen between 1 and 2");
			
		
	}
	
	public void doInstantEffect(DoubleResourcesTransformation instantEffect,NetworkPlayer player) throws IOException, NotEnoughResourcesException, InvalidInputException{
		//ask to the player what exchange he wants to do
				player.setMessage("What exchange do you want to do? 1 or 2");
				//get the player response
				Integer choice = Integer.parseInt(player.sendMessage());
				if (choice==1){
					//subtract the resources from the player
					gameHandler.subCardResources(instantEffect.requestedForTransformation, player);
					//add resources to the player
					gameHandler.addCardResources(instantEffect.resources, player);}
				else if(choice==2){
					//subtract the resources from the player
					gameHandler.subCardResources(instantEffect.requestedForTransformation2, player);
					//add resources to the player
					gameHandler.addCardResources(instantEffect.resources2, player);}
				else
					throw new InvalidInputException("The exchange must be chosen between 1 and 2");
	}
	
	public void doInstantEffect(GetCard instantEffect,NetworkPlayer player) throws IOException, CardNotFoundException, NotEnoughResourcesException, NotEnoughPointsException{
		// ask to the player if he wants to use this effect
		player.setMessage("Do you want to use this effect? yes or no");
		String response = player.sendMessage();
		if(("yes").equals(response)){
			// ask to the player what card he wants
			player.setMessage("What card do you want?");
			//get the player response
			String cardName = player.sendMessage();
			FamilyMember familyMember = new FamilyMember();
			familyMember.color = "uncolored";
			familyMember.playerColor = player.playerColor;
			
			Integer qtyServants = gameHandler.addServants(player);
			familyMember.setServants(qtyServants);
			
			//converting the card name to cardNumber
			Integer cardNumber = gameHandler.cardNameToInteger(cardName,player.personalMainBoard.getCardNamesOnTheTowers(),player.personalMainBoard.getCardsOnTheTowers());
			boolean flag = gameHandler.addFamilyMemberToTheTower(familyMember,cardNumber,player);
			
			if (flag==false)
				player.resources.setServants(qtyServants);
			
			else{
				Integer[][] CardsOnTheTowers = player.personalMainBoard.getCardsOnTheTowers();
				//looking for this card on the Towers
				for(int i=0;i<4;i++)
					for(int j=0;j<4;j++)
						if(CardsOnTheTowers[i][j]==cardNumber){
							familyMember=null;
							player.personalMainBoard.familyMembersLocation.setFamilyMemberOnTheTower(familyMember, i, j);
							//avoid useless cicles
							return;
						}
				}
			}
		else if(!("no").equals(response)){
			player.setMessage("You must answer yes or no");
			doInstantEffect(instantEffect,player);
		}
	}
	
	public void doInstantEffect(GetCardAndPoints instantEffect,NetworkPlayer player) throws IOException, NotEnoughPointsException, CardNotFoundException, NotEnoughResourcesException{
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
	
	public void doInstantEffect(GetCardAndResources instantEffect,NetworkPlayer player) throws IOException, NotEnoughResourcesException, CardNotFoundException, NotEnoughPointsException{
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
	
	public void doInstantEffect(GetDiscountedCard instantEffect,NetworkPlayer player) throws IOException, CardNotFoundException, NotEnoughResourcesException{
		// ask to the player if he wants to use this effect
		player.setMessage("Do you want to use this effect? yes or no");
		String response = player.sendMessage();
		if(("yes").equals(response)){
			// ask to the player what card he wants
			player.setMessage("What card do you want?");
			String cardName = player.sendMessage();
			//converting the card name to cardNumber
			Integer cardNumber = gameHandler.cardNameToInteger(cardName,player.personalMainBoard.getCardNamesOnTheTowers(),player.personalMainBoard.getCardsOnTheTowers());
			Integer[][] CardsOnTheTowers = player.personalMainBoard.getCardsOnTheTowers();
			//looking for this card on the Towers
			for(int i=0;i<4;i++)
				for(int j=0;j<4;j++)
					if(CardsOnTheTowers[i][j]==cardNumber){
						//if the card is a Territory the discount get lost
						if(j==1)
							gameHandler.getCard(cardNumber, player, j);
						//if the card is Character the discount is only on coins
						else if(j==2){
								Character character = MainBoard.characterMap.get(cardNumber);
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
							if(j==3){
								Building building = MainBoard.buildingMap.get(cardNumber);
								costResources = building.costResources;
							}
							//if the card is a Venture
							else if(j==4){
								Venture venture = MainBoard.ventureMap.get(cardNumber);
								costResources = venture.costResources;
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
							gameHandler.addCardResources(bonusResources, player);
							//if the player failed to get the card for lack of resources or not enough space on the PersonalBoard, remove the bonus given
							if(!gameHandler.getCard(cardNumber, player, j))
								gameHandler.subCardResources(bonusResources, player);
						}
						//avoid useless cicles
						return;
					}
		}
		else if(!("no").equals(response)){
			player.setMessage("You must answer yes or no");
			doInstantEffect(instantEffect,player);
		}
	}
	
	public void doInstantEffect(HarvestProductionAction instantEffect,NetworkPlayer player) throws IOException, NotEnoughResourcesException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughPointsException{
		//ask to the player if he wants to add servants to the action
		Integer qtyServants = gameHandler.addServants(player);
		//check if the effect is for harvest o production and call the correct method
		if(("Harvest").equals(instantEffect.actionType))
			gameHandler.playerBoardHandler.activateHarvest(instantEffect.actionValue + qtyServants, player);
		else if(("Production").equals(instantEffect.actionType))
			gameHandler.playerBoardHandler.activateProduction(instantEffect.actionValue + qtyServants, player);
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
		gameHandler.addCardPoints(instantEffect.points, player);
	}
	
	public void doInstantEffect(PointsTransformation instantEffect,NetworkPlayer player) throws NotEnoughResourcesException, NotEnoughPointsException{
		//check if the player has enough resources
		gameHandler.subCardResources(instantEffect.requestedForTransformation, player);
		//add points to the player
		gameHandler.addCardPoints(instantEffect.points, player);
	}

	public void doInstantEffect(Resources instantEffect,NetworkPlayer player) throws NotEnoughResourcesException{
		gameHandler.addCardResources(instantEffect.resources, player);
		//double instant bonus if the player has the leader effect DoubleResourcesFromDevelopment 
		if(this.playerDoubleResourcesFromDevelopment==player){
			CardResources resourcesBonus = instantEffect.resources;
			resourcesBonus.council=0;
			gameHandler.addCardResources(resourcesBonus, player);
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
	
	public void doInstantEffect(ResourcesTransformation instantEffect,NetworkPlayer player) throws NotEnoughResourcesException{
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
		cArg[1] = player.getClass();
		Method lMethod = (this.getClass().getMethod("checkLeaderRequestedObject",cArg));
		return (boolean)lMethod.invoke(requestedObject,player);
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
	
	void activateCharacter(CharacterPermanentEffect permanentEffect,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{									
		Class[] cArg = new Class[2];
	    cArg[0] = permanentEffect.getClass();
	    cArg[1] = player.getClass();
		Method lMethod = (this.getClass().getMethod("activateCharacter",cArg));
		lMethod.invoke(permanentEffect,player);
		}
	
	public void activateCharacter(CardActionDiscount permanentEffect, NetworkPlayer player){
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
	}
	
	public void activateCharacter(CardActionResourcesDiscount permanentEffect, NetworkPlayer player){
		//make a CardActionDiscount effect and call his method
		CardActionDiscount effect = new CardActionDiscount();
		effect.cardType = permanentEffect.cardType;
		effect.discount = permanentEffect.discount;
		activateCharacter(effect,player);
		//need to check when a player get a card to set the discount like done for the GetDiscountedCard effect
		if(("Character").equals(permanentEffect.cardType))
			gameHandler = new CharacterResourcesDiscountDecorator(gameHandler,permanentEffect.resourcesDiscount,player);
		else if(("Venture").equals(permanentEffect.cardType))
			gameHandler = new VentureResourcesDiscountDecorator(gameHandler,permanentEffect.resourcesDiscount,player);
		else if(("Building").equals(permanentEffect.cardType))
			gameHandler = new BuildingResourcesDiscountDecorator(gameHandler,permanentEffect.resourcesDiscount,player);
	}
	
	
	public void activateCharacter(HarvestProductionBoost permanentEffect, NetworkPlayer player){
		//need to check when a player with this effect try to do a Production or Harvest and give him the bonus
		if(("Harvest").equals(permanentEffect.actionType))
			gameHandler.playerBoardHandler = new HarvestBoostDecorator(gameHandler.playerBoardHandler,permanentEffect.actionValue,player);
		else
			gameHandler.playerBoardHandler = new ProductionBoostDecorator(gameHandler.playerBoardHandler,permanentEffect.actionValue,player); 
	}
	
	public void activateCharacter(NoBoardBonuses permanentEffect, NetworkPlayer player){
		//create an empty towersBonuses and set it to the playerPersonalBoard
		ActionBonus[][] towersBonuses = new ActionBonus[4][4];
		player.personalMainBoard.setTowersBonuses(towersBonuses);
	}
		
	
	/*
	 * ExcommunicationPermanentEffect
	 */
		
	public void activateExcommunication(ExcommunicationPermanentEffect permanentEffect,NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{
		Class[] cArg = new Class[2];
	    cArg[0] = permanentEffect.getClass();
	    cArg[1] = player.getClass();
		Method lMethod = (this.getClass().getMethod("activateExcommunication",cArg));
		lMethod.invoke(permanentEffect,player);
}
	
	public void activateExcommunication(CardActionMalus permanentEffect,NetworkPlayer player){
		//make a CardActionDiscount effect that will act in the opposite way giving the discount parameter as a malus, then call his method
		CardActionDiscount effect = new CardActionDiscount();
		effect.cardType = permanentEffect.cardType;
		effect.discount = - permanentEffect.malus;
		activateCharacter(effect,player);
	}
	
	public void activateExcommunication(DiceMalus permanentEffect,NetworkPlayer player){
		Integer[] diceValues = player.personalMainBoard.getDiceValues();
		for(int i=0;i<3;i++)
			diceValues[i] -= permanentEffect.malus;
		player.personalMainBoard.setDiceValues(diceValues);
	}
	
	public void activateExcommunication(HarvestProductionMalus permanentEffect,NetworkPlayer player){
		if(("Harvest").equals(permanentEffect.actionType))
			gameHandler.playerBoardHandler = new HarvestBoostDecorator(gameHandler.playerBoardHandler,-permanentEffect.malus,player);
		else
			gameHandler.playerBoardHandler = new ProductionBoostDecorator(gameHandler.playerBoardHandler,-permanentEffect.malus,player); 
	}
	
	public void activateExcommunication(MalusForResources permanentEffect,NetworkPlayer player){
		Integer victoryMalus = ((player.resources.getCoins() + player.resources.getWoods() + player.resources.getStones() + player.resources.getServants()) / permanentEffect.resourceQty)* permanentEffect.victoryQty;
		if (player.points.getVictory() >= victoryMalus)
			player.points.setVictory(-victoryMalus);
		else
			player.points.setVictory(0);
	}
	
	public void activateExcommunication(MalusForResourcesCost permanentEffect,NetworkPlayer player){
		ArrayList<Integer> buildings = player.personalBoard.getPossessions("Building");
		Integer victoryMalus = 0;
		for (Integer i : buildings)
			victoryMalus=MainBoard.buildingMap.get(i).costResources.woods + MainBoard.buildingMap.get(i).costResources.stones;
		victoryMalus = (victoryMalus / permanentEffect.resourceQty)* permanentEffect.victoryQty;
		if (player.points.getVictory() >= victoryMalus)
			player.points.setVictory(-victoryMalus);
		else
			player.points.setVictory(0);
	}
	
	public void activateExcommunication(MalusVictoryForMilitary permanentEffect,NetworkPlayer player){
		Integer victoryMalus = (player.points.getMilitary() / permanentEffect.militaryQty)* permanentEffect.victoryQty;
		if (player.points.getVictory() >= victoryMalus)
			player.points.setVictory(-victoryMalus);
		else
			player.points.setVictory(0);
	}
	
	public void activateExcommunication(MilitaryPointsMalus permanentEffect,NetworkPlayer player){
		gameHandler = new MilitaryPointsMalusDecorator(gameHandler,permanentEffect.militaryQty ,player);
	}
	
	public void activateExcommunication(NoMarket permanentEffect,NetworkPlayer player){
		gameHandler = new NoMarketDecorator(gameHandler,player);
	}
	
	public void activateExcommunication(ResourcesMalus permanentEffect,NetworkPlayer player){
		gameHandler = new ResourcesMalusDecorator(gameHandler,permanentEffect.resources,player);
	}
	
	public void activateExcommunication(ServantsMalus permanentEffect,NetworkPlayer player){
		gameHandler = new ServantsMalusDecorator(gameHandler,permanentEffect.servantsQty,player);
	}
	
	public void activateExcommunication(VictoryMalus permanentEffect,NetworkPlayer player){
		Integer victoryMalus = (player.points.getVictory() / permanentEffect.victoryQty)* permanentEffect.victoryMalus;
		player.points.setVictory(-victoryMalus);
	}
	
	
	/*
	 * LeaderPermanentEffect
	 */
	
	public void activateLeader(Effect permanentEffect,NetworkPlayer player) throws SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException	{
		Class[] cArg = new Class[2];
	    cArg[0] = permanentEffect.getClass();
	    cArg[1] = player.getClass();
		Method lMethod;
		try {
			lMethod = (this.getClass().getMethod("activateLeader",cArg));
		} catch (NoSuchMethodException e) {
			lMethod = (this.getClass().getMethod("doInstantEffect",cArg));
		}
		lMethod.invoke(permanentEffect,player);
}
	
	public void activateLeader(AlreadyOccupiedTowerDiscount permanentEffect,NetworkPlayer player){
		player.personalMainBoard.occupiedTowerCost = 0;
	}
	
	public void activateLeader(CardCoinDiscount permanentEffect,NetworkPlayer player){
	gameHandler = new CardCoinDiscountDecorator(gameHandler,permanentEffect.coinQty,player);
	}
	
	public void activateLeader(CopyLeaderAbility permanentEffect,NetworkPlayer player) throws CardNotFoundException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException{
		if(player.copiedLeaderCard == null){
			boolean flag = true;
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (Integer i : player.personalMainBoard.getPlayedLeaderCard()){
				for (Integer j : player.getPlayerPlayedLeaderCards()){
					if(i == j)
						flag = false;
					}
			if(flag==true)
				list.add(i);
			flag=true;
			}
			player.setMessage("What Leader do you want to copy?");
			ArrayList<String> nameList = new ArrayList<String>();
			for(Integer i : list){
				player.setMessage(MainBoard.leaderMap.get(i).cardName);
				nameList.add(MainBoard.leaderMap.get(i).cardName);
				}
			String response = player.sendMessage();
			flag = false;
			Integer cardNumber = 0;
			for (String name : nameList)
				if(name.equals(response)){
					cardNumber = gameHandler.cardNameToInteger(response, player.personalMainBoard.getCardNamesOnTheTowers(), player.personalMainBoard.getCardsOnTheTowers());
					flag = true;}
			if(flag==false){
				player.setMessage("You must choose a Leader Card already played by one of your opponents");
				activateLeader(permanentEffect,player);
				return;
			}
			//add the card to the player copiedLeaderCard attribute to prevent that the player copy more than one effect violating the rule of this effect
			player.copiedLeaderCard = cardNumber;
			Leader leader = MainBoard.leaderMap.get(cardNumber);
			activateLeader(leader.effect,player);
		}
		else{
			Leader leader = MainBoard.leaderMap.get(player.copiedLeaderCard);
			activateLeader(leader.effect,player);
		}
	}
	
	public void activateLeader(DoubleResourcesFromDevelopment permanentEffect,NetworkPlayer player) {
		playerDoubleResourcesFromDevelopment = player;
	}
	
	public void activateLeader(NoMilitaryRequirementsForTerritory permanentEffect,NetworkPlayer player) {
		for(int i=0;i<4;i++)
			player.personalMainBoard.militaryForTerritory[i]=0;
	}
	
	public void activateLeader(PlaceFamilyMemberOnOccupiedSpace permanentEffect,NetworkPlayer player) throws InvalidActionTypeException {
		//empty the market
		for (int i=0;i<4;i++)
			player.personalMainBoard.familyMembersLocation.setFamilyMemberOnTheMarket(new FamilyMember(),i);
		//empty the production and the harvest area
		player.personalMainBoard.familyMembersLocation.changeFamilyMemberOnProductionOrHarvest(new ArrayList<FamilyMember>(), "Harvest");
		player.personalMainBoard.familyMembersLocation.changeFamilyMemberOnProductionOrHarvest(new ArrayList<FamilyMember>(), "Production");
	}
	
	

	
	
	
	//TODO getInfo methods
	//TODO deactivateLeader
	
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
