package it.polimi.LM39.model;

import it.polimi.LM39.exception.NotEnoughPoints;
import it.polimi.LM39.exception.NotEnoughResources;

public class PlayerPoints {

    private Integer faith;

    private Integer military;

    private Integer victory;

    private Integer finalVictory;


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

    public void setFaith(Integer qtyFaith) throws NotEnoughPoints {
    	if(this.faith+qtyFaith>=0)
    		this.faith+=qtyFaith;
    	else throw new NotEnoughPoints ("Not enough faith points!");
    }

    public void setMilitary(Integer qtyMilitary) throws NotEnoughPoints {
    	if(this.military+qtyMilitary>=0)
    		this.military+=qtyMilitary;
    	else throw new NotEnoughPoints ("Not enough military points!");
    }

    public void setVictory(Integer qtyVictory) {
    	this.victory+=qtyVictory;
    }

    public void setFinalVictory(Integer qtyFinalVictory) {
    	this.finalVictory+=qtyFinalVictory;
    }

}