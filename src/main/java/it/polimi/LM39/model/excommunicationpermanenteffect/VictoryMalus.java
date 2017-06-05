package it.polimi.LM39.model.excommunicationpermanenteffect;

import java.io.Serializable;

/**
 * 
 */
public class VictoryMalus extends ExcommunicationPermanentEffect implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8055561913410548276L;

	/**
     * At the end of the game, before the Final Scoring, you lose *victoryMalus* Victory Point for every *victoryQty* Victory Points you have.
     */
    public VictoryMalus() {
    }

    /**
     * 
     */
    public Integer victoryQty;
    
    public Integer victoryMalus;

}