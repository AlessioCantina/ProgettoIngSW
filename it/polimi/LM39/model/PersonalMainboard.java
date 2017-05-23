package it.polimi.LM39.model;


public class PersonalMainboard {

    public String playerName;

    private MainBoard playerMainBoard;

    
    public MainBoard getPlayerMainBoard() {
        return this.playerMainBoard;
    }

    public void setPlayerMainBoard(MainBoard mainBoard) {
        this.playerMainBoard = mainBoard;
        
    }

}