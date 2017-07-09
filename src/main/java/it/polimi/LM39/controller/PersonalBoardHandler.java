package it.polimi.LM39.controller;


import it.polimi.LM39.server.NetworkPlayer;
import java.util.ArrayList;
import com.google.gson.Gson;
import it.polimi.LM39.exception.InvalidActionTypeException;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.model.instanteffect.InstantEffect;

/**
 * this class contains the methods to handle the player PersonalBoard
 */
public class PersonalBoardHandler {
	private GameHandler gameHandler;
    
	/**
	 * setter for rgameHandler
	 * @param gameHandler
	 */
	public void setGameHandler(GameHandler gameHandler){
    	this.gameHandler = gameHandler;
    }
	
	/**
	 * to activate the harvest
	 * @param actionValue
	 * @param player
	 * @param familyMember
	 * @return
	 * @throws ReflectiveOperationException
	 */
    public boolean activateHarvest(Integer actionValue, NetworkPlayer player,FamilyMember familyMember) throws ReflectiveOperationException , NotEnoughResourcesException, NotEnoughPointsException, InvalidActionTypeException {
    	if(actionValue > 0){
    	gameHandler.setActionBonus(player.personalBoard.personalBonusTile.harvestBonus,player);
    	ArrayList <Integer> territories = player.personalBoard.getPossessions("Territory");
        CardHandler cardHandler = new CardHandler(gameHandler,gameHandler.decoratedMethods);
        for (int i=0;i<territories.size();i++)
        	if(actionValue >= gameHandler.mainBoard.territoryMap.get(territories.get(i)).activationCost)
        		cardHandler.doInstantEffect(gameHandler.mainBoard.territoryMap.get(territories.get(i)).activationReward,player);
        return true;
    	}
    	else{
    		if(gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Harvest").contains(familyMember))
    			gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Harvest").remove(familyMember);
    		player.setMessage("Your family member has not an high enough value");
    		return false;
    	}
    }

    /**
     * to activate the Production
     * @param actionValue
     * @param player
     * @param familyMember
     * @return
     * @throws ReflectiveOperationException
     */
    public boolean activateProduction(Integer actionValue, NetworkPlayer player,FamilyMember familyMember) throws ReflectiveOperationException,  NotEnoughResourcesException, NotEnoughPointsException, InvalidActionTypeException {
    	if(actionValue > 0){
    	gameHandler.setActionBonus(player.personalBoard.personalBonusTile.productionBonus,player);
    	ArrayList <Integer> buildings = player.personalBoard.getPossessions("Building");
        CardHandler cardHandler = new CardHandler(gameHandler,gameHandler.decoratedMethods);
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
        			 checkPlayer(player,gameHandler.mainBoard.buildingMap.get(buildings.get(i)).activationEffect,costResources,costPoints,bonusResources,bonusPoints);
        		 
        	 }
        		 try{
        			 gameHandler.subCardResources(costResources, player);
        		 }
        		 catch (NotEnoughResourcesException e){
        			 player.setMessage("You don't have enough resources");
        			 activateProduction(actionValue,player,familyMember);
        			 //the player at least gets the bonus from the bonus tile so return true
        			 return true;
        		 }
        		 
        		 try{
        			 gameHandler.subCardPoints(costPoints, player);
        		 }
        		 catch (NotEnoughPointsException e){
        			 //give back the resources subtracted from the player if the action fails
        			 gameHandler.decoratedMethods.addCardResources(costResources, player);
        			 player.setMessage("You don't have enough points");
        			 activateProduction(actionValue,player,familyMember);
        			//the player at least gets the bonus from the bonus tile so return true
        			 return true;
        		 }
        		 
        		 //give the bonus received from the transformation to the player
        		 gameHandler.decoratedMethods.addCardResources(bonusResources, player);
        		 gameHandler.decoratedMethods.addCardPoints(bonusPoints, player);
        return true;	 
    	}
    	else{
    		if(gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Production").contains(familyMember))
    			gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Production").remove(familyMember);
    		player.setMessage("Your family member has not an high enough value");
    		return false;
    	}
    		
    }
    
    /**
     * to check if a player has the resources and points to do the transformations he chose
     * @param player
     * @param activationEffect
     * @param costResources
     * @param costPoints
     * @param bonusResources
     * @param bonusPoints
     * @throws NotEnoughResourcesException
     * @throws NotEnoughPointsException
     * @throws ReflectiveOperationException
     */
    private void checkPlayer(NetworkPlayer player,InstantEffect activationEffect,CardResources costResources, CardPoints costPoints, CardResources bonusResources, CardPoints bonusPoints) throws  NotEnoughResourcesException, NotEnoughPointsException,ReflectiveOperationException {
    	NetworkPlayer fakePlayer;
    	Gson gson = new Gson();
    	
    	//make a copy of the player
    	fakePlayer = gson.fromJson(gson.toJson(player),player.getClass());
    	
    	//set all resources and values of the fakePlayer to 20(a value high enough to complete any transformation)
    	fakePlayer.resources.setCoins(-fakePlayer.resources.getCoins());
    	fakePlayer.resources.setCoins(20);
    	fakePlayer.resources.setServants(-fakePlayer.resources.getServants());
    	fakePlayer.resources.setServants(20);
    	fakePlayer.resources.setStones(-fakePlayer.resources.getStones());
    	fakePlayer.resources.setStones(20);
    	fakePlayer.resources.setWoods(-fakePlayer.resources.getWoods());
    	fakePlayer.resources.setWoods(20);
    	fakePlayer.resources.setCouncil(0);
    	fakePlayer.points.setFaith(-fakePlayer.points.getFaith());
    	fakePlayer.points.setFaith(20);
    	fakePlayer.points.setFinalVictory(-fakePlayer.points.getFinalVictory());
    	fakePlayer.points.setFinalVictory(20);
    	fakePlayer.points.setMilitary(-fakePlayer.points.getMilitary());
    	fakePlayer.points.setMilitary(20);
    	fakePlayer.points.setVictory(-fakePlayer.points.getVictory());
    	fakePlayer.points.setVictory(20);
    	
    	CardHandler cardHandler = new CardHandler(gameHandler,gameHandler.decoratedMethods);
    	cardHandler.doInstantEffect(activationEffect,player,fakePlayer);
    	
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

}
