package it.polimi.LM39.model.instanteffect;

import java.io.Serializable;

import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.CardResources;

/**
 * Used by Buildings
 * instant effect, when activated gives to the player points in return for resources
 * asks the player to choose the effect to activate
 */
public class DoublePointsTransformation extends PointsTransformation implements Serializable {

	private static final long serialVersionUID = -5823505132051943232L;
	
	public CardPoints points2;
	
	public CardResources requestedForTransformation2;
}
