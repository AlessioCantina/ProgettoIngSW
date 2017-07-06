package it.polimi.LM39.model.instanteffect;

import java.io.Serializable;

import it.polimi.LM39.model.CardResources;
/**
 * Used by Buildings
 * instant effect, when activated gives to the player resources in return for resources
 *
 **/
public class ResourcesTransformation extends Resources implements Serializable{

	
	private static final long serialVersionUID = 8363276099450175246L;

	public CardResources requestedForTransformation;
	
}
