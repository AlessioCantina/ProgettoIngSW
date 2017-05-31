package it.polimi.LM39.model;

import it.polimi.LM39.exception.NotEnoughResourcesException;

public class PlayerResources {

    private Integer woods;

    private Integer stones;

    private Integer coins;

    private Integer servants;
    
    private Integer council;
    


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