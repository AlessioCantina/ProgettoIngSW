package it.polimi.LM39.model;

/**
 * 
 */
public class FamilyMemberRank extends FamilyMember {

    /**
     * Default constructor
     */
    public FamilyMemberRank() {
    }

    private Integer position;

    
    public Integer getPosition() {
        return this.position;
    }

    public void setFamilyMemberRank(Integer position) {
        this.position=position;
    }

}