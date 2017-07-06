package it.polimi.LM39.model.instanteffect;

import it.polimi.LM39.model.CardPoints;

/**
 * Used by characters
 * instant effect, when activated it allow the player to do a free action (value: *cardValue*) to take a card of *cardtype*
 * it also gives points to the player
 */
public class GetCardAndPoints extends GetCard{

	private static final long serialVersionUID = -8637610397523322649L;
	
	public CardPoints points;
}
