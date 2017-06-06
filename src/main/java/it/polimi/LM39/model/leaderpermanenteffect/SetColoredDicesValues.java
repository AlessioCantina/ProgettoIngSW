package it.polimi.LM39.model.leaderpermanenteffect;

import java.io.Serializable;

/**
 * 
 */
public class SetColoredDicesValues extends LeaderPermanentEffect implements Serializable{

    /**
	 * 	Lucrezia Borgia & Ludovico il Moro
	 */
	private static final long serialVersionUID = -2443162995112661976L;

	/**
     * Default constructor
     */
    public SetColoredDicesValues() {
    }

    /**
     * 
     */
    public Integer diceValue;

    /**
     * 
     */
    //true for boost, false for set
    public Boolean boostOrSet;

}