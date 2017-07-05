package it.polimi.LM39.model;

import java.io.Serializable;

/**
 * this class contains the different kind of resources that a card can give to a player
 */
public class CardResources implements Serializable{

	private static final long serialVersionUID = -286586941734230661L;

	public Integer woods = 0;

    public Integer stones = 0;

    public Integer servants = 0;

    public Integer coins = 0;

    public Integer council = 0;

}