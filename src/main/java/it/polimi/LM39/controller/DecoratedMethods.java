package it.polimi.LM39.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import it.polimi.LM39.exception.InvalidActionTypeException;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.Building;
import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.Character;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.model.PlayerPoints;
import it.polimi.LM39.model.PlayerResources;
import it.polimi.LM39.model.Venture;
import it.polimi.LM39.server.NetworkPlayer;

public class DecoratedMethods {
	//TODO setter di gameHandler
	GameHandler gameHandler;
	

	public void setGameHandler(GameHandler gameHandler){
		this.gameHandler = gameHandler;
	}
	
	 public void resourcesForBuilding(NetworkPlayer player ,Building building) throws NotEnoughResourcesException{
		 	gameHandler.subCardResources(building.costResources,player);
	    }
	 
	 public void coinsForCharacter(NetworkPlayer player ,Character character) throws NotEnoughResourcesException{
	    	player.resources.setCoins(-character.costCoins);
	    }
	 
	 public void resourcesForVenture(NetworkPlayer player ,Venture venture) throws NotEnoughResourcesException{
	    	gameHandler.subCardResources(venture.costResources,player);
	    }
	 
	 public void addCardResources (CardResources resources, NetworkPlayer player) throws NotEnoughResourcesException, NotEnoughPointsException{
	    	CouncilHandler councilHandler = new CouncilHandler();
		 	PlayerResources playerResources = player.resources;
	    	playerResources.setCoins(resources.coins);
	    	playerResources.setWoods(resources.woods);
	    	playerResources.setStones(resources.stones);
	    	playerResources.setServants(resources.servants);
	    	councilHandler.getCouncil(resources.council,player,gameHandler,new ArrayList<Integer>());
	    	player.resources=playerResources;
	    }
	 
	 public boolean addFamilyMemberToTheMarket(FamilyMember familyMember, Integer position, NetworkPlayer player) throws IOException, NotEnoughResourcesException, NotEnoughPointsException {
	    	FamilyMember[] familyMembersAtTheMarket = player.personalMainBoard.familyMembersLocation.getFamilyMembersOnTheMarket(); // we use the player Personal MainBaord
	        if(("").equals(familyMembersAtTheMarket[position-1].color) && (position-1)<gameHandler.mainBoard.marketSize){ 
	        	if(gameHandler.familyMemberValue(familyMember,player)>=1){
	        	if(position==1 || position==2 || position==3 || position==4)
	        		gameHandler.setActionBonus(player.personalMainBoard.marketBonuses[position-1],player);
	        	else{
	        		player.setMessage("Invalid position! the position must be between 1 and 4");
		        	return false;
		        	}
	        	(gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnTheMarket()[position-1].color) = (familyMember.color);
	        	(gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnTheMarket()[position-1].playerColor) = (familyMember.playerColor);
	        	}
	        	else {
	            	player.setMessage("Your Family Member must have a value of at least 1");
	            	return false;
	            }
	        }
	        else {
	        	player.setMessage("This place is occupied or not usable if two player game");
	        	return false;
	        }
	        return true;
	    }
	 
	 public void addCardPoints (CardPoints points, NetworkPlayer player) throws NotEnoughPointsException{
	    	PlayerPoints playerPoints = player.points;
	    	playerPoints.setFaith(points.faith);
	    	playerPoints.setVictory(points.victory);
	    	playerPoints.setMilitary(points.military);
	    	player.points=playerPoints;
	    }
	 
	 public Integer addServants(NetworkPlayer player) throws IOException, NotEnoughResourcesException{
	    	player.setMessage("Do you want to add servants? yes or no");
	    	String response = player.sendMessage();
	    	response = GameHandler.checkResponse(response, player);
	    	if(("yes").equals(response)){
	    		player.setMessage("How many?");
	    		Integer qty = Integer.parseInt(player.sendMessage());
	    		player.resources.setServants(-qty);
	    		return qty;
	    	}
	    	else
	    		return 0;
	    }
	 
	 public boolean activateHarvest(Integer actionValue, NetworkPlayer player,PersonalBoardHandler personalBoardHandler,FamilyMember familyMember) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughResourcesException, NotEnoughPointsException, InvalidActionTypeException{
		 return personalBoardHandler.activateHarvest(actionValue,player,familyMember);
	 }
	
	 public boolean activateProduction(Integer actionValue, NetworkPlayer player,PersonalBoardHandler personalBoardHandler,FamilyMember familyMember) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NotEnoughResourcesException, NotEnoughPointsException, InvalidActionTypeException{
		 return personalBoardHandler.activateProduction(actionValue, player,familyMember);
	 }
	 
	 
	 
}
