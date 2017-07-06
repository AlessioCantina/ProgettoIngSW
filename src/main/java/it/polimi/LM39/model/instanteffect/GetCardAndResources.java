package it.polimi.LM39.model.instanteffect;

import java.io.Serializable;

import it.polimi.LM39.model.CardResources;

/**
 * Used by characters
 * instant effect, when activated it allow the player to do a free action (value: *cardValue*) to take a card of *cardtype*
 * it also gives resources to the player
 */
public class GetCardAndResources extends GetCard implements Serializable{

	private static final long serialVersionUID = 986305471723225027L;

	public CardResources resources;
}
