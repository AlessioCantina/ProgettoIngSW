package it.polimi.LM39.model;

import java.io.Serializable;

import it.polimi.LM39.model.leaderobject.*;

/**
 * this class contains the period, the effect and the name of a Leader card
 */
public class Leader implements Serializable{

	private static final long serialVersionUID = -1386992865586803229L;

	public LeaderRequestedObjects requestedObjects;

    public Effect effect;

    public String cardName;
  
}