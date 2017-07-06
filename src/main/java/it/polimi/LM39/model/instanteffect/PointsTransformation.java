package it.polimi.LM39.model.instanteffect;

import java.io.Serializable;

import it.polimi.LM39.model.CardResources;
/**
 * Used by Buildings
 * instant effect, when activated gives to the player points in return for resources
 *
 **/
public class PointsTransformation extends Points implements Serializable{

	private static final long serialVersionUID = 4804621478956920013L;
	
	public CardResources requestedForTransformation;
}
