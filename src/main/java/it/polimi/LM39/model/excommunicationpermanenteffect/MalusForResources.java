package it.polimi.LM39.model.excommunicationpermanenteffect;

import java.io.Serializable;

/**
 * 
 */
public class MalusForResources extends ExcommunicationPermanentEffect implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2517806848264989346L;


	/*
	 * At the end of the game, you lose *victoryQty* Victory Point for *resourceQty* resource (wood, stone, coin, servant) in your supply on your Personal Board.
	 */
    /**
     * Default constructor
     */
    public MalusForResources() {
    }

    /**
     * 
     */
    
    public Integer resourceQty;
    
    
    public Integer victoryQty;

}