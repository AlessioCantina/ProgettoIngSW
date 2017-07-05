package it.polimi.LM39.model;

import java.io.Serializable;

import it.polimi.LM39.model.instanteffect.InstantEffect;

/**
 * this class contains costs and effects of a territory card
 */
public class Territory extends Card implements Serializable{

	private static final long serialVersionUID = 1214239985239265580L;

	public Integer activationCost;

    public InstantEffect instantBonuses;

    public InstantEffect activationReward;

}