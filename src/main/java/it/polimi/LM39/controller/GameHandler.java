package it.polimi.LM39.controller;

import java.io.IOException;
import java.util.*;

import it.polimi.LM39.exception.NotEnoughPoints;
import it.polimi.LM39.exception.NotEnoughResources;
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
	public Integer marketSize = new Integer(0);
	
	public Integer harvestAndProductionSize = new Integer(0);
    
	private Integer period = 1;

    private Integer round = 1;
    
    public BuildingHandler buildingHandler = new BuildingHandler();
    
    public TerritoryHandler territoryHandler = new TerritoryHandler();

    public VentureHandler ventureHandler = new VentureHandler();

    public CharacterHandler characterHandler = new CharacterHandler();

    public LeaderHandler leaderHandler = new LeaderHandler();

    public ExcommunicationHandler excommunicationHandler = new ExcommunicationHandler();

    public PlayerBoardHandler playerBoardHandler = new PlayerBoardHandler();

    public CouncilHandler councilHandler = new CouncilHandler();
    
    public CardHandler cardHandler = new CardHandler();
    
    public GsonReader gsonReader = new GsonReader();

    public void setPeriod(Integer period){
    	this.period=period;
    }
    public void setRound(Integer round){
    	this.round=round;
    }
    
    public void rollTheDices() {
    	Integer[] diceValues = new Integer[3];
    	for(int i=0;i<3;i++){
    	Random rand = new Random();
    	diceValues[i] = new Integer(rand.nextInt(6) + 1);
    	// There is a + 1 because rand.nextInt(6) generates number from 0 to 5 but we need from 1 to 6
    	}
    	mainBoard.setDiceValues(diceValues);    
    }

    public boolean getCard(Integer cardNumber,Player player) {
    	boolean cardGotten=false;
    	FamilyMembersLocation familyMembersLocation;
    	familyMembersLocation = mainBoard.getFamilyMembersLocation();
    	FamilyMember[][] familyMembersOnTheTowers = familyMembersLocation.getFamilyMembersOnTheTowers();
    	int i,j;
    	for(i=0,j=0;!familyMembersOnTheTowers[i][j].equals(cardNumber) && i<4;i++){
    		for(j=0;!familyMembersOnTheTowers[i][j].equals(cardNumber) && j<4;j++);}
    	    	if (!familyMembersOnTheTowers[i][j].equals(cardNumber))
    	    		System.out.println("This card is not on the Game Board!");
    	    	else{
    	    		switch(j){
    		    		case 0: Territory territory=mainBoard.territoryMap.get(cardNumber);
    		    			cardGotten=getTerritoryCard(territory,player,mainBoard.towerBonuses[i][j]);
    		    			break;
    		    		case 1: Character character=mainBoard.characterMap.get(cardNumber);
    		    			cardGotten=getCharacterCard(character,player,mainBoard.towerBonuses[i][j]);
    		    			break;
    		    		case 2: Building building=mainBoard.buildingMap.get(cardNumber);
    		    			cardGotten=getBuildingCard(building,player,mainBoard.towerBonuses[i][j]);
    		    			break;
    		    		case 3: Venture venture=mainBoard.ventureMap.get(cardNumber);
    		    			cardGotten=getVentureCard(venture,player,mainBoard.towerBonuses[i][j]);
    		    			break;
    		    		default: System.out.println("This tower doesn't exist!");
    		    			break;
    	    		}
    	    		return cardGotten;
    	    	}
        return false;
    }
    
    
    public boolean getTerritoryCard(Territory territory,Player player,ActionBonus actionBonus){
    	//instantResources
    	territoryHandler.doInstantEffect(territory.instantBonuses,player);
    	ArrayList<String> possessedTerritories = player.personalBoard.getPossessions("Territory");
    	int militaryPoints = player.points.getMilitary();
    	boolean canGet=false;
    	if (possessedTerritories.size()<6){
    		//if there is a place for the territory
    		switch(possessedTerritories.size()){
    		//checking if the player has enough military points
    		case 2: if(militaryPoints >= 3) 
    			canGet=true;
    		break;
    		case 3: if(militaryPoints >= 7) 
    			canGet=true;
    		break;
    		case 4: if(militaryPoints >= 12) 
    			canGet=true;
    		break;
    		case 5: if(militaryPoints >= 18) 
    			canGet=true;
    		break;
    		default: canGet=true;
    		break;
    		}
    		if(canGet==true){
    			//add the territory to PersonalBoard
    			possessedTerritories.add(territory.cardName);
    			player.personalBoard.setPossessions(possessedTerritories,"Territory");
    			territoryHandler.doInstantEffect(territory.instantBonuses, player);
    			return true;
    			}
    		else
        		System.out.println("You don't have enough military points");
    	}
    	else 
    		System.out.println("You can't have more than 6 territories! ");
    	return false;
    }

    public boolean getCharacterCard(Character character,Player player,ActionBonus actionBonus){
    	ArrayList<String> possessedCharacters = player.personalBoard.getPossessions("Character");
		if (possessedCharacters.size()<6){
	    		try {
					player.resources.setCoins(-character.costCoins);
				} catch (NotEnoughResources e) {
					e.printStackTrace();
					return false;
				}
	    		possessedCharacters.add(character.cardName);
    			player.personalBoard.setPossessions(possessedCharacters,"Character");
    			characterHandler.doInstantEffect(character.instantBonuses, player);
    			characterHandler.activate(character.permanentEffect, player);
    			return true;
    	}
		else
			System.out.println("You can't have more than 6 characters!");
    	return false;
    }

    public boolean getBuildingCard(Building building,Player player,ActionBonus actionBonus){
    	ArrayList<String> possessedBuildings = player.personalBoard.getPossessions("Building");
		if (possessedBuildings.size()<6){
	    		try {
					subCardResources(building.costResources,player);
					addCardPoints(building.instantBonuses,player);
				} catch (NotEnoughResources | NotEnoughPoints e) {
					e.printStackTrace();
					return false;
				}
	    		possessedBuildings.add(building.cardName);
	    		player.personalBoard.setPossessions(possessedBuildings,"Building");
	    		return true;
	    	
    	}
		else
			System.out.println("You can't have more than 6 buildings!");
    	return false;
    }
    
    public boolean getVentureCard(Venture venture,Player player,ActionBonus actionBonus){
    	ArrayList<String> possessedVentures = player.personalBoard.getPossessions("Venture");
		if (possessedVentures.size()<6){
	    	if(player.points.getMilitary() >= venture.neededMilitary){
	    		try {
					subCardResources(venture.costResources,player);
				} catch (NotEnoughResources e1) {
					e1.printStackTrace();
					return false;
				}
	    		try {
					player.points.setMilitary(-venture.costMilitary);
				} catch (NotEnoughPoints e) {
					e.printStackTrace();
					return false;
				}
	    		possessedVentures.add(venture.cardName);
	    		player.personalBoard.setPossessions(possessedVentures,"Venture");
	    		player.points.setFinalVictory(venture.finalVictory);
	    		ventureHandler.doInstantEffect(venture.instant, player);
	    		return true;
	    	}
    		else
    			System.out.println("You don't have enough Military Points!");
    	}
		else
			System.out.println("You can't have more than 6 ventures!");
    	return false;
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
    
    public void addFamilyMemberToTheTower(FamilyMember familyMember , Integer cardNumber, Player player) {
        int i,j;
        boolean coloredFamilyMemberOnTheTower = false;
        boolean uncoloredFamilyMemberOnTheTower = false;
        Integer[][] cardsOnTheTowers = mainBoard.getCardsOnTheTowers();
        Integer[] diceValues = player.personalMainBoard.getDiceValues(); // we use the player Personal MainBaord
        FamilyMember[][] familyMembersOnTheTowers = player.personalMainBoard.getFamilyMembersLocation().getFamilyMembersOnTheTowers(); // we use the player Personal MainBaord
    	
        for(i=0, j=0;!cardsOnTheTowers[i][j].equals(cardNumber) && i<4;i++)
        	for(j=0;!cardsOnTheTowers[i][j].equals(cardNumber) && j<4;j++);
    		//search the coordinates of the card in the board
        int k=j;
        int p=i;
        //to store i and j as the coordinates of the position interested, the if check if the player can get the card with a specific family member
        if(familyMembersOnTheTowers[i][k] == null && ((diceValues[familyMemberColortoDiceValue(familyMember.color)]+familyMember.getServants())>=(((i+1)*2)-1))){
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
        		if(player.resources.getCoins()>=3 && getCard(cardNumber,player)){
        			(familyMembersOnTheTowers[i][k].playerColor)=(familyMember.playerColor);
	        		(familyMembersOnTheTowers[i][k].color)=(familyMember.color);
	        		try {
						player.resources.setCoins(-3);
					} catch (NotEnoughResources e) {
						e.printStackTrace();
					}
	        		setTowerBonus(mainBoard.towerBonuses[j][i],player);
	        		}
        		else
        			System.out.println("You don't have the necessary resources!");
	        	}
        	if (uncoloredFamilyMemberOnTheTower==false && coloredFamilyMemberOnTheTower==false){
        		//if there is none of my family members
        		for(p=0;p<4 && familyMembersOnTheTowers[p][k]==null;p++);
        		if(p==5 && getCard(cardNumber,player)){
        			//if the tower is free
        			(familyMembersOnTheTowers[i][k].playerColor)=(familyMember.playerColor);
        			(familyMembersOnTheTowers[i][k].color)=(familyMember.color);
	        		setTowerBonus(mainBoard.towerBonuses[j][i],player);
        		}
        		else{
        			//if the tower is occupied
        			if(player.resources.getCoins()>=3 && getCard(cardNumber,player)){
        				(familyMembersOnTheTowers[i][k].playerColor)=(familyMember.playerColor);
            			(familyMembersOnTheTowers[i][k].color)=(familyMember.color);
    	        		try {
							player.resources.setCoins(-3);
						} catch (NotEnoughResources e) {
							e.printStackTrace();
						}
    	        		setTowerBonus(mainBoard.towerBonuses[j][i],player);
        			}
        			else
            			System.out.println("You don't have the necessary resources!");
        		}
        	}
        }
        else	
        	System.out.println("This position is occupied or your family mmeber hasn't a value high enough!");	
        
    }
    
    public void setTowerBonus(ActionBonus towerBonus,Player player){
    	try {
			addPlayerResources(towerBonus.resources,player);
			addPlayerPoints(towerBonus.points,player);
		} catch (NotEnoughResources | NotEnoughPoints e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    public void addPlayerResources (PlayerResources resources, Player player) throws NotEnoughResources{
    	PlayerResources playerResources = player.resources;
    	playerResources.setCoins(resources.getCoins());
    	playerResources.setWoods(resources.getWoods());
    	playerResources.setStones(resources.getStones());
    	playerResources.setServants(resources.getServants());
    	councilHandler.getCouncil(resources.getCouncil(),player);
    	player.resources=playerResources;
    }
    
    public void subCardResources (CardResources resources, Player player) throws NotEnoughResources{
    	PlayerResources playerResources = player.resources;
    	playerResources.setCoins(-resources.coins);
    	playerResources.setWoods(-resources.woods);
    	playerResources.setStones(-resources.stones);
    	playerResources.setServants(-resources.servants);
    	player.resources=playerResources;
    }
    
    public void addCardResources (CardResources resources, Player player) throws NotEnoughResources{
    	PlayerResources playerResources = player.resources;
    	playerResources.setCoins(resources.coins);
    	playerResources.setWoods(resources.woods);
    	playerResources.setStones(resources.stones);
    	playerResources.setServants(resources.servants);
    	player.resources=playerResources;
    }
    
    public void addPlayerPoints (PlayerPoints points, Player player) throws NotEnoughPoints{
    	PlayerPoints playerPoints = player.points;
    	playerPoints.setFaith(points.getFaith());
    	playerPoints.setFaith(points.getVictory());
    	playerPoints.setFinalVictory(points.getFinalVictory());
    	playerPoints.setMilitary(points.getMilitary());
    	player.points=playerPoints;
    }
    
    public void subCardPoints (CardPoints points, Player player) throws NotEnoughPoints{
    	PlayerPoints playerPoints = player.points;
    	playerPoints.setFaith(-points.faith);
    	playerPoints.setFaith(-points.victory);
    	playerPoints.setMilitary(-points.military);
    	player.points=playerPoints;
    }
    
    public void addCardPoints (CardPoints points, Player player) throws NotEnoughPoints{
    	PlayerPoints playerPoints = player.points;
    	playerPoints.setFaith(points.faith);
    	playerPoints.setFaith(points.victory);
    	playerPoints.setMilitary(points.military);
    	player.points=playerPoints;
    }

    public void addFamilyMemberToTheMarket(FamilyMember familyMember, Integer position, Player player) {
    	FamilyMember[] familyMembersAtTheMarket = player.personalMainBoard.getFamilyMembersLocation().getFamilyMembersOnTheMarket(); // we use the player Personal MainBaord
        if(familyMembersAtTheMarket[position] == null && position<=marketSize){
        	(familyMembersAtTheMarket[position].color) = (familyMember.color);
        	(familyMembersAtTheMarket[position].playerColor) = (familyMember.playerColor);
        	switch(position){
	        	case 1: try {
					player.resources.setCoins(5);
				} catch (NotEnoughResources e) {
					e.printStackTrace();
				}
	        		break;
	        	case 2: try {
					player.resources.setServants(5);
				} catch (NotEnoughResources e) {
					e.printStackTrace();
				}
	        		break;
	        	case 3: try {
					player.resources.setCoins(2);
					player.points.setMilitary(3);
				} catch (NotEnoughResources | NotEnoughPoints e) {
					e.printStackTrace();
				}
	    			break;
	        	case 4: councilHandler.getCouncil(1,player);
	        		break;
	        	default: System.out.println("Invalid position! the position must be between 1 and 4");
	        		break;
        	}
        }
        else {
        	System.out.println("This place is occupied or not usable if two player game");
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
    				System.out.println("The production area is full!");
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
    				System.out.println("You can't place another family member");
    			for(i=0;familyMembersAtProductionOrHarvest[i]!=null && i<harvestAndProductionSize;i++);
    			//move i to the first free slot
    			if(j==-1){
    				//if there is a colored family member
    				if(familyMember.color=="uncolored"){
    					familyMembersAtProductionOrHarvest[i].color=familyMember.color;
    		    		familyMembersAtProductionOrHarvest[i].playerColor=familyMember.playerColor;
		    			doAction=true;}
    				else
    					System.out.println("You can place just one uncolored family member");
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
	    			playerBoardHandler.activateHarvest(player.personalMainBoard.getDiceValues()[familyMemberColortoDiceValue(familyMember.color)]-penalty,player); // we use the player Personal MainBaord
	    		if(actionType=="Harvest")
	    			playerBoardHandler.activateHarvest(player.personalMainBoard.getDiceValues()[familyMemberColortoDiceValue(familyMember.color)]-penalty,player); // we use the player Personal MainBaord
	    		else
	    			System.out.println("Invalid action it must be Production or Harvest");
    		}
    			
    		}

    public void inizializeTheGame() {
        // TODO implement here
        try {
			gsonReader.fileToCard(mainBoard);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    
    public void loadCardsOnTheMainBoard(){
    	int j = 0;
    	Integer[][] cardsOnTheTowers = mainBoard.getCardsOnTheTowers();
    	//checking the period to load the correct cards, we are using integers because
    	//the cards on the hashmaps use an integer as a key
    	//keys 1-8 first period, 9-16 second period, 17-24 third period
    	if(period==1){
    			j=1;
    			}
    		else if(period==2){
    			j=9;
    		}
    		else{
    			j=17;
    		}
    	//if it is the first round of a period it loads four random cards of the correct period
    	if(round==1){
    		ArrayList<Integer> list = new ArrayList<Integer>();
	    	for(int i=0;i<4;i++,list.clear()){
	            for (int r=0+j; r<8+j; r++) {
	                list.add(new Integer(r));
	            }
	            //ordering randomly the numbers on the list
	            Collections.shuffle(list);
		    	for(int k=0;k<4;k++){
			    	cardsOnTheTowers[k][i] = list.get(k);
			    }
	    	}
    	}
    	//if it is the second round of a period, we load the four remaining cards for this period
    	else{
    		int l=0;
    		int k=0;
    		int p=0;
    		boolean alreadyOnTheBoard = false;
    		ArrayList<Integer> list = new ArrayList<Integer>();
    		for(int i=0;i<4;i++,list.clear()){
    			//checking if a number l generated in the range of possible values 
    			//for the specific period is present or not
    			for(p=0,l=0;l<8 && p<4; l++){
		    	    for(alreadyOnTheBoard=false, k=0;k<4;k++){
		    	    	if(cardsOnTheTowers[k][i]==(l+j)){
		    	    		alreadyOnTheBoard=true;}
		    	    }
		    	    //if l is not present 
	    	    	if(alreadyOnTheBoard==false){
	    	    		list.add(new Integer(l+j));
	    	    		p++;
	    	    	}
    			}	
    			//ordering randomly the numbers on the list
    			Collections.shuffle(list);
    		    for(k=0;k<4;k++){
    			    cardsOnTheTowers[k][i] = list.get(k);
    		    }		
    		}
    	}
    	this.mainBoard.setCardsOnTheTowers(cardsOnTheTowers);
    	
    }
   
    public String[][] cardNameOnTheMainBoard(){
    	Integer[][] cardsOnTheTowers = mainBoard.getCardsOnTheTowers();
    	String[][] cardNamesOnTheTowers = new String[4][4]; 
    	for(int i=0;i<4;i++){
            for (int j=0; j<4; j++){
            	switch(i){
	        	case 0: cardNamesOnTheTowers[j][i] = mainBoard.territoryMap.get(cardsOnTheTowers[j][i]).cardName;
	        		break;
	        	case 1: cardNamesOnTheTowers[j][i] = mainBoard.characterMap.get(cardsOnTheTowers[j][i]).cardName;
	        		break;
	        	case 2: cardNamesOnTheTowers[j][i] = mainBoard.buildingMap.get(cardsOnTheTowers[j][i]).cardName;
	    			break;
	        	case 3: cardNamesOnTheTowers[j][i] = mainBoard.ventureMap.get(cardsOnTheTowers[j][i]).cardName;
	        		break;
	        	default: System.out.println("Invalid position it has to be between 0 and 3");
	        		break;
            	}
            }
        }
    	return cardNamesOnTheTowers;
    }

    public Integer calculateFinalPoints(Player player,MainBoard mainBoard) {
        // TODO implement here
    	//remember of excommunications!
        return null; //prevent error
    }

}