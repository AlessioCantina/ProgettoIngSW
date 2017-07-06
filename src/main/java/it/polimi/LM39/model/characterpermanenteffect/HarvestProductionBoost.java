package it.polimi.LM39.model.characterpermanenteffect;

import java.io.Serializable;

/**
 * Used by Farmer,Artisan,Peasant,Scholar
 * permanent effect on harvest or production, every action on actiontype will be increased by actionvalue
 */
public class HarvestProductionBoost extends CharacterPermanentEffect implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -578440549372567931L;

    /**
     * 
     */
    public String actionType;

    /**
     * 
     */
    public Integer actionValue;

}