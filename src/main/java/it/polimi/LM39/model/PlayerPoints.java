package it.polimi.LM39.model;


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

    public void setFaith(Integer qtyFaith) {
    	this.faith=qtyFaith;
    }

    public void setMilitary(Integer qtyMilitary) {
    	this.military=qtyMilitary;
    }

    public void setVictory(Integer qtyVictory) {
    	this.victory=qtyVictory;
    }

    public void setFinalVictory(Integer qtyFinalVictory) {
    	this.finalVictory=qtyFinalVictory;
    }

}