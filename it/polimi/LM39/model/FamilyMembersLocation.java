package it.polimi.LM39.model;

import java.util.ArrayList;
import it.polimi.LM39.exception.*;


public class FamilyMembersLocation {

    private FamilyMember[][] familyMembersOnTheTowers= new FamilyMember[4][4];

    private FamilyMember[] familyMembersAtTheMarket = new FamilyMember[4];

    private ArrayList<FamilyMember> familyMembersAtTheProduction = new ArrayList<FamilyMember>();

    private ArrayList<FamilyMember> familyMembersAtTheHarvest = new ArrayList<FamilyMember>();
    
    private ArrayList<FamilyMember> familyMembersAtTheCouncilPalace = new ArrayList<FamilyMember>();


    public void setFamilyMembersAtTheCouncilPalace(FamilyMember familyMember) {
        //set a FamilyMember at the Council Palace
    	this.familyMembersAtTheCouncilPalace.add(familyMember);
    }
    
    public ArrayList<FamilyMember> getFamilyMembersAtTheCouncilPalace() {
        return this.familyMembersAtTheCouncilPalace;
    }
    
    public void setFamilyMemberOnTheTower(FamilyMember familyMember, Integer positionX, Integer positionY) {
       this.familyMembersOnTheTowers[positionX][positionY]=familyMember;
        
    }

    public FamilyMember[][] getFamilyMembersOnTheTowers() {
        return this.familyMembersOnTheTowers;
    }

    public void setFamilyMemberOnTheMarket(FamilyMember familyMember, Integer position) {
    	//set a FamilyMember in a specific position of the Market
    	this.familyMembersAtTheMarket[position]=familyMember;
    }

    public FamilyMember[] getFamilyMembersOnTheMarket() {
        return this.familyMembersAtTheMarket; 
    }

    public void setFamilyMemberOnProductionOrHarvest(FamilyMember familyMember, String actionType) throws InvalidActionType{
    	if(actionType.equals("Production"))
    		this.familyMembersAtTheProduction.add(familyMember);
    	else 
    		if (actionType.equals("Harvest"))
    			this.familyMembersAtTheHarvest.add(familyMember);
    		else
    			throw new InvalidActionType("The actionType is invalid"); // invalid action
        
    }
 
    public ArrayList<FamilyMember> getFamilyMembersOnProductionOrHarvest(String actionType) throws InvalidActionType{
    	if(actionType.equals("Production"))
    		return this.familyMembersAtTheProduction;
    	else 
    		if (actionType.equals("Harvest"))
    			return this.familyMembersAtTheHarvest;
    		else
    			throw new InvalidActionType("The actionType is invalid"); // invalid action
    }

}