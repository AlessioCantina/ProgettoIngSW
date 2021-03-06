package it.polimi.LM39.model.instanteffect;

import java.io.Serializable;

/**
 * Used by Federico da Montefeltro
 * instant effect, when activated allows the player to change the value of a family member
 */
public class SetFamilyMember extends InstantEffect implements Serializable{

	
	private static final long serialVersionUID = 6741147375352529790L;

	
    public Integer familyMemberValue;

}