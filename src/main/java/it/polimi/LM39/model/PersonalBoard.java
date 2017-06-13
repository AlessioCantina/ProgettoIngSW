package it.polimi.LM39.model;

import java.io.Serializable;
import java.util.ArrayList;

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
        	default: System.out.println("Invalid cardType!");
        		break;
        }
        return null;
    }

    public void setPossessions(ArrayList<Integer> possession, String cardType) {
    	switch(cardType){
        	case "Territory": this.possessedTerritories=possession;
        		break;
        	case "Building": this.possessedBuildings=possession;
        		break;
        	case "Venture": this.possessedVentures=possession;
        		break;
        	case "Character": this.possessedCharacters=possession;
        		break;
        	default: System.out.println("Invalid cardType!");
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