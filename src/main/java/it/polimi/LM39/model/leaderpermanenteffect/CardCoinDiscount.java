package it.polimi.LM39.model.leaderpermanenteffect;

import java.io.Serializable;

/**
 * Permanent effect used by Pico della Mirandola
 * Permanent discount on card's cost
 */
public class CardCoinDiscount extends LeaderPermanentEffect implements Serializable{

	private static final long serialVersionUID = 2078060839475915283L;

    public Integer coinQty;

}