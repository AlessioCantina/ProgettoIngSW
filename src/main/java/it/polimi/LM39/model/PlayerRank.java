package it.polimi.LM39.model;

import java.io.Serializable;

public class PlayerRank implements Serializable{

	private static final long serialVersionUID = -802117554699921290L;
	
	private Integer points = 0;
	
	public String playerColor;

    
    public Integer getPlayerPoints() {
        return this.points;
    }

    public void setPlayerPoints(Integer points) {
        this.points+=points;
    }

}