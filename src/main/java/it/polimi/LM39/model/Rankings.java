package it.polimi.LM39.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class is used to handle all the rankings: victory, faith and military
 */
public class Rankings implements Serializable{

	private static final long serialVersionUID = 3477030534526157937L;

	private ArrayList<PlayerRank> victoryRanking = new ArrayList<PlayerRank>();

    private ArrayList<PlayerRank> faithRanking = new ArrayList<PlayerRank>();

    private ArrayList<PlayerRank> militaryRanking = new ArrayList<PlayerRank>();
    
    
    public void setVictoryRanking(ArrayList<PlayerRank> victoryRanking) {
        this.victoryRanking=victoryRanking;
    }

    public ArrayList<PlayerRank> getVictoryRanking() {
        return this.victoryRanking;
    }

    public void setFaithRanking(ArrayList<PlayerRank> faithRanking) {
    	this.faithRanking=faithRanking;
    }

    public ArrayList<PlayerRank> getFaithRanking() {
        return this.faithRanking;
    }

    public void setMilitaryRanking(ArrayList<PlayerRank> militaryRanking) {
    	this.militaryRanking=militaryRanking;
    }

    public ArrayList<PlayerRank> getMilitaryRanking() {
    	return this.militaryRanking;
    }

}