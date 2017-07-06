package it.polimi.LM39.model.instanteffect;

import java.io.Serializable;

import it.polimi.LM39.model.CardResources;

/**
 * instant effect, when activated it gives to the player *resources* resources
 */
public class Resources extends InstantEffect implements Serializable{

	
	private static final long serialVersionUID = -8360052407552093892L;

	
    public CardResources resources;

}