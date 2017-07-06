package it.polimi.LM39.model.leaderpermanenteffect;

import java.io.Serializable;

/**
 * Used by Lucrezia Borgia & Ludovico il Moro
 * this effect changes the value of family members by setting them (Lucrezia Borgia) or increasing them (Ludovico il Moro)
 */
public class SetColoredDicesValues extends LeaderPermanentEffect implements Serializable{

	private static final long serialVersionUID = -2443162995112661976L;

    public Integer diceValue;

    //true for boost, false for set
    public Boolean boostOrSet;

}