package it.polimi.LM39.model.instanteffect;

import java.io.Serializable;

/**
 * Used by Buildings
 * instant effect, when activated it gives *coinqty* coins based on cards of type *cardType*
 */
public class CoinForCard extends InstantEffect implements Serializable{


	private static final long serialVersionUID = 6462073064640869335L;


    public String cardType;


    public Integer coinQty;

}