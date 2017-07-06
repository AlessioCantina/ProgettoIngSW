package it.polimi.LM39.model.instanteffect;

import java.io.Serializable;

/**
 * Used by characters,ventures,leaders
 * instant effect, when activated it allow the player to do a free harvest or production action of value *actionValue*
 */
public class HarvestProductionAction extends InstantEffect implements Serializable {


	private static final long serialVersionUID = 4465637740277191907L;


    public Integer actionValue;


    public String actionType;

}