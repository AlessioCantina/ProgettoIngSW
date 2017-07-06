package it.polimi.LM39.model.excommunicationpermanenteffect;

import java.io.Serializable;

/**
 * Used by 1 excommunication
 * At the end of the game, you lose *victoryQty* Victory Point for *resourceQty* resource (wood, stone, coin, servant) in your supply on your Personal Board.
 */
public class MalusForResources extends ExcommunicationPermanentEffect implements Serializable{


	private static final long serialVersionUID = -2517806848264989346L;


    public Integer resourceQty;
    
    
    public Integer victoryQty;

}