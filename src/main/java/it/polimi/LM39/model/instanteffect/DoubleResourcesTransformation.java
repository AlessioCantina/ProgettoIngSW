package it.polimi.LM39.model.instanteffect;

import java.io.Serializable;

import it.polimi.LM39.model.CardResources;

/**
 * Used by Buildings
 * instant effect, when activated gives to the player resources in return for resources
 * asks the player to choose the effect to activate
 *
 */
public class DoubleResourcesTransformation extends  ResourcesTransformation implements Serializable{

	private static final long serialVersionUID = 7375965728019837730L;
	
	public CardResources resources2;
	
	public CardResources requestedForTransformation2;
}
