package it.polimi.LM39.model.instanteffect;

import java.io.Serializable;

/**
 * Used by Ventures
 * instant effect, when activated it gives *victoryqty* victory points based on cards of type *cardType*
 */
public class VictoryForCard extends InstantEffect implements Serializable{

	private static final long serialVersionUID = -2663911291693847987L;


    public String cardType;


    public Integer victoryQty;

}