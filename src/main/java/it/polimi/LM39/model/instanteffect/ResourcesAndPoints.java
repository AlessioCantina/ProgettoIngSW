package it.polimi.LM39.model.instanteffect;

import java.io.Serializable;

import it.polimi.LM39.model.CardPoints;

/**
 * instant effect, when activated it gives to the player *resources* resources and *points* points
 */
public class ResourcesAndPoints extends Resources implements Serializable{

	private static final long serialVersionUID = 816625272721317948L;

    public CardPoints points;

}