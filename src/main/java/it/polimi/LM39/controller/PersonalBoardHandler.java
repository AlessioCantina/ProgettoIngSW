package it.polimi.LM39.controller;


import it.polimi.LM39.server.NetworkPlayer;
import it.polimi.LM39.server.SocketPlayer;import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.google.gson.Gson;

import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.model.Player;
import it.polimi.LM39.model.instanteffect.InstantEffect;

public class PersonalBoardHandler {
	private GameHandler gameHandler;
    
	public void setGameHandler(GameHandler gameHandler){
    	this.gameHandler = gameHandler;
    }
	
    public void activateHarvest(Integer actionValue, NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughResourcesException, NotEnoughPointsException {
    	gameHandler.setActionBonus(player.personalBoard.personalBonusTile.harvestBonus,player);
    	ArrayList <Integer> territories = player.personalBoard.getPossessions("Territory");
        CardHandler cardHandler = new CardHandler(gameHandler,gameHandler.decoratedMethods);
        for (int i=0;i<territories.size();i++)
        	if(actionValue >= gameHandler.mainBoard.territoryMap.get(territories.get(i)).activationCost)
        		cardHandler.doInstantEffect((gameHandler.mainBoard.territoryMap.get(territories.get(i)).activationReward),player);
    }

    public void activateProduction(Integer actionValue, NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException , IOException, NotEnoughResourcesException, NotEnoughPointsException {
    	gameHandler.setActionBonus(player.personalBoard.personalBonusTile.productionBonus,player);
    	ArrayList <Integer> buildings = player.personalBoard.getPossessions("Building");
        CardHandler cardHandler = new CardHandler(gameHandler,gameHandler.decoratedMethods);
        /*
        CardResources costResources = initializeResources();
		CardResources bonusResources = initializeResources();
		CardPoints costPoints = initializePoints();
		CardPoints bonusPoints = initializePoints();
		*/
        CardResources costResources = new CardResources();
        CardResources bonusResources = new CardResources();
		CardPoints costPoints = new CardPoints();
		CardPoints bonusPoints = new CardPoints();
        for (int i=0;i<buildings.size();i++)
        	 if(actionValue >= gameHandler.mainBoard.buildingMap.get(buildings.get(i)).activationCost){
        		 player.setMessage("Do you want to activate " + gameHandler.mainBoard.buildingMap.get(buildings.get(i)).cardName + " ? yes or no");
        		 cardHandler.getInfo(gameHandler.mainBoard.buildingMap.get(buildings.get(i)).activationEffect,player);
        		 String response = player.sendMessage();
        		 response = GameHandler.checkResponse(response, player);
  
        		 if(("yes").equals(response))
        			 checkPlayer(player,(gameHandler.mainBoard.buildingMap.get(buildings.get(i)).activationEffect),costResources,costPoints,bonusResources,bonusPoints);
        		 
        	 }
        		 try{
        			 gameHandler.subCardResources(costResources, player);
        		 }
        		 catch (NotEnoughResourcesException e){
        			 player.setMessage("You don't have enough resources");
        			 activateProduction(actionValue,player);
        			 return;
        		 }
        		 
        		 try{
        			 gameHandler.subCardPoints(costPoints, player);
        		 }
        		 catch (NotEnoughPointsException e){
        			 //give back the reources subtracted from the player if the action fails
        			 gameHandler.decoratedMethods.addCardResources(costResources, player);
        			 player.setMessage("You don't have enough points");
        			 activateProduction(actionValue,player);
        			 return;
        		 }
        		 
        		 //give the bonus received from the transformation to the player
        		 gameHandler.decoratedMethods.addCardResources(bonusResources, player);
        		 gameHandler.decoratedMethods.addCardPoints(bonusPoints, player);
    }
    
    public void checkPlayer(NetworkPlayer player,InstantEffect activationEffect,CardResources costResources, CardPoints costPoints, CardResources bonusResources, CardPoints bonusPoints) throws IOException, NotEnoughResourcesException, NotEnoughPointsException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
    	NetworkPlayer fakePlayer;
    	Gson gson =new Gson();
    	fakePlayer = gson.fromJson(gson.toJson(player),player.getClass());
 
    	fakePlayer.decoratorHandler = player.decoratorHandler;
    	fakePlayer.resources.setCoins(20);
    	fakePlayer.resources.setServants(20);
    	fakePlayer.resources.setStones(20);
    	fakePlayer.resources.setWoods(20);
    	fakePlayer.resources.setCouncil(0);
    	fakePlayer.points.setFaith(20);
    	fakePlayer.points.setFinalVictory(20);
    	fakePlayer.points.setMilitary(20);
    	fakePlayer.points.setVictory(20);
    	
    	CardHandler cardHandler = new CardHandler(gameHandler,gameHandler.decoratedMethods);
    	cardHandler.doInstantEffect(activationEffect,fakePlayer);
    	
    	if(fakePlayer.resources.getCoins()>20)
    		bonusResources.coins = fakePlayer.resources.getCoins() - 20;
    	else if (fakePlayer.resources.getCoins()<20)
    		costResources.coins = 20 - fakePlayer.resources.getCoins();
    	
    	if(fakePlayer.resources.getServants()>20)
    		bonusResources.servants = fakePlayer.resources.getServants() - 20;
    	else if (fakePlayer.resources.getServants()<20)
    		costResources.servants = 20 - fakePlayer.resources.getServants();
    	
    	if(fakePlayer.resources.getStones()>20)
    		bonusResources.stones = fakePlayer.resources.getStones() - 20;
    	else if (fakePlayer.resources.getStones()<20)
    		costResources.stones = 20 - fakePlayer.resources.getStones();
    	
    	if(fakePlayer.resources.getWoods()>20)
    		bonusResources.woods = fakePlayer.resources.getWoods() - 20;
    	else if (fakePlayer.resources.getWoods()<20)
    		costResources.woods = 20 - fakePlayer.resources.getWoods();
    	
    	if(fakePlayer.resources.getCouncil()>0)
    		bonusResources.council = fakePlayer.resources.getCouncil();
    	
    	
    	if(fakePlayer.points.getFaith()>20)
    		bonusPoints.faith = fakePlayer.points.getFaith() - 20;
    	else if (fakePlayer.points.getFaith()<20)
    		costPoints.faith = 20 - fakePlayer.points.getFaith();
    	
    	if(fakePlayer.points.getMilitary()>20)
    		bonusPoints.military = fakePlayer.points.getMilitary() - 20;
    	else if (fakePlayer.points.getMilitary()<20)
    		costPoints.military = 20 - fakePlayer.points.getMilitary();
    	
    	if(fakePlayer.points.getVictory()>20)
    		bonusPoints.victory = fakePlayer.points.getVictory() - 20;
    	else if (fakePlayer.points.getVictory()<20)
    		costPoints.victory = 20 - fakePlayer.points.getVictory();
    }
    
    
    //probably useless methods
    /*
    public CardResources initializeResources (){
    	CardResources resources = new CardResources();
    	resources.coins = 0;
    	resources.woods = 0;
    	resources.stones = 0;
    	resources.servants = 0;
    	resources.council = 0;
    	return resources;
    }
    
    public CardPoints initializePoints (){
    	CardPoints points = new CardPoints();
    	points.faith = 0;
    	points.military = 0;
    	points.victory = 0;
    	return points;
    }
    */

}
