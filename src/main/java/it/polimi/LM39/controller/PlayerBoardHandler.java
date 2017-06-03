package it.polimi.LM39.controller;


import it.polimi.LM39.server.NetworkPlayer;

import java.util.ArrayList;

import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.model.PersonalBoard;


public class PlayerBoardHandler implements PlayerBoardHandlerInterface{
	private GameHandler gameHandler;
    
    public PlayerBoardHandler(GameHandler gameHandler){
    	this.gameHandler = gameHandler;
    }


    public void activateHarvest(Integer actionValue, NetworkPlayer player) {
        ArrayList <Integer> territories = player.personalBoard.getPossessions("Territory");
        CardHandler cardHandler = new CardHandler(gameHandler);
        for (int i=0;i<territories.size();i++)
        	cardHandler.doInstantEffect((MainBoard.territoryMap.get(territories.get(i)).activationReward),player);
    }

    public void activateProduction(Integer actionValue, NetworkPlayer player) {
    	 ArrayList <Integer> buildings = player.personalBoard.getPossessions("Building");
         CardHandler cardHandler = new CardHandler(gameHandler);
         for (int i=0;i<buildings.size();i++)
         	cardHandler.doInstantEffect((MainBoard.buildingMap.get(buildings.get(i)).activationEffect),player);
     }
    }
