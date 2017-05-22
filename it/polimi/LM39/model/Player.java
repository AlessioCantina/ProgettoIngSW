package it.polimi.LM39.model;

import java.util.*;

/**
 * 
 */
public class Player {

    /**
     * Default constructor
     */
    public Player() {
    }
    
    private String nickname;

    private String playerColor;

    private PlayerBoard possessions;

    private PlayerPoints points;

    private PlayerResources resources;

    private Excommunication[] excommunications;

    private MainBoard personalMainBoard;

    private String[] playerPlayedLeaderCards;
    //todo

    
    public void setNicknameAndColor(String nickname, String color) {
        this.nickname = nickname;
        this.playerColor = color;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getColor() {
        return this.playerColor;
    }

    public void setPlayerBoard(PlayerBoard playerBoard) {
       this.possessions=playerBoard;
    }

    public PlayerBoard getPlayerBoard() {
    	return this.possessions;
    }

    public void setPoints(PlayerPoints points) {
        this.points=points;
    }

    public PlayerPoints getPoints() {
    	return this.points; 
    }

    public void setResources(PlayerResources resources) {
       this.resources=resources;    
    }

    public PlayerResources getResources() {
    	return this.resources;
    }

    public void setPersonalMainBoard(MainBoard personalMainBoard) {
       this.personalMainBoard=personalMainBoard;
    }
    
    public MainBoard getPersonalMainBoard() {
    	return this.personalMainBoard;
    }

    public void setPlayerPlayedLeaderCards(String cardName) {
    	// TODO implement here
    }

    public String[] getPlayerPlayedLeaderCards() {
        return this.playerPlayedLeaderCards;
    }

    public void setExcommunications(Excommunication[] excommunications) {
        this.excommunications=excommunications;
    }

    public Excommunication[] getExcommunications() {
    	return this.excommunications;
    }

}