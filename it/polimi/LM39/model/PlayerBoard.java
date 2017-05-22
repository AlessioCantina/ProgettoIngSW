package it.polimi.LM39.model;


/**
 * 
 */
public class PlayerBoard {

    /**
     * Default constructor
     */
    public PlayerBoard() {
    }

    private String[] possessedTerritories;

    private String[] possessedBuildings;

    private String[] possessedVentures;

    private String[] possessedCharacters;



    public String[] getPossessions(String cardType) {
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

    public void setPossession(String[] possession, String cardType) {
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