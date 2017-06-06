package it.polimi.LM39.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable{

	private static final long serialVersionUID = 216892385150280280L;

	public String nickname;

    public String playerColor;

    public PersonalBoard personalBoard;

    public PlayerPoints points;

    public PlayerResources resources;

    private ArrayList<Excommunication> excommunications = new ArrayList<Excommunication>();

    public MainBoard personalMainBoard; // this is a modified version of the mainboard, should be updated every time the board gets modified

    private ArrayList<String> playerPlayedLeaderCards = new ArrayList<String>();
    
    public Integer copiedLeaderCard;

    public DecoratorHandler decoratorHandler = new DecoratorHandler();
    

    public void setPlayerPlayedLeaderCards(String cardName) {
    	playerPlayedLeaderCards.add(cardName);
    }

    public ArrayList<String> getPlayerPlayedLeaderCards() {
        return this.playerPlayedLeaderCards;
    }

    public void setExcommunications(ArrayList<Excommunication> excommunications) {
        this.excommunications=excommunications;
    }

    public ArrayList<Excommunication> getExcommunications() {
    	return this.excommunications;
    }

}