package it.polimi.LM39.model;

import java.util.ArrayList;

/**
 * 
 */
public class FamilyMembersLocation {

    private FamilyMember[][] familyMembersOnTheTowers= new FamilyMember[4][4];

    private FamilyMember[] familyMembersAtTheMarket = new FamilyMember[4];

    private ArrayList<FamilyMember> familyMembersAtTheProduction = new ArrayList<FamilyMember>();

    private ArrayList<FamilyMember> familyMembersAtTheHarvest = new ArrayList<FamilyMember>();
    
    private ArrayList<FamilyMember> familyMembersAtTheCouncilPalace = new ArrayList<FamilyMember>();


    public void setFamilyMembersAtTheCouncilPalace(FamilyMember familyMember) {
        //set a FamilyMember at the Council Palace
    	// TODO implement here
    }
    
    public ArrayList<FamilyMember> getFamilyMembersAtTheCouncilPalace() {
        return this.familyMembersAtTheCouncilPalace;
    }
    
    public void setFamilyMemberOnTheTower(FamilyMember familyMember, Integer positionX, Integer positionY) {
        // TODO implement here
        
    }

    public FamilyMember[][] getFamilyMembersOnTheTowers() {
        return this.familyMembersOnTheTowers;
    }

    public void setFamilyMemberOnTheMarket(FamilyMember familyMember, Integer position) {
    	//set a FamilyMember in a specific position of the Market
    	// TODO implement here
    }

    public FamilyMember[] getFamilyMembersOnTheMarket() {
        return this.familyMembersAtTheMarket; 
    }

    public void setFamilyMemberOnProductionOrHarvest(FamilyMember familyMember, String ActionType) {
        // TODO implement here
        
    }


    public ArrayList<FamilyMember> getFamilyMembersOnProductionOrHarvest(String actionType) {
    	if(actionType.equals("Production"))
    		return this.familyMembersAtTheProduction;
    	else 
    		if (actionType.equals("Harvest"))
    			return this.familyMembersAtTheHarvest;
    		else
    			return null; // invalid action
    }

}