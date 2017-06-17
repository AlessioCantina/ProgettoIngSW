package it.polimi.LM39.model;

import java.io.Serializable;

import it.polimi.LM39.exception.NotEnoughResourcesException;

public class PlayerResources implements Serializable{

	private static final long serialVersionUID = -4338651550708606365L;

	private Integer woods = 0;

    private Integer stones = 0;

    private Integer coins = 0;

    private Integer servants = 0;
    
    private Integer council = 0;
    


    public Integer getWoods() {
    	return this.woods;
    }

    public Integer getStones() {
    	return this.stones;
    }

    public Integer getCoins() {
    	return this.coins;
    }

    public Integer getServants() {
    	return this.servants;
    }
    
    public Integer getCouncil() {
    	return this.council;
    }

    public void setWoods(Integer qtywoods) throws NotEnoughResourcesException{
    	if(this.woods+qtywoods>=0)
    		this.woods+=qtywoods;
    	else throw new NotEnoughResourcesException ("Not enough woods!");
    }

    public void setStones(Integer qtystones) throws NotEnoughResourcesException {
    	if(this.stones+qtystones>=0)
    		this.stones+=qtystones;
    	else throw new NotEnoughResourcesException ("Not enough stones!");
     }

    public void setCoins(Integer qtycoins) throws NotEnoughResourcesException{
    	System.out.println("AVAILABLE COINS: " + coins + "COINS TO PAY: " + qtycoins);
    	if(this.coins+qtycoins>=0)
    		this.coins+=qtycoins;
    	else throw new NotEnoughResourcesException ("Not enough coins!");
    }

    public void setServants(Integer qtyservants) throws NotEnoughResourcesException{
    	if(this.servants+qtyservants>=0)
    		this.servants+=qtyservants;
    	else throw new NotEnoughResourcesException ("Not enough servants!");
    }
    
    public void setCouncil (Integer qtycouncil) {
        this.council+=qtycouncil;
    }

}