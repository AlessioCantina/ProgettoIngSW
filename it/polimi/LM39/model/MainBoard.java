package it.polimi.LM39.model;

import java.util.HashMap;

/**
 * 
 */
public class MainBoard {

    /**
     * Default constructor
     */
    public MainBoard() {
    }

    /**
     * 
     */
    private String[][] cardsOnTheTowers = new String[4][4];

    /**
     * 
     */
    private Integer[] diceValues = new Integer[4];

    /**
     * 
     */
    private Excommunication[] excommunicationsOnTheBoard;

    /**
     * 
     */
    private String[] playedLeaderCard;

    /**
     * 
     */
    private FamilyMembersLocation familyMembersLocation;

    /**
     * 
     */
    private Rankings rankings;

    /**
     * 
     */
    public ActionBonus[][] towerBonuses = new ActionBonus[4][4];
    public ActionBonus[][] playerBoardBonuses = new ActionBonus[6][4];
    public ActionBonus[] faithBonuses = new ActionBonus[6];
    public HashMap<String,Territory> territoryMap = new HashMap<String,Territory>();

    /**
     * 
     */
    public HashMap<String,Building> buildingMap = new HashMap<String,Building>();

    /**
     * 
     */
    public HashMap<String,Venture> ventureMap = new HashMap<String,Venture>();

    /**
     * 
     */
    public HashMap<String,Character> characterMap = new HashMap<String,Character>();

    /**
     * 
     */
    public HashMap<String,Leader> leaderMap = new HashMap<String,Leader>();

    /**
     * 
     */
    public HashMap<String,Excommunication> excommunicationMap = new HashMap<String,Excommunication>();









    /**
     * @param cardOnTheTowers 
     * @return
     */
    public void setCardsOnTheTowers(String cardOnTheTowers) {
        // TODO implement here
        
    }

    /**
     * @return
     */
    public String[][] getCardsOnTheTowers() {
        // TODO implement here
        return null; // prevent error
    }

    /**
     * @param diceValues 
     * @return
     */
    public void setDiceValues(Integer[] diceValues) {
        // TODO implement here
        
    }

    /**
     * @return
     */
    public Integer[] getDiceValues() {
        // TODO implement here
       return null; //prevent error 
    }

    /**
     * @param excommunicationsOnTheBoard 
     * @return
     */
    public void setExcommunicationsOnTheBoard(Excommunication[] excommunicationsOnTheBoard) {
        // TODO implement here
        
    }

    /**
     * @return
     */
    public Excommunication[] getExcommunicationsOnTheBoard() {
        // TODO implement here
    	return null; //prevent error 
    }

    /**
     * @param playedLeaderCard 
     * @return
     */
    public void setPlayedLeaderCard(String[] playedLeaderCard) {
        // TODO implement here
        
    }

    /**
     * @return
     */
    public String[] getPlayedLeaderCard() {
        // TODO implement here
    	return null; //prevent error 
    }

    /**
     * @param familyMembersLocation 
     * @return
     */
    public void setFamilyMembersLocation(FamilyMembersLocation familyMembersLocation) {
        // TODO implement here
        
    }

    /**
     * @return
     */
    public FamilyMembersLocation getFamilyMembersLocation() {
        // TODO implement here
    	return null; //prevent error 
    }

    /**
     * @param rankings 
     * @return
     */
    public void setRankings(Rankings rankings) {
        // TODO implement here
        
    }

    /**
     * @return
     */
    public Rankings getRankings() {
        // TODO implement here
    	return null; //prevent error  
    }

}