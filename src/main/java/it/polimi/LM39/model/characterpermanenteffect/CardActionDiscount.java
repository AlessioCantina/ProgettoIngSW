package it.polimi.LM39.model.characterpermanenteffect;

import java.io.Serializable;

/**
 * Used by Knight and Warlord
 * permanent effect on a cardType, every card action of this cardtype will be discounted by discount
 */
public class CardActionDiscount extends CharacterPermanentEffect implements Serializable{

	private static final long serialVersionUID = -60160185994280563L;

    public String cardType;
    
    public Integer discount;

}