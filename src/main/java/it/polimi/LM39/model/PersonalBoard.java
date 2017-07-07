package it.polimi.LM39.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class contains all the attributes of the player PersonalBoard
 */
public class PersonalBoard implements Serializable{

	private static final long serialVersionUID = -8268917967400496440L;

	private ArrayList<Integer> possessedTerritories = new ArrayList<Integer>();

	private ArrayList<Integer> possessedBuildings = new ArrayList<Integer>();

	private ArrayList<Integer> possessedVentures = new ArrayList<Integer>();

	private ArrayList<Integer> possessedCharacters = new ArrayList<Integer>();
    
	private ArrayList<String> possessedLeaders = new ArrayList<String>();
    
	public ActionBonus[] territoryBonuses = new ActionBonus[6]; //the bonuses are loaded from file

    public ActionBonus[] characterBonuses = new ActionBonus[6]; //the bonuses are loaded from file
    
    public PersonalBonusTile personalBonusTile = new PersonalBonusTile();

    public ArrayList<Integer> getPossessions(String cardType) {
        switch(cardType){
        //break is not needed in every case because return acts like a break
        	case "Territory": return this.possessedTerritories;
        	case "Building": return this.possessedBuildings;
        	case "Venture": return this.possessedVentures;
        	case "Character": return this.possessedCharacters;
        	default:
        		break;
        }
        return new ArrayList<>();
    }

    public void setPossessions(Integer possession, String cardType) {
    	switch(cardType){
        	case "Territory": this.possessedTerritories.add(possession);
        		break;
        	case "Building": this.possessedBuildings.add(possession);
        		break;
        	case "Venture": this.possessedVentures.add(possession);
        		break;
        	case "Character": this.possessedCharacters.add(possession);
        		break;
        	default:
        		break;
        }
    }

	public ArrayList<String> getPossessedLeaders() {
		return possessedLeaders;
	}

	public void setLeader(String leader) {
		this.possessedLeaders.add(leader);
	}
	
	public void setPossessedLeaders(ArrayList<String> possessedLeaders) {
		this.possessedLeaders = possessedLeaders;
	}
	

}