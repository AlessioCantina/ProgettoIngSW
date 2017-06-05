package it.polimi.LM39.model;

import java.io.Serializable;

public class FamilyMemberRank extends FamilyMember implements Serializable{

	private static final long serialVersionUID = -802117554699921290L;
	
	private Integer position;

    
    public Integer getPosition() {
        return this.position;
    }

    public void setFamilyMemberRank(Integer position) {
        this.position=position;
    }

}