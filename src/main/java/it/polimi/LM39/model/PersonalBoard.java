package it.polimi.LM39.model;

import java.util.ArrayList;

public class PersonalBoard {

	ArrayList<Integer> possessedTerritories = new ArrayList<Integer>();

	ArrayList<Integer> possessedBuildings = new ArrayList<Integer>();

	ArrayList<Integer> possessedVentures = new ArrayList<Integer>();

	ArrayList<Integer> possessedCharacters = new ArrayList<Integer>();
    
    public ActionBonus[] territoryBonuses = new ActionBonus[6]; //the bonuses are loaded from file

    public ActionBonus[] characterBonuses = new ActionBonus[6]; //the bonuses are loaded from file
    
    public ActionBonus[][] personalBonusTile = new ActionBonus[2][2]; //the bonuses are loaded from file

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

}