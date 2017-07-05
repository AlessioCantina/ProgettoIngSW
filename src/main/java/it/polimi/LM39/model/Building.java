package it.polimi.LM39.model;

import java.io.Serializable;

import it.polimi.LM39.model.instanteffect.*;

/**
 * this class contains costs and effects of a building card
 */
public class Building extends Card implements Serializable {

	private static final long serialVersionUID = 7421456678099815797L;

	public Integer activationCost;

    public CardResources costResources;

    public CardPoints instantBonuses;

    public InstantEffect activationEffect;
	
}