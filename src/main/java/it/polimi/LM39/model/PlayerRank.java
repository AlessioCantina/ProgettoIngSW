package it.polimi.LM39.model;

import java.io.Serializable;

/**
 * this class is used to handle the rankings, it stores the player points in a specific ranking
 */
public class PlayerRank implements Serializable{

	private static final long serialVersionUID = -802117554699921290L;
	
	private Integer points = 0;
	
	public String playerColor;

    
    public Integer getPlayerPoints() {
        return this.points;
    }

    public void setPlayerPoints(Integer points) {
    	//not this.points+=points because it would sum the player points at the end of every move
        this.points=points;
    }

}