package it.polimi.LM39.model.instanteffect;

import java.io.Serializable;

/**
 * Used by characters,ventures
 * instant effect, when activated it allow the player to do a free action (value: *cardValue*) to take a card of *cardtype*
 */
public class GetCard extends InstantEffect implements Serializable {

	private static final long serialVersionUID = 1388078448991697253L;

    public String cardType;

    public Integer cardValue;

}