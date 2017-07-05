package it.polimi.LM39.model;

import java.io.Serializable;

/**
 * this is the abstract class that all specific card types extend
 */
public abstract class Card implements Serializable{

	private static final long serialVersionUID = -6145819095684362653L;

	public String cardName;

    public Integer period;

}