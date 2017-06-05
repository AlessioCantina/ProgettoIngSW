package it.polimi.LM39.model.characterpermanenteffect;

import java.io.Serializable;

import it.polimi.LM39.model.CardResources;

/**
 * 
 */
public class CardActionResourcesDiscount extends CardActionDiscount implements Serializable {

	private static final long serialVersionUID = 184732623897799072L;

	/**
     * Default constructor
     */
    public CardActionResourcesDiscount() {
    }

    /**
     * 
     */
    public CardResources resourcesDiscount;

}