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

public class CardHandler {
	
	
	public void doInstantEffect(Effect instantEffect,Player player){	
		{									
			try{
				Class[] cArg = new Class[2];
		        cArg[0] = instantEffect.getClass();
		        cArg[1] = player.getClass();
				Method lMethod = (this.getClass().getMethod("doInstantEffect",cArg));
				lMethod.invoke(this,instantEffect,player);
			}catch(Exception e){
				e.printStackTrace();}
			}
	}
	public void doInstantEffect(CoinForCard instantEffect,Player player){
		int coinQty=(player.personalBoard.getPossessions(instantEffect.cardType).size())*instantEffect.coinQty;
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
