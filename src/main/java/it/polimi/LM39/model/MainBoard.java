package it.polimi.LM39.model;

import java.util.HashMap;


public class MainBoard {

	private Integer[][] cardsOnTheTowers = new Integer[4][4];

    private Integer[] diceValues = new Integer[4];

    private Integer[] excommunicationsOnTheBoard = new Integer[3];

    private Integer[] playedLeaderCard;

    private FamilyMembersLocation familyMembersLocation;

    private Rankings rankings;

    public ActionBonus[][] towerBonuses = new ActionBonus[4][4]; //the bonuses are loaded from file
    
    public ActionBonus[] faithBonuses = new ActionBonus[16]; //the bonuses are loaded from file
    
    public ActionBonus[] marketBonuses = new ActionBonus[4]; //the bonuses are loaded from file
    
    public ActionBonus councilPalaceBonus = new ActionBonus(); //the bonuses are loaded from file
    
    public ActionBonus harvestBonus = new ActionBonus(); //the bonuses are loaded from file
    
    public ActionBonus productionBonus = new ActionBonus(); //the bonuses are loaded from file
    
    public HashMap<Integer,Territory> territoryMap = new HashMap<Integer,Territory>();

    public HashMap<Integer,Building> buildingMap = new HashMap<Integer,Building>();

    public HashMap<Integer,Venture> ventureMap = new HashMap<Integer,Venture>();

    public HashMap<Integer,Character> characterMap = new HashMap<Integer,Character>();

    public HashMap<Integer,Leader> leaderMap = new HashMap<Integer,Leader>();

    public HashMap<Integer,Excommunication> excommunicationMap = new HashMap<Integer,Excommunication>();


    public void setCardsOnTheTowers(Integer[][] cardOnTheTowers) {
        this.cardsOnTheTowers=cardOnTheTowers;
    }

    public Integer[][] getCardsOnTheTowers() {
        return this.cardsOnTheTowers;
    }

    public void setDiceValues(Integer[] diceValues) {
        this.diceValues=diceValues;
    }

    public Integer[] getDiceValues() {
       return this.diceValues;  
    }

    public void setExcommunicationsOnTheBoard(Integer[] excommunicationsOnTheBoard) {
        this.excommunicationsOnTheBoard=excommunicationsOnTheBoard;
    }

    public Integer[] getExcommunicationsOnTheBoard() {
    	return this.excommunicationsOnTheBoard;
    }

    public void setPlayedLeaderCard(Integer[] playedLeaderCard) {
        this.playedLeaderCard=playedLeaderCard;
    }

    public Integer[] getPlayedLeaderCard() {
    	return this.playedLeaderCard;
    }

    public void setFamilyMembersLocation(FamilyMembersLocation familyMembersLocation) {
        this.familyMembersLocation=familyMembersLocation;
    }

    public FamilyMembersLocation getFamilyMembersLocation() {
    	return this.familyMembersLocation;
    }

    public void setRankings(Rankings rankings) {
        this.rankings=rankings;
    }

    public Rankings getRankings() {
    	return this.rankings;
    }

}