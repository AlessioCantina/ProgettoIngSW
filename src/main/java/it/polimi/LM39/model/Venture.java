package it.polimi.LM39.model;

import java.io.Serializable;

import it.polimi.LM39.model.instanteffect.*;

/**
 * this class contains costs and effects of a venture card
 */
public class Venture extends Card implements Serializable{

	private static final long serialVersionUID = 337667726119442570L;

	public CardResources costResources;

    public Integer neededMilitary;

    public Integer costMilitary;

    //victory points given only at the end of the game
    public Integer finalVictory;

    public InstantEffect instant;
	
}