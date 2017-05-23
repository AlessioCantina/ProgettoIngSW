package it.polimi.LM39.model;

import java.util.HashMap;


public class MainBoard {

	private String[][] cardsOnTheTowers = new String[4][4];

    private Integer[] diceValues = new Integer[4];

    private Excommunication[] excommunicationsOnTheBoard = new Excommunication[3];

    private String[] playedLeaderCard;

    private FamilyMembersLocation familyMembersLocation;

    private Rankings rankings;

    public ActionBonus[][] towerBonuses = new ActionBonus[4][4];
   
    public ActionBonus[][] playerBoardBonuses = new ActionBonus[6][4];
    
    public ActionBonus[] faithBonuses = new ActionBonus[6];
    
    public HashMap<String,Territory> territoryMap = new HashMap<String,Territory>();

    public HashMap<String,Building> buildingMap = new HashMap<String,Building>();

    public HashMap<String,Venture> ventureMap = new HashMap<String,Venture>();

    public HashMap<String,Character> characterMap = new HashMap<String,Character>();

    public HashMap<String,Leader> leaderMap = new HashMap<String,Leader>();

    public HashMap<String,Excommunication> excommunicationMap = new HashMap<String,Excommunication>();


    public void setCardsOnTheTowers(String[][] cardOnTheTowers) {
        this.cardsOnTheTowers=cardOnTheTowers;
    }

    public String[][] getCardsOnTheTowers() {
        return this.cardsOnTheTowers;
    }

    public void setDiceValues(Integer[] diceValues) {
        this.diceValues=diceValues;
    }

    public Integer[] getDiceValues() {
       return this.diceValues;  
    }

    public void setExcommunicationsOnTheBoard(Excommunication[] excommunicationsOnTheBoard) {
        this.excommunicationsOnTheBoard=excommunicationsOnTheBoard;
    }

    public Excommunication[] getExcommunicationsOnTheBoard() {
    	return this.excommunicationsOnTheBoard;
    }

    public void setPlayedLeaderCard(String[] playedLeaderCard) {
        this.playedLeaderCard=playedLeaderCard;
    }

    public String[] getPlayedLeaderCard() {
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