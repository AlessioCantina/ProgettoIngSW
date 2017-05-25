package it.polimi.LM39.controller;

import java.io.File;
import java.util.*;

import it.polimi.LM39.model.*;
import it.polimi.LM39.model.Character;

/**
 * 
 */
public class GameHandler {
	private MainBoard mainBoard;
	
	public GameHandler(MainBoard mainBoard) {
	        this.mainBoard=mainBoard;
	    }
	public Integer marketSize;
	
	public Integer harvestAndProductionSize;
    
	private Integer period;

    private Integer round;
    
    public BuildingHandler buildingHandler;
    
    public TerritoryHandler territoryHandler;

    public VentureHandler ventureHandler;

    public CharacterHandler characterHandler;

    public LeaderHandler leaderHandler;

    public ExcommunicationHandler excommunicationHandler;

    public PlayerBoardHandler playerBoardHandler;

    public CouncilHandler councilHandler;
    
    public CardHandler cardHandler;


    public void rollTheDices() {
    	Integer[] diceValues = new Integer[3];
    	for(int i=0;i<3;i++){
    	Random rand = new Random();
    	diceValues[i] = new Integer(rand.nextInt(6) + 1);
    	// There is a + 1 because rand.nextInt(6) generates number from 0 to 5 but we need from 1 to 6
    	}
    	mainBoard.setDiceValues(diceValues);    
    }

    public boolean getCard(String cardName,Player player) {
    	    	FamilyMembersLocation familyMembersLocation;
    	    	familyMembersLocation = mainBoard.getFamilyMembersLocation();
    	    	FamilyMember[][] familyMembersOnTheTowers = familyMembersLocation.getFamilyMembersOnTheTowers();
    	    	int i,j;
    	    	for(i=0,j=0;!familyMembersOnTheTowers[i][j].equals(cardName) && i<4;i++){
    	    		for(j=0;!familyMembersOnTheTowers[i][j].equals(cardName) && j<4;j++);}
    	    	if (!familyMembersOnTheTowers[i][j].equals(cardName)){
    	    		System.out.println("This card is not on the Game Board!");
    	    		return false;}
    	    	else{
    	    		switch(j){
    		    		case 0: Territory territory=mainBoard.territoryMap.get(cardName);
    		    				getTerritoryCard(territory,player);
    		    			break;
    		    		case 1: Character character=mainBoard.characterMap.get(cardName);
    		    				getCharacterCard(character);
    		    			break;
    		    		case 2: Building building=mainBoard.buildingMap.get(cardName);
    		    				getBuildingCard(building);
    		    			break;
    		    		case 3: Venture venture=mainBoard.ventureMap.get(cardName);
    		    				getVentureCard(venture);
    		    			break;
    		    		default: System.out.println("This tower doesn't exist!");
    		    			break;
    	    		}
    	    		return true;
    	    	}
        
    }
    
    
    public void getTerritoryCard(Territory territory,Player player){
    	//instantResources
    	territoryHandler.doInstantEffect(territory.instantBonuses,player);
    	
    	String[] possedTerritories = player.getPlayerBoard().getPossessions("Territory");
    	int i;
    	for (i=0;i<6 && possedTerritories[i]!=null;i++);
    	if (i<6 && possedTerritories[i]==null)
    		possedTerritories[i]=territory.cardName;
    }

    public void getCharacterCard(Character character){
    	
    	
    }

    public void getBuildingCard(Building building){
	
	
    }
    
    public void getVentureCard(Venture venture){
    	
    	
    }
    
    public Integer familyMemberColortoDiceValue(String familyMemberColor){
    	//The order followed is the one on the Game Board for the dices positions
    	Integer value = new Integer(-1);
    	Integer[] diceValues = mainBoard.getDiceValues();
    	switch(familyMemberColor){
	    	case "black": value = diceValues[0];
	    		break;
	    	case "white": value = diceValues[1];
	    		break;
	    	case "orange": value = diceValues[2];
	    		break;
	    	case "uncolored": value = diceValues[3];
	    		break;
	    	default: System.out.println("Invalid familyMemberColor");
	    		break;
    	}
    	return value;
    }
    
    public void addFamilyMemberToTheTower(FamilyMember familyMember , String cardName, Player player) {
        int i,j;
        boolean coloredFamilyMemberOnTheTower = false;
        boolean uncoloredFamilyMemberOnTheTower = false;
        String[][] cardsOnTheTowers = mainBoard.getCardsOnTheTowers();
        Integer[] diceValues = player.getPersonalMainBoard().getDiceValues(); // we use the player Personal MainBaord
        FamilyMember[][] familyMembersOnTheTowers = player.getPersonalMainBoard().getFamilyMembersLocation().getFamilyMembersOnTheTowers(); // we use the player Personal MainBaord
    	
        for(i=0, j=0;!cardsOnTheTowers[i][j].equals(cardName) && i<4;i++)
        	for(j=0;!cardsOnTheTowers[i][j].equals(cardName) && j<4;j++);
    		//search the coordinates of the card in the board
        int k=j;
        int p=i;
        //to store i and j as the coordinates of the position interested
        if(familyMembersOnTheTowers[i][k] == null && diceValues[familyMemberColortoDiceValue(familyMember.color)]>=(((i+1)*2)-1)){
        	//if the place is free and the family member has an high enough value, ((i+1)*2)-1 is to convert the value i of the matrix to the value of the floor in dice
        	for(p=0;p<4;p++){
        		if((familyMembersOnTheTowers[p][k].playerColor).equals(familyMember.playerColor)){
        			//if there is one of my family members on the tower
        			if ((familyMembersOnTheTowers[p][k].color).equals("uncolored"))
        				//if this family member is uncolored
        				uncoloredFamilyMemberOnTheTower=true;
        			else
        				//if this family member is colored
        				coloredFamilyMemberOnTheTower=true;
        		}
        	}
        	if ((uncoloredFamilyMemberOnTheTower==true && coloredFamilyMemberOnTheTower==false) || (coloredFamilyMemberOnTheTower==true && (familyMember.color).equals("uncolored"))){
        	//if there is an uncolored family member on the tower or there is a colored one but the player uses an uncolored family member
        		if(getCard(cardName,player) && player.getResources().getCoins()>=3){
        			(familyMembersOnTheTowers[i][k].playerColor)=(familyMember.playerColor);
	        		(familyMembersOnTheTowers[i][k].color)=(familyMember.color);
	        		player.getResources().setCoins(-3);
	        		setTowerBonus(mainBoard.towerBonuses[j][i],player);
	        		}
        		else
        			System.out.println("Non hai le risorse necessarie!");
	        	}
        	if (uncoloredFamilyMemberOnTheTower==false && coloredFamilyMemberOnTheTower==false){
        		//if there is none of my family members
        		for(p=0;p<4 && familyMembersOnTheTowers[p][k]==null;p++);
        		if(p==5 && getCard(cardName,player)){
        			//if the tower is free
        			(familyMembersOnTheTowers[i][k].playerColor)=(familyMember.playerColor);
        			(familyMembersOnTheTowers[i][k].color)=(familyMember.color);
	        		setTowerBonus(mainBoard.towerBonuses[j][i],player);
        		}
        		else{
        			//if the tower is occupied
        			if(getCard(cardName,player) && player.getResources().getCoins()>=3){
        				(familyMembersOnTheTowers[i][k].playerColor)=(familyMember.playerColor);
            			(familyMembersOnTheTowers[i][k].color)=(familyMember.color);
    	        		player.getResources().setCoins(-3);
    	        		setTowerBonus(mainBoard.towerBonuses[j][i],player);
        			}
        			else
            			System.out.println("Non hai le risorse necessarie!");
        		}
        	}
        }
        else	
        	System.out.println("La posizione scelta � occupata o il familiare ha un valore troppo basso!");	
        
    }
    
    public void setTowerBonus(ActionBonus towerBonus,Player player){
    	addResources(towerBonus.getResources(),player);
    	addPoints(towerBonus.getPoints(),player);
    	
    }
    
    public void addResources (PlayerResources resources, Player player){
    	PlayerResources playerResources = player.getResources();
    	playerResources.setCoins(resources.getCoins());
    	playerResources.setWoods(resources.getWoods());
    	playerResources.setStones(resources.getStones());
    	playerResources.setServants(resources.getServants());
    	councilHandler.getCouncil(resources.getCouncil(),player);
    	player.setResources(playerResources);
    }
    
    public void addPoints (PlayerPoints points, Player player){
    	PlayerPoints playerPoints = player.getPoints();
    	playerPoints.setFaith(points.getFaith());
    	playerPoints.setFaith(points.getVictory());
    	playerPoints.setFinalVictory(points.getFinalVictory());
    	playerPoints.setMilitary(points.getMilitary());
    	player.setPoints(playerPoints);
    }

    public void addFamilyMemberToTheMarket(FamilyMember familyMember, Integer position, Player player) {
    	FamilyMember[] familyMembersAtTheMarket = player.getPersonalMainBoard().getFamilyMembersLocation().getFamilyMembersOnTheMarket(); // we use the player Personal MainBaord
        if(familyMembersAtTheMarket[position] == null && position<=marketSize){
        	(familyMembersAtTheMarket[position].color) = (familyMember.color);
        	(familyMembersAtTheMarket[position].playerColor) = (familyMember.playerColor);
        	switch(position){
	        	case 1: player.getResources().setCoins(5);
	        		break;
	        	case 2: player.getResources().setServants(5);
	        		break;
	        	case 3: player.getResources().setCoins(2);
	    				player.getPoints().setMilitary(3);
	    			break;
	        	case 4: councilHandler.getCouncil(1,player);
	        		break;
	        	default: System.out.println("Posizione invalida! la posizione deve essere compresa fra 1 e 4");
	        		break;
        	}
        }
        else {
        	System.out.println("Posto occupato o non utilizzabile se in 2 player!");
        }
    }
    
    public void addFamilyMemberToProductionOrHarvest(FamilyMember familyMember, FamilyMember[] familyMembersAtProductionOrHarvest, String actionType,Player player) {
    	int i;
    	boolean doAction=false;
    	//to know if the action Harvest or Production can be done
    	int penalty=3;
    	//penalty in case of first slot already occupied
    		for(i=0;!(familyMembersAtProductionOrHarvest[i].playerColor).equals(familyMember.playerColor) && i<harvestAndProductionSize;i++);
    		if(i==harvestAndProductionSize){
    			//if there isn't any of my family Members
    			for(i=0;familyMembersAtProductionOrHarvest[i]!=null && i<harvestAndProductionSize;i++);
    			//move i to the first free slot
    			if(i<harvestAndProductionSize){
    				//if there is place in the Production Area
    				if(i==0){
    					//if there is no one
    					familyMembersAtProductionOrHarvest[i].color=familyMember.color;
    		    		familyMembersAtProductionOrHarvest[i].playerColor=familyMember.playerColor;
    		    		doAction=true;
    		    		penalty=0;
    		    	}
    				else{
    					//if there is someone but not any of my Family Members
    					familyMembersAtProductionOrHarvest[i].color=familyMember.color;
    		    		familyMembersAtProductionOrHarvest[i].playerColor=familyMember.playerColor;
        		    		doAction=true;
    					}
    				}
    			else {
    				//this happens only in matches of 2 players
    				System.out.println("Area di produzione piena!");
    			}
    		}
    		
    		else{
    			//if there is already one of my family members
    			int j=0;
    			for(i=0;i<harvestAndProductionSize;i++){
    				if((familyMembersAtProductionOrHarvest[i].playerColor).equals(familyMember.playerColor)){
    					if((familyMembersAtProductionOrHarvest[i].color).equals("uncolored"))
    						j++;
    					else
    						j--;
    					}
    			}
    			if(j==0)
    				//if there are already two of my family members
    				System.out.println("Non puoi mettere un altro familiare!");
    			for(i=0;familyMembersAtProductionOrHarvest[i]!=null && i<harvestAndProductionSize;i++);
    			//move i to the first free slot
    			if(j==-1){
    				//if there is a colored family member
    				if(familyMember.color=="uncolored"){
    					familyMembersAtProductionOrHarvest[i].color=familyMember.color;
    		    		familyMembersAtProductionOrHarvest[i].playerColor=familyMember.playerColor;
		    			doAction=true;}
    				else
    					System.out.println("Puoi piazzare solo un familiare uncolored!");
    				}
    			else {
    				//if there is an uncolored family member
    				familyMembersAtProductionOrHarvest[i].color=familyMember.color;
		    		familyMembersAtProductionOrHarvest[i].playerColor=familyMember.playerColor;
	    			doAction=true;
    			}	
    		}
    		if (doAction==true){
    			if (actionType=="Production")
	    			playerBoardHandler.activateHarvest(player.getPersonalMainBoard().getDiceValues()[familyMemberColortoDiceValue(familyMember.color)]-penalty,player); // we use the player Personal MainBaord
	    		if(actionType=="Harvest")
	    			playerBoardHandler.activateHarvest(player.getPersonalMainBoard().getDiceValues()[familyMemberColortoDiceValue(familyMember.color)]-penalty,player); // we use the player Personal MainBaord
	    		else
	    			System.out.println("Azione non valida! deve essere Production o Harvest");
    		}
    			
    		}

    public void loadCards(MainBoard mainBoard) {
        // TODO implement here
    }

    public void iniazializeTheGame() {
        // TODO implement here
        
    }

    public Integer calculateFinalPoints(Player player,MainBoard mainBoard) {
        // TODO implement here
        return null; //prevent error
    }

}