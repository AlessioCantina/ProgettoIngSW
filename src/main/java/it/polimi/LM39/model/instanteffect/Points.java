package it.polimi.LM39.model.instanteffect;

import java.io.Serializable;

import it.polimi.LM39.model.CardPoints;

/**
 * instant effect, when activated it gives to the player *points* points
 */
public class Points extends InstantEffect implements Serializable{

	private static final long serialVersionUID = -1382799360026464544L;

    public CardPoints points;

}