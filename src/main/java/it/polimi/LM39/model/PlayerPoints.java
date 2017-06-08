package it.polimi.LM39.model;

import java.io.Serializable;

import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;

public class PlayerPoints implements Serializable{

	private static final long serialVersionUID = 238408352644162214L;

	private Integer faith = 0;

    private Integer military = 0;

    private Integer victory = 0;

    private Integer finalVictory = 0;


    public Integer getFaith() {
    	return this.faith;
    }

    public Integer getMilitary() {
    	return this.military;
    }

    public Integer getVictory() {
    	return this.victory;
    }
    public Integer getFinalVictory() {
    	return this.finalVictory;
    }

    public void setFaith(Integer qtyFaith) throws NotEnoughPointsException {
    	if(this.faith+qtyFaith>=0)
    		this.faith+=qtyFaith;
    	else throw new NotEnoughPointsException ("Not enough faith points!");
    }

    public void setMilitary(Integer qtyMilitary) throws NotEnoughPointsException {
    	if(this.military+qtyMilitary>=0)
    		this.military+=qtyMilitary;
    	else throw new NotEnoughPointsException ("Not enough military points!");
    }

    public void setVictory(Integer qtyVictory) {
    	this.victory+=qtyVictory;
    }

    public void setFinalVictory(Integer qtyFinalVictory) {
    	this.finalVictory+=qtyFinalVictory;
    }

}