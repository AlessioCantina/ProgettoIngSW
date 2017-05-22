package it.polimi.LM39.model;


/**
 * 
 */
public class FamilyMembersLocation {

    /**
     * Default constructor
     */
    public FamilyMembersLocation() {
    }

    /**
     * 
     */
    private FamilyMember[][] familyMembersOnTheTowers= new FamilyMember[4][4];

    /**
     * 
     */
    private FamilyMember familyMembersAtTheMarket;

    /**
     * 
     */
    private FamilyMember familyMembersAtTheProduction;

    /**
     * 
     */
    private FamilyMember familyMembersAtTheHarvest;
    
    private FamilyMember[] familyMembersAtTheCouncilPalace;



    /**
     * @param familyMember 
     * @param positionX 
     * @param positionY 
     * @return
     */
    public void setFamilyMembersAtTheCouncilPalace(FamilyMember familyMember, Integer position) {
        // TODO implement here
        
    }
    
    public FamilyMember[] getFamilyMembersAtTheCouncilPalace() {
        // TODO implement here
        return null; //prevent error
    }
    
    public void setFamilyMemberOnTheTower(FamilyMember familyMember, Integer positionX, Integer positionY) {
        // TODO implement here
        
    }

    /**
     * @return
     */
    public FamilyMember[][] getFamilyMembersOnTheTowers() {
        // TODO implement here
        return null; //prevent error
    }

    /**
     * @param familyMember 
     * @param Integer position 
     * @return
     */
    public void setFamilyMemberOnTheMarket(FamilyMember familyMember, Integer position) {
        // TODO implement here
        
    }

    /**
     * @return
     */
    public FamilyMember[] getFamilyMembersOnTheMarket() {
        // TODO implement here
        return null; //prevent error
    }

    /**
     * @param familyMember 
     * @param ActionType 
     * @return
     */
    public void setFamilyMemberOnProductionOrHarvest(FamilyMember familyMember, String ActionType) {
        // TODO implement here
        
    }

    /**
     * @param actionType 
     * @return
     */
    public FamilyMember[] getFamilyMembersOnProductionOrHarvest(String actionType) {
        // TODO implement here
        return null; //prevent error
    }

}