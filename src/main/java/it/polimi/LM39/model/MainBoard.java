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

    public FamilyMembersLocation familyMembersLocation = new FamilyMembersLocation();

    public Rankings rankings = new Rankings();
    
    public Integer[] militaryForTerritory = {3,7,12,18};
    
    public Integer marketSize;
	
	public Integer harvestAndProductionSize;

    private ActionBonus[][] towersBonuses = new ActionBonus[4][4]; //the bonuses are loaded from file
    
    public ActionBonus[] faithBonuses = new ActionBonus[16]; //the bonuses are loaded from file
    
    public ActionBonus[] marketBonuses = new ActionBonus[4]; //the bonuses are loaded from file
    
    public ActionBonus councilPalaceBonus = new ActionBonus(); //the bonuses are loaded from file
    
    public  HashMap<Integer,Territory> territoryMap = new HashMap<Integer,Territory>();

    public  HashMap<Integer,Building> buildingMap = new HashMap<Integer,Building>();

    public  HashMap<Integer,Venture> ventureMap = new HashMap<Integer,Venture>();

    public  HashMap<Integer,Character> characterMap = new HashMap<Integer,Character>();

    public  HashMap<String,Leader> leaderMap = new HashMap<String,Leader>();
    
    public  ArrayList<PersonalBonusTile> personalBonusTiles = new ArrayList<PersonalBonusTile>();
    
    public  ArrayList<String> leaderName = new ArrayList<String>();

    public  HashMap<Integer,Excommunication> excommunicationMap = new HashMap<Integer,Excommunication>();

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

    public void setPlayedLeaderCard(String playedLeaderCard) {
        this.playedLeaderCard.add(playedLeaderCard);
    }

    public ArrayList<String> getPlayedLeaderCard() {
    	return this.playedLeaderCard;
    }

}