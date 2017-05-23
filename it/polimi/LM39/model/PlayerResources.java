package it.polimi.LM39.model;


public class PlayerResources {

    private Integer woods;

    private Integer stones;

    private Integer coins;

    private Integer servants;


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

    public void setWoods(Integer qtywoods) {
    	this.woods=qtywoods;
    }

    public void setStones(Integer qtystones) {
        this.stones=qtystones;
     }

    public void setCoins(Integer qtycoins) {
        this.coins=qtycoins;
    }

    public void setServants(Integer qtyservants) {
        this.servants=qtyservants;
    }

}