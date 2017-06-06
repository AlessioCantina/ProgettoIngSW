package it.polimi.LM39.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class MainBoard implements Serializable{

	private static final long serialVersionUID = 5321590011683617039L;

	private Integer[][] cardsOnTheTowers = new Integer[4][4];
	
	private String[][] cardNamesOnTheTowers = new String[4][4];

    private Integer[] diceValues = new Integer[4];

    public Integer[] excommunicationsOnTheBoard = new Integer[3];
    
    private Integer[][] towersValue = new Integer[4][4];
    
    private ArrayList<String> playedLeaderCard = new ArrayList<String>();
    
    public Integer occupiedTowerCost = -3;

    public FamilyMembersLocation familyMembersLocation;

    public Rankings rankings;
    
    public Integer[] militaryForTerritory = {3,7,12,18};

    private ActionBonus[][] towersBonuses = new ActionBonus[4][4]; //the bonuses are loaded from file
    
    public ActionBonus[] faithBonuses = new ActionBonus[16]; //the bonuses are loaded from file
    
    public ActionBonus[] marketBonuses = new ActionBonus[4]; //the bonuses are loaded from file
    
    public ActionBonus councilPalaceBonus = new ActionBonus(); //the bonuses are loaded from file
    
    public ActionBonus harvestBonus = new ActionBonus(); //the bonuses are loaded from file
    
    public ActionBonus productionBonus = new ActionBonus(); //the bonuses are loaded from file
    
    public static HashMap<Integer,Territory> territoryMap = new HashMap<Integer,Territory>();

    public static HashMap<Integer,Building> buildingMap = new HashMap<Integer,Building>();

    public static HashMap<Integer,Venture> ventureMap = new HashMap<Integer,Venture>();

    public static HashMap<Integer,Character> characterMap = new HashMap<Integer,Character>();

    public static HashMap<String,Leader> leaderMap = new HashMap<String,Leader>();

    public static HashMap<Integer,Excommunication> excommunicationMap = new HashMap<Integer,Excommunication>();

   
    public void setTowersBonuses(ActionBonus[][] towersBonuses) {
        this.towersBonuses=towersBonuses;
    }

    public ActionBonus[][] getTowersBonuses() {
        return this.towersBonuses;
    }
    
    public void setTowersValue(Integer[][] towersValue) {
        this.towersValue=towersValue;
    }

    public Integer[][] getTowersValue() {
        return this.towersValue;
    }
    
    public void setCardsOnTheTowers(Integer[][] cardsOnTheTowers) {
        this.cardsOnTheTowers=cardsOnTheTowers;
    }

    public Integer[][] getCardsOnTheTowers() {
        return this.cardsOnTheTowers;
    }
    
    public void setCardNamesOnTheTowers(String[][] cardNamesOnTheTowers) {
        this.cardNamesOnTheTowers=cardNamesOnTheTowers;
    }
    
    public String[][] getCardNamesOnTheTowers() {
        return this.cardNamesOnTheTowers;
    }

    public void setDiceValues(Integer[] diceValues) {
        this.diceValues=diceValues;
    }

    public Integer[] getDiceValues() {
       return this.diceValues;  
    }

    public void setPlayedLeaderCard(ArrayList<String> playedLeaderCard) {
        this.playedLeaderCard=playedLeaderCard;
    }

    public ArrayList<String> getPlayedLeaderCard() {
    	return this.playedLeaderCard;
    }
    public FamilyMembersLocation getFamilyMembersLocation(){
    	return this.familyMembersLocation;
    }

}