package it.polimi.LM39.model.excommunicationpermanenteffect;

import java.io.Serializable;

/**
 * 
 */
public class MalusForResourcesCost extends ExcommunicationPermanentEffect implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7725012601883721840L;

	/**
     * At the end of the game, you lose *victoryQty* Victory Point for *resourceQty* wood and stone on your Building Cards’ costs.
     */
    public MalusForResourcesCost() {
    }

    /**
     * 
     */
    public Integer resourceQty;

    /**
     * 
     */
    public Integer victoryQty;

}