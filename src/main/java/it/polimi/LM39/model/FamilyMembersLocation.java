package it.polimi.LM39.model;

import java.io.Serializable;
import java.util.ArrayList;
import it.polimi.LM39.exception.*;


public class FamilyMembersLocation implements Serializable{

	private static final long serialVersionUID = -7342514104269107127L;

	private FamilyMember[][] familyMembersOnTheTowers= new FamilyMember[4][4];

    private FamilyMember[] familyMembersAtTheMarket = new FamilyMember[4];

    private ArrayList<FamilyMember> familyMembersAtTheProduction = new ArrayList<FamilyMember>();

    private ArrayList<FamilyMember> familyMembersAtTheHarvest = new ArrayList<FamilyMember>();
    
    private ArrayList<FamilyMember> familyMembersAtTheCouncilPalace = new ArrayList<FamilyMember>();


    public void setFamilyMemberAtTheCouncilPalace(FamilyMember familyMember) {
        //set a FamilyMember at the Council Palace
    	this.familyMembersAtTheCouncilPalace.add(familyMember);
    }
    
    public void setFamilyMembersAtTheCouncilPalace(ArrayList<FamilyMember> familyMembers) {
    	this.familyMembersAtTheCouncilPalace = familyMembers;
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

    public void setFamilyMemberOnProductionOrHarvest(FamilyMember familyMember, String actionType) throws InvalidActionTypeException{
    	if(actionType.compareToIgnoreCase("Production") == 0)
    		this.familyMembersAtTheProduction.add(familyMember);
    	else 
    		if (actionType.compareToIgnoreCase("Harvest") == 0)
    			this.familyMembersAtTheHarvest.add(familyMember);
    		else
    			throw new InvalidActionTypeException("The actionType is invalid"); // invalid action
        
    }
    public void changeFamilyMemberOnProductionOrHarvest(ArrayList<FamilyMember> familyMemberOnProductionOrHarvest, String actionType) throws InvalidActionTypeException{
    	if(actionType.compareToIgnoreCase("Production") == 0)
    		this.familyMembersAtTheProduction = familyMemberOnProductionOrHarvest;
    	else 
    		if (actionType.compareToIgnoreCase("Harvest") == 0)
    			this.familyMembersAtTheHarvest = familyMemberOnProductionOrHarvest;
    		else
    			throw new InvalidActionTypeException("The actionType is invalid"); // invalid action
        
    }
 
    public ArrayList<FamilyMember> getFamilyMembersOnProductionOrHarvest(String actionType) throws InvalidActionTypeException{
    	if(actionType.compareToIgnoreCase("Production") == 0)
    		return this.familyMembersAtTheProduction;
    	else 
    		if (actionType.compareToIgnoreCase("Harvest") == 0)
    			return this.familyMembersAtTheHarvest;
    		else
    			throw new InvalidActionTypeException("The actionType is invalid"); // invalid action
    }

}