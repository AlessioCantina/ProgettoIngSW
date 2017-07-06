package it.polimi.LM39.model.instanteffect;

import java.io.Serializable;

/**
 * Used by Characters
 * instant effect, when activated it gives *victoryqty* victory points based on player's military points
 */
public class VictoryForMilitary extends InstantEffect implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 733272110042908236L;

	/**
     * 
     */
    public Integer victoryQty;

    /**
     * 
     */
    public Integer militaryQty;

}