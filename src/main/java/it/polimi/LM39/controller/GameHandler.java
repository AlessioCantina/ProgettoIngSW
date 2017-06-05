package it.polimi.LM39.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import it.polimi.LM39.exception.CardNotFoundException;
import it.polimi.LM39.exception.FailedToReadFileException;
import it.polimi.LM39.exception.FailedToRegisterEffectException;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.*;
import it.polimi.LM39.model.Character;
import it.polimi.LM39.server.NetworkPlayer;
/**
 * 
 */
public class GameHandler {
	

	public Integer marketSize = 0;
	
	public Integer harvestAndProductionSize = 0;
    
	private Integer period = 1;

    private Integer round = 1;
    
    public MainBoard mainBoard;
    
    public BuildingHandler buildingHandler = new BuildingHandler();
    
    public TerritoryHandler territoryHandler = new TerritoryHandler();

    public VentureHandler ventureHandler = new VentureHandler();

    public CharacterHandler characterHandler = new CharacterHandler();

    public LeaderHandler leaderHandler = new LeaderHandler();

    public ExcommunicationHandler excommunicationHandler = new ExcommunicationHandler();

    public PlayerBoardHandler playerBoardHandler = new PlayerBoardHandler();
    
    public CouncilHandler councilHandler = new CouncilHandler();
    
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
    	diceValues[i] = (rand.nextInt(6) + 1);
    	// There is a + 1 because rand.nextInt(6) generates number from 0 to 5 but we need from 1 to 6
    	}
    	mainBoard.setDiceValues(diceValues);    
    }

    public boolean getCard(Integer cardNumber,NetworkPlayer player, Integer towerNumber) throws IOException {
    	boolean cardGotten=false;
    	    		switch(towerNumber){
    		    		case 0: Territory territory=MainBoard.territoryMap.get(cardNumber);
    		    			cardGotten=getTerritoryCard(territory,player,cardNumber);
    		    			break;
    		    		case 1: Character character=MainBoard.characterMap.get(cardNumber);
    		    			cardGotten=getCharacterCard(character,player,cardNumber);
    		    			break;
    		    		case 2: Building building=MainBoard.buildingMap.get(cardNumber);
    		    			cardGotten=getBuildingCard(building,player,cardNumber);
    		    			break;
    		    		case 3: Venture venture=MainBoard.ventureMap.get(cardNumber);
    		    			cardGotten=getVentureCard(venture,player,cardNumber);
    		    			break;
    		    		default: player.setMessage("This tower doesn't exist!");
    		    			break;
    	    		}
    	    		return cardGotten;
    }
    
    
    public boolean getTerritoryCard(Territory territory,NetworkPlayer player,Integer cardNumber) throws IOException{
    	//instantResources
    	territoryHandler.doInstantEffect(territory.instantBonuses,player);
    	ArrayList<Integer> possessedTerritories = player.personalBoard.getPossessions("Territory");
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
    		default: canGet=false;  
    		break;
    		}
    		if(canGet==true){
    			//add the territory to PersonalBoard
    			possessedTerritories.add(cardNumber);
    			player.personalBoard.setPossessions(possessedTerritories,"Territory");
    			
    			territoryHandler.doInstantEffect(territory.instantBonuses, player);
    			return true;
    			}
    		else
        		player.setMessage("You don't have enough military points");
    	}
    	else 
    		player.setMessage("You can't have more than 6 territories! ");
    	return false;
    }

    public boolean getCharacterCard(Character character,NetworkPlayer player,Integer cardNumber) throws IOException{
    	ArrayList<Integer> possessedCharacters = player.personalBoard.getPossessions("Character");
		if (possessedCharacters.size()<6){
	    		try {
	    			coinsForCharacter(player,character);
				} catch (NotEnoughResourcesException e) {
					player.setMessage("You don't have enough coins!");
					e.printStackTrace();
					return false;
				}
	    		possessedCharacters.add(cardNumber);
    			player.personalBoard.setPossessions(possessedCharacters,"Character");
    			characterHandler.doInstantEffect(character.instantBonuses, player);
    			characterHandler.activate(character.permanentEffect, player);
    			return true;
    	}
		else
			player.setMessage("You can't have more than 6 characters!");
    	return false;
    }
    
    public void coinsForCharacter(NetworkPlayer player ,Character character) throws NotEnoughResourcesException{
    	player.resources.setCoins(-character.costCoins);
    }

    public boolean getBuildingCard(Building building,NetworkPlayer player,Integer cardNumber) throws IOException{
    	ArrayList<Integer> possessedBuildings = player.personalBoard.getPossessions("Building");
		if (possessedBuildings.size()<6){
	    		try {
	    			resourcesForBuilding(player ,building);
					addCardPoints(building.instantBonuses,player);
				} catch (NotEnoughResourcesException | NotEnoughPointsException e) {
					player.setMessage("You don't have enough resources or points!");
					e.printStackTrace();
					return false;
				}
	    		possessedBuildings.add(cardNumber);
	    		player.personalBoard.setPossessions(possessedBuildings,"Building");
	    		return true;
	    	
    	}
		else
			player.setMessage("You can't have more than 6 buildings!");
    	return false;
    }
    
    public void resourcesForBuilding(NetworkPlayer player ,Building building) throws NotEnoughResourcesException{
    	subCardResources(building.costResources,player);
    }
    
    public boolean getVentureCard(Venture venture,NetworkPlayer player,Integer cardNumber) throws IOException{
    	ArrayList<Integer> possessedVentures = player.personalBoard.getPossessions("Venture");
    	Integer choice = 0;
		if (possessedVentures.size()<6){
	    	if(venture.costMilitary!=0 && (venture.costResources.coins!=0 || venture.costResources.woods!=0 || venture.costResources.stones!=0 || venture.costResources.servants!=0)) {
	    		//ask to the player what payment he wants to do
	    		player.setMessage("What payment do you want to do? 1 or 2");
	    		//get the player response
	    		choice = Integer.parseInt(player.sendMessage());
	    	}
	    	if(venture.costMilitary==0 || choice == 2){
	    		try {
	    			resourcesForVenture(player,venture);
	    		} catch (NotEnoughResourcesException e1) {
	    			player.setMessage("You don't have enough resources!");
	    			e1.printStackTrace();
	    			player.setMessage("You don't have enough resources!");
	    			return false;
				}
	    	}
	    
	    	else if((venture.costMilitary!=0 || choice ==1) && (player.points.getMilitary() >= venture.neededMilitary)){
	    		try {
	    			player.points.setMilitary(-venture.costMilitary);
	    		} catch (NotEnoughPointsException e) {
	    			e.printStackTrace();
	    			player.setMessage("You don't have enough military points!");
	    			return false;
	    		}
	    	}
	    	else{
	    		player.setMessage("You don't have enough military points!");
    			return false;
    			}
	    
	    	possessedVentures.add(cardNumber);
	    	player.personalBoard.setPossessions(possessedVentures,"Venture");
	    	player.points.setFinalVictory(venture.finalVictory);
	    	ventureHandler.doInstantEffect(venture.instant, player);
	    	return true;
	   }
		else
			player.setMessage("You can't have more than 6 ventures!");
    	return false;
    }
    
    public void resourcesForVenture(NetworkPlayer player ,Venture venture) throws NotEnoughResourcesException{
    	subCardResources(venture.costResources,player);
    }
    
    public Integer familyMemberColorToDiceValue(String familyMemberColor,NetworkPlayer player) throws IOException{
    	//The order followed is the one on the Game Board for the dices positions
    	Integer value = -1;
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
	    	default: player.setMessage("Invalid familyMemberColor");
	    		break;
    	}
    	return value;
    }
    
    public boolean addFamilyMemberToTheTower(FamilyMember familyMember , Integer cardNumber, NetworkPlayer player) throws IOException, NotEnoughResourcesException, NotEnoughPointsException {
        int i,j;
        boolean coloredFamilyMemberOnTheTower = false;
        boolean uncoloredFamilyMemberOnTheTower = false;
        Integer[][] cardsOnTheTowers = mainBoard.getCardsOnTheTowers();
        Integer[] diceValues = player.personalMainBoard.getDiceValues(); // we use the player Personal MainBaord
        FamilyMember[][] familyMembersOnTheTowers = player.personalMainBoard.getFamilyMembersLocation().getFamilyMembersOnTheTowers(); // we use the player Personal MainBaord
    	
        for(i=0, j=0;!cardsOnTheTowers[i][j].equals(cardNumber) && i<4;i++)
        	for(j=0;!cardsOnTheTowers[i][j].equals(cardNumber) && j<4;j++){}
    		//search the coordinates of the card in the board
        int p=i-1;
        int k=j;
        //to store i and j as the coordinates of the position interested, the if check if the player can get the card with a specific family member
        if(familyMembersOnTheTowers[p][k] == null && ((diceValues[familyMemberColorToDiceValue(familyMember.color,player)]+familyMember.getServants()) >= (mainBoard.getTowersValue())[p][k])){
        	//if the place is free and the family member has an high enough value, ((i+1)*2)-1 is to convert the value i of the matrix to the value of the floor in dice
        	for(i=0;i<4;i++){
        		if((familyMembersOnTheTowers[i][k].playerColor).equals(familyMember.playerColor)){
        			//if there is one of my family members on the tower
        			if (("uncolored").equals(familyMembersOnTheTowers[i][k].color))
        				//if this family member is uncolored
        				uncoloredFamilyMemberOnTheTower=true;
        			else
        				//if this family member is colored
        				coloredFamilyMemberOnTheTower=true;
        		}
        	}
        	if ((uncoloredFamilyMemberOnTheTower==true && coloredFamilyMemberOnTheTower==false) || (coloredFamilyMemberOnTheTower==true && ("uncolored").equals(familyMember.color))){
        	//if there is an uncolored family member on the tower or there is a colored one but the player uses an uncolored family member
        		if(player.resources.getCoins()>=3 && getCard(cardNumber,player,k)){
	        		try {
						player.resources.setCoins(player.personalMainBoard.occupiedTowerCost);
					} catch (NotEnoughResourcesException e) {
						player.setMessage("You don't have enough resources!");
						e.printStackTrace();
						return false;
					}
	        		setTowerBonus((mainBoard.getTowersBonuses())[p][k],player);
	        		(familyMembersOnTheTowers[p][k].playerColor)=(familyMember.playerColor);
	        		(familyMembersOnTheTowers[p][k].color)=(familyMember.color);
	        		return true;
	        		}
        		else
        			player.setMessage("You don't have the necessary resources!");
	        	}
        	if (uncoloredFamilyMemberOnTheTower==false && coloredFamilyMemberOnTheTower==false){
        		//if there is none of my family members
        		for(i=0;i<4 && familyMembersOnTheTowers[i][k]==null;i++){}
        		if(i==5 && getCard(cardNumber,player,k)){
        			//if the tower is free
        			(familyMembersOnTheTowers[i][k].playerColor)=(familyMember.playerColor);
        			(familyMembersOnTheTowers[i][k].color)=(familyMember.color);
	        		setTowerBonus((mainBoard.getTowersBonuses())[p][k],player);
	        		return true;
        		}
        		else{
        			//if the tower is occupied
        			if(player.resources.getCoins()>=3 && getCard(cardNumber,player,k)){
    	        		try {
							player.resources.setCoins(player.personalMainBoard.occupiedTowerCost);
						} catch (NotEnoughResourcesException e) {
							player.setMessage("You don't have enough resources");
							e.printStackTrace();
							return false;
						}
    	        		setTowerBonus((mainBoard.getTowersBonuses())[p][k],player);
    	        		(familyMembersOnTheTowers[p][k].playerColor)=(familyMember.playerColor);
            			(familyMembersOnTheTowers[p][k].color)=(familyMember.color);
    	        		return true;
        			}
        			else{
        				player.setMessage("You don't have the necessary resources!");
        				return false;
        				}
        		}
        	}
        }
        else{
        	player.setMessage("This position is occupied or your family mmeber hasn't a value high enough!");	
        	return false;
        }
       return false; 
    }
    
    public void setTowerBonus(ActionBonus towerBonus,NetworkPlayer player) throws NotEnoughResourcesException, NotEnoughPointsException{
    		addCardResources(towerBonus.resources,player);
    		addCardPoints(towerBonus.points,player);
    	
    }
    
    //probably useless method
    /*
    public void addPlayerResources (PlayerResources resources, NetworkPlayer player) throws NotEnoughResourcesException, NotEnoughPointsException{
    	PlayerResources playerResources = player.resources;
    	playerResources.setCoins(resources.getCoins());
    	playerResources.setWoods(resources.getWoods());
    	playerResources.setStones(resources.getStones());
    	playerResources.setServants(resources.getServants());
    	ArrayList<Integer> list = new ArrayList<Integer>();
    	councilHandler.getCouncil(resources.getCouncil(),player,this,list);
    	player.resources=playerResources;
    }
    */
    
    //probably useless method
    /*
    public void addPlayerPoints (PlayerPoints points, NetworkPlayer player) throws NotEnoughPointsException{
    	PlayerPoints playerPoints = player.points;
    	playerPoints.setFaith(points.getFaith());
    	playerPoints.setFaith(points.getVictory());
    	playerPoints.setFinalVictory(points.getFinalVictory());
    	playerPoints.setMilitary(points.getMilitary());
    	player.points=playerPoints;
    }
    */
    
    
    public void subCardResources (CardResources resources, NetworkPlayer player) throws NotEnoughResourcesException{
    	PlayerResources playerResources = player.resources;
    	playerResources.setCoins(-resources.coins);
    	playerResources.setWoods(-resources.woods);
    	playerResources.setStones(-resources.stones);
    	playerResources.setServants(-resources.servants);
    	player.resources=playerResources;
    }
    
    public void addCardResources (CardResources resources, NetworkPlayer player) throws NotEnoughResourcesException{
    	PlayerResources playerResources = player.resources;
    	playerResources.setCoins(resources.coins);
    	playerResources.setWoods(resources.woods);
    	playerResources.setStones(resources.stones);
    	playerResources.setServants(resources.servants);
    	player.resources=playerResources;
    }
    
    public void subCardPoints (CardPoints points, NetworkPlayer player) throws NotEnoughPointsException{
    	PlayerPoints playerPoints = player.points;
    	playerPoints.setFaith(-points.faith);
    	playerPoints.setFaith(-points.victory);
    	playerPoints.setMilitary(-points.military);
    	player.points=playerPoints;
    }
    
    public void addCardPoints (CardPoints points, NetworkPlayer player) throws NotEnoughPointsException{
    	PlayerPoints playerPoints = player.points;
    	playerPoints.setFaith(points.faith);
    	playerPoints.setFaith(points.victory);
    	playerPoints.setMilitary(points.military);
    	player.points=playerPoints;
    }

    public boolean addFamilyMemberToTheMarket(FamilyMember familyMember, Integer position, NetworkPlayer player) throws IOException, NotEnoughResourcesException, NotEnoughPointsException {
    	FamilyMember[] familyMembersAtTheMarket = player.personalMainBoard.getFamilyMembersLocation().getFamilyMembersOnTheMarket(); // we use the player Personal MainBaord
        if(familyMembersAtTheMarket[position] == null && position<=marketSize){
        	(familyMembersAtTheMarket[position].color) = (familyMember.color);
        	(familyMembersAtTheMarket[position].playerColor) = (familyMember.playerColor);
        	switch(position){
	        	case 1: 
					player.resources.setCoins(5);
	        		break;
	        	case 2: 
					player.resources.setServants(5);
					break;
	        	case 3: 
					player.resources.setCoins(2);
					player.points.setMilitary(3);
	    			break;
	        	case 4: ArrayList<Integer> list = new ArrayList<Integer>();
	        			councilHandler.getCouncil(1,player,this,list);
	        		break;
	        	default: player.setMessage("Invalid position! the position must be between 1 and 4");
	        			 return false;
        	}
        }
        else {
        	player.setMessage("This place is occupied or not usable if two player game");
        	return false;
        }
        return true;
    }
    
    public boolean addFamilyMemberToProductionOrHarvest(FamilyMember familyMember, FamilyMember[] familyMembersAtProductionOrHarvest, String actionType,NetworkPlayer player) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	int i;
    	//doAction is false by default
    	boolean doAction;
    	//to know if the action Harvest or Production can be done
    	Integer penalty=3;
    	//penalty in case of first slot already occupied
    		for(i=0;!(familyMembersAtProductionOrHarvest[i].playerColor).equals(familyMember.playerColor) && i<harvestAndProductionSize;i++){}
    		if(i==harvestAndProductionSize){
    			//if there isn't any of my family Members
    			for(i=0;familyMembersAtProductionOrHarvest[i]!=null && i<harvestAndProductionSize;i++){}
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
    				player.setMessage("The production area is full!");
    				return false;
    			}
    		}
    		
    		else{
    			//if there is already one of my family members
    			int j=0;
    			for(i=0;i<harvestAndProductionSize;i++){
    				if((familyMembersAtProductionOrHarvest[i].playerColor).equals(familyMember.playerColor)){
    					if(("uncolored").equals(familyMembersAtProductionOrHarvest[i].color))
    						j++;
    					else
    						j--;
    					}
    			}
    			if(j==0){
    				//if there are already two of my family members
    				player.setMessage("You can't place another family member");
    				return false;
    			}	
    			for(i=0;familyMembersAtProductionOrHarvest[i]!=null && i<harvestAndProductionSize;i++){}
    			//move i to the first free slot
    			if(j==-1){
    				//if there is a colored family member
    				if(familyMember.color=="uncolored"){
    					familyMembersAtProductionOrHarvest[i].color=familyMember.color;
    		    		familyMembersAtProductionOrHarvest[i].playerColor=familyMember.playerColor;
		    			doAction=true;}
    				else{
    					player.setMessage("You can place just one uncolored family member");
    					return false;
    				}
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
	    			playerBoardHandler.activateHarvest(player.personalMainBoard.getDiceValues()[familyMemberColorToDiceValue(familyMember.color,player)]-penalty,player); // we use the player Personal MainBaord
	    		if(actionType=="Harvest")
	    			playerBoardHandler.activateHarvest(player.personalMainBoard.getDiceValues()[familyMemberColorToDiceValue(familyMember.color,player)]-penalty,player); // we use the player Personal MainBaord
	    		else{
	    			player.setMessage("Invalid action it must be Production or Harvest");
	    			return false;
	    		}
    		}
    		return true;
    	}

    public void initializeTheGame() throws FailedToReadFileException, FailedToRegisterEffectException {
    	playerBoardHandler.setGameHandler(this);
    	//initialize the value of an action space on the Towers
    	Integer [] towerValue = {1,3,5,7};
    	Integer[][] towersValue = new Integer[4][4];
    	for(int i=0;i<4;i++)
    		for(int j=0;j<4;j++)
    			towersValue[j][i]=towerValue[j];
    	mainBoard.setTowersValue(towersValue);
        //read the files
    	try {
			gsonReader.fileToCard();
		} catch (IOException e) {
			throw new FailedToReadFileException(e);
		}
        
    }
    
    public void loadCardsOnTheMainBoard(){
    	int j;
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
	                list.add(r);
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
    		int l,p,k;
    		//alreadyOnTheBoard is false by default
    		boolean alreadyOnTheBoard ;
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
	    	    		list.add(l+j);
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
   
    public String[][] cardNameOnTheMainBoard(NetworkPlayer player) throws IOException{
    	Integer[][] cardsOnTheTowers = mainBoard.getCardsOnTheTowers();
    	String[][] cardNamesOnTheTowers = new String[4][4]; 
    	for(int i=0;i<4;i++){
            for (int j=0; j<4; j++){
            	switch(i){
	        	case 0: cardNamesOnTheTowers[j][i] = MainBoard.territoryMap.get(cardsOnTheTowers[j][i]).cardName;
	        		break;
	        	case 1: cardNamesOnTheTowers[j][i] = MainBoard.characterMap.get(cardsOnTheTowers[j][i]).cardName;
	        		break;
	        	case 2: cardNamesOnTheTowers[j][i] = MainBoard.buildingMap.get(cardsOnTheTowers[j][i]).cardName;
	    			break;
	        	case 3: cardNamesOnTheTowers[j][i] = MainBoard.ventureMap.get(cardsOnTheTowers[j][i]).cardName;
	        		break;
	        	default: player.setMessage("Invalid position it has to be between 0 and 3");
	        		break;
            	}
            	mainBoard.setCardNamesOnTheTowers(cardNamesOnTheTowers);
            }
        }
    	return cardNamesOnTheTowers;
    }

    public Integer cardNameToInteger (String card, String[][] cardNamesOnTheTowers, Integer[][] cardOnTheTowers) throws CardNotFoundException{
    	for(int i=0;i<4;i++)
    		for(int j=0;j<4;j++)
    			if(cardNamesOnTheTowers[i][j].equals(card))
    				return cardOnTheTowers[i][j];
    	throw new CardNotFoundException("Card not found!"); //card not found
    			
    }
    
    
    public Integer calculateFinalPoints(NetworkPlayer player,MainBoard mainBoard) {
        // TODO implement here
    	//remember of excommunications!
    	//NoVictoryForCard
        return null; //prevent error
    }

    //ask to the player if he wants to add servants to the action
    public Integer addServants(NetworkPlayer player) throws IOException, NotEnoughResourcesException{
    	player.setMessage("Do you want to add servants? yes or no");
    	String response = player.sendMessage();
    	if(("yes").equals(response)){
    		player.setMessage("How many?");
    		Integer qty = Integer.parseInt(player.sendMessage());
    		player.resources.setServants(-qty);
    		return qty;
    	}
    	else if (("no").equals(response))
    		return 0;
    	else{
    		player.setMessage("The answer must be yes or no");
    		return addServants(player);
    	}
    }
    
    public void removeDecoration(Class toRemove, NetworkPlayer player){
		//if it is not a decorator
    	if (this.getClass() == toRemove)
			return;
	}
}