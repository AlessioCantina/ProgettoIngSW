package it.polimi.LM39.controller;

import java.lang.reflect.Method;

import it.polimi.LM39.exception.NotEnoughPoints;
import it.polimi.LM39.exception.NotEnoughResources;
import it.polimi.LM39.model.Effect;
import it.polimi.LM39.model.NoEffect;
import it.polimi.LM39.model.Player;
import it.polimi.LM39.model.PlayerResources;
import it.polimi.LM39.model.characterpermanenteffect.*;
import it.polimi.LM39.model.instanteffect.*;
import it.polimi.LM39.model.leaderpermanenteffect.*;
import it.polimi.LM39.model.leaderobject.*;

public class CardHandler {
	
	
	public void doInstantEffect(Effect instantEffect,Player player)	
		{									
			try{
				Class[] cArg = new Class[2];
		        cArg[0] = instantEffect.getClass();
		        cArg[1] = player.getClass();
				Method lMethod = (this.getClass().getMethod("doInstantEffect",cArg));
				lMethod.invoke(instantEffect,player);
			}catch(Exception e){
				e.printStackTrace();}
			}
	public void doInstantEffect(CoinForCard instantEffect,Player player){
		//calculate the coin to receive by multiplying the possessed cards of a specific type by the coin quantity given by card
		Integer coinQty=(player.personalBoard.getPossessions(instantEffect.cardType).size())*instantEffect.coinQty;
		try {
			player.resources.setCoins(coinQty);
		} catch (NotEnoughResources e) {
			e.printStackTrace();
		}	
	}
	
	public void doInstantEffect(DoublePointsTransformation instantEffect,Player player){
		//need to ask to the player what exchange he wants to do
		//TODO
	}
	
	public void doInstantEffect(DoubleResourcesTransformation instantEffect,Player player){
		//need to ask to the player what exchange he wants to do
		//TODO
	}
	
	public void doInstantEffect(GetCard instantEffect,Player player){
		//need to ask to the player what card he wants
		//TODO
	}
	
	public void doInstantEffect(GetCardAndPoints instantEffect,Player player){
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
	
	public void doInstantEffect(GetCardAndResources instantEffect,Player player){
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
	
	public void doInstantEffect(GetDiscountedCard instantEffect,Player player){
		//TODO
		//ask the player what card he wants then check the card cost and evaluate how many bonuses to give
	}
	
	public void doInstantEffect(HarvestProductionAction instantEffect,Player player){
		PlayerBoardHandler playerBoardHandler = new PlayerBoardHandler();
		//check if the effect is for harvest o production and call the carrect method
		if(instantEffect.actionType.equals("Harvest"))
			playerBoardHandler.activateHarvest(instantEffect.actionValue, player);
		else if(instantEffect.actionType.equals("Production"))
			playerBoardHandler.activateProduction(instantEffect.actionValue, player);
	}
	
	public void doInstantEffect(HarvestProductionAndPoints instantEffect,Player player){
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
	
	public void doInstantEffect(Points instantEffect,Player player){
		GameHandler gameHandler = new GameHandler();
		try {
			gameHandler.addCardPoints(instantEffect.points, player);
		} catch (NotEnoughPoints e) {
			e.printStackTrace();
		}
	}
	
	public void doInstantEffect(PointsTransformation instantEffect,Player player){
		GameHandler gameHandler = new GameHandler();
		//check if the player has enough resources
		try {
			gameHandler.subCardResources(instantEffect.requestedForTransformation, player);
		} catch (NotEnoughResources e) {
			e.printStackTrace();
		}
		//add points to the player
		try {
			gameHandler.addCardPoints(instantEffect.points, player);
		} catch (NotEnoughPoints e) {
			e.printStackTrace();
		}
	}

	public void doInstantEffect(Resources instantEffect,Player player){
		GameHandler gameHandler = new GameHandler();
		try {
			gameHandler.addCardResources(instantEffect.resources, player);
		} catch (NotEnoughResources e) {
			e.printStackTrace();
		}
	}
	
	public void doInstantEffect(ResourcesAndPoints instantEffect,Player player){
		//making a Resources effect and calling his method
		Resources resourcesEffect = new Resources();
		resourcesEffect.resources = instantEffect.resources;
		doInstantEffect(resourcesEffect,player);
		//making a Points effect and calling his method
		Points pointsEffect = new Points();
		pointsEffect.points = instantEffect.points;
		doInstantEffect(pointsEffect,player);
	}
	
	public void doInstantEffect(ResourcesAndPointsTransformation instantEffect,Player player){
		GameHandler gameHandler = new GameHandler();
		//check if the player has enough points for the transformation
		try {
			gameHandler.subCardPoints(instantEffect.requestedForTransformation, player);
		} catch (NotEnoughPoints e) {
			e.printStackTrace();
		}
		//making a ResourcesAndPoints effect and calling his method
		ResourcesAndPoints effect = new ResourcesAndPoints();
		effect.points = instantEffect.points;
		effect.resources = instantEffect.resources;
		doInstantEffect(effect,player);
	}
	
	public void doInstantEffect(ResourcesTransformation instantEffect,Player player){
		GameHandler gameHandler = new GameHandler();
		//checking if the player has enough resources
		try {
			gameHandler.subCardResources(instantEffect.requestedForTransformation, player);
		} catch (NotEnoughResources e) {
			e.printStackTrace();
		}
		//making a Resources effect and calling his method
		Resources resourcesEffect = new Resources();
		resourcesEffect.resources = instantEffect.resources;
		doInstantEffect(resourcesEffect,player);
	}
	
	public void doInstantEffect(SetFamilyMember instantEffect,Player player){
		//the color will be chosen by the user
		//TODO
	}
	
	public void doInstantEffect(VictoryForCard instantEffect,Player player){
		//calculate the victory points to receive by multiplying the possessed cards of a specific type by the victory quantity given by card
		Integer victoryQty=(player.personalBoard.getPossessions(instantEffect.cardType).size())*instantEffect.victoryQty;
			player.points.setVictory(victoryQty);
	}
	
	public void doInstantEffect(VictoryForMilitary instantEffect,Player player){
		//calculate the victory points to receive by multiplying the possessed cards of a specific type by the victory quantity given by card
		Integer victoryQty=(player.points.getMilitary())*instantEffect.victoryQty;
		player.points.setVictory(victoryQty);
		
	}
	
	
	
	public boolean checkLeaderRequestedObject(LeaderRequestedObjects requestedObject,Player player)	
		{	
			boolean flag = false;
			try{
				Class[] cArg = new Class[2];
		        cArg[0] = requestedObject.getClass();
		        cArg[1] = player.getClass();
				Method lMethod = (this.getClass().getMethod("checkLeaderRequestedObject",cArg));
				flag = (boolean)lMethod.invoke(requestedObject,player);
			}catch(Exception e){
				e.printStackTrace();}
		return flag;
	}
	
	public boolean checkLeaderRequestedObject(RequestedCard requestedObject,Player player){
		//check if the player has enough card of a type
		if (player.personalBoard.getPossessions(requestedObject.cardType).size() >= requestedObject.cardQty)
			return true;
		return false;
	}
	
	public boolean checkLeaderRequestedObject(RequestedCardPointsResources requestedObject,Player player){
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
	
	public boolean checkLeaderRequestedObject(RequestedPoints requestedObject,Player player){
		if(player.points.getMilitary() >= requestedObject.points.military &&
				player.points.getFaith() >= requestedObject.points.faith &&
					player.points.getVictory() >= requestedObject.points.victory)
			return true;
		return false;
	}
	
	public boolean checkLeaderRequestedObject(RequestedResources requestedObject,Player player){
		if(player.resources.getWoods() >= requestedObject.resources.woods &&
				player.resources.getStones() >= requestedObject.resources.stones &&
				player.resources.getServants() >= requestedObject.resources.servants &&
				player.resources.getCoins() >= requestedObject.resources.coins)
			return true;
		return false;
	}
	
	public boolean checkLeaderRequestedObject(RequestedSameCard requestedObject,Player player){
		boolean flag=false;
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
		
	public boolean checkLeaderRequestedObject(RequestedTwoCards requestedObject,Player player){
		boolean flag1 = false;
		boolean flag2 = false;
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
