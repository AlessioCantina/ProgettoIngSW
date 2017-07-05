package it.polimi.LM39.model;

import java.io.Serializable;

/**
 * this class contains the different kind of points that a card can give to a player
 */
public class CardPoints implements Serializable{

	private static final long serialVersionUID = -433202169653017493L;

	public Integer victory = 0;

    public Integer faith = 0;

    public Integer military = 0;

}