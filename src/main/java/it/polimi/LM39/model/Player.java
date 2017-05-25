package it.polimi.LM39.model;


public class Player {

   public String nickname;

   public String playerColor;

    public PersonalBoard personalBoard;

    public PlayerPoints points;

    public PlayerResources resources;

    private Excommunication[] excommunications;

    public MainBoard personalMainBoard; // this is a modified version of the mainboard, should be updated every time the board gets modified

    private String[] playerPlayedLeaderCards;
    //todo

    

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