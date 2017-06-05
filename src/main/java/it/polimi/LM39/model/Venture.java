package it.polimi.LM39.model;

import java.io.Serializable;

import it.polimi.LM39.model.instanteffect.*;

public class Venture extends Card implements Serializable{

	private static final long serialVersionUID = 337667726119442570L;

	public CardResources costResources;

    public Integer neededMilitary;

    public Integer costMilitary;

    public Integer finalVictory;

    public InstantEffect instant;
	
}