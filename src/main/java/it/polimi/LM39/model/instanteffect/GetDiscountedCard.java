package it.polimi.LM39.model.instanteffect;

import java.io.Serializable;

import it.polimi.LM39.model.CardResources;

/**
 * Used by character (Dame)
 * instant effect, when activated it allow the player to do a free action (value: *cardValue*) to take a card of *cardtype*
 * it also apply a discount on the card's cost
 */
public class GetDiscountedCard extends GetCard  implements Serializable {

	
	private static final long serialVersionUID = 2686838379557586166L;

	
    public CardResources cardDiscount;

}