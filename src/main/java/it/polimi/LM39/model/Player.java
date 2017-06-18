package it.polimi.LM39.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable{

	private static final long serialVersionUID = 216892385150280280L;

	public String nickname = "";

    public String playerColor = "";

    public PersonalBoard personalBoard = new PersonalBoard();

    public PlayerPoints points = new PlayerPoints();

    public PlayerResources resources = new PlayerResources();

    private ArrayList<Integer> excommunications = new ArrayList<Integer>();

    public MainBoard personalMainBoard = new MainBoard(); // this is a modified version of the mainboard, should be updated every time the board gets modified

    private ArrayList<String> playerPlayedLeaderCards = new ArrayList<String>();
    
    private ArrayList<String> playedFamilyMembers = new ArrayList<String>();
    
    public Integer copiedLeaderCard = -1;

    public DecoratorHandler decoratorHandler = new DecoratorHandler();
    
    

    public void setPlayerPlayedLeaderCards(String cardName) {
    	playerPlayedLeaderCards.add(cardName);
    }

    public ArrayList<String> getPlayerPlayedLeaderCards() {
        return this.playerPlayedLeaderCards;
    }

    public void setExcommunications(Integer excommunicationNumber) {
        this.excommunications.add(excommunicationNumber);
    }

    public ArrayList<Integer> getExcommunications() {
    	return this.excommunications;
    }

	public ArrayList<String> getPlayedFamilyMembers() {
		return playedFamilyMembers;
	}

	public void setPlayedFamilyMember(String playedFamilyMember) {
		this.playedFamilyMembers.add(playedFamilyMember);
	}
	
	public void setPlayedFamilyMembers(ArrayList<String> playedFamilyMembers) {
		this.playedFamilyMembers = playedFamilyMembers;
	}

	

}