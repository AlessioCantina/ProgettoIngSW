package it.polimi.LM39.model.characterpermanenteffect;

import java.io.Serializable;

import it.polimi.LM39.model.CardResources;

/**
 * Used by Dame,Stonemason
 * permanent effect on a cardType, every card action of this cardtype will be discounted by discount
 * and the card cost will be discounted by resourcesDiscount
 */
public class CardActionResourcesDiscount extends CardActionDiscount implements Serializable {

	private static final long serialVersionUID = 184732623897799072L;

    public CardResources resourcesDiscount;

}