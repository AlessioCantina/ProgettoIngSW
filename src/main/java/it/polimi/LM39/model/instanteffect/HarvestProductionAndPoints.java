package it.polimi.LM39.model.instanteffect;

import java.io.Serializable;

import it.polimi.LM39.model.CardPoints;

/**
 * Used by characters,ventures,leaders
 * instant effect, when activated it allow the player to do a free harvest or production action of value *actionValue*
 * it also gives the player points
 */
public class HarvestProductionAndPoints extends HarvestProductionAction implements Serializable{

	private static final long serialVersionUID = 5336979987627185390L;
	
	public CardPoints points;
}
