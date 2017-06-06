package it.polimi.LM39.controller;


import it.polimi.LM39.server.NetworkPlayer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.model.PersonalBoard;


public class PersonalBoardHandler {
	private GameHandler gameHandler;
    
    public void activateHarvest(Integer actionValue, NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughResourcesException, NotEnoughPointsException {
    	gameHandler.setActionBonus(player.personalMainBoard.harvestBonus,player);
    	ArrayList <Integer> territories = player.personalBoard.getPossessions("Territory");
        CardHandler cardHandler = new CardHandler(gameHandler);
        for (int i=0;i<territories.size();i++)
        	if(actionValue >= MainBoard.territoryMap.get(territories.get(i)).activationCost)
        		cardHandler.doInstantEffect((MainBoard.territoryMap.get(territories.get(i)).activationReward),player);
    }

    public void activateProduction(Integer actionValue, NetworkPlayer player) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughResourcesException, NotEnoughPointsException {
    	gameHandler.setActionBonus(player.personalMainBoard.productionBonus,player);
    	ArrayList <Integer> buildings = player.personalBoard.getPossessions("Building");
         CardHandler cardHandler = new CardHandler(gameHandler);
         for (int i=0;i<buildings.size();i++)
        	 if(actionValue >= MainBoard.buildingMap.get(buildings.get(i)).activationCost)
        		 //TODO ask to the player if he wants to perform sendig him the effect getInfo
        		 cardHandler.doInstantEffect((MainBoard.buildingMap.get(buildings.get(i)).activationEffect),player);
     }
    public void setGameHandler(GameHandler gameHandler){
    	this.gameHandler = gameHandler;
    }
}
