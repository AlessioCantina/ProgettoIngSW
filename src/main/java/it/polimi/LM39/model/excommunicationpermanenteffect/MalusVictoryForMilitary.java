package it.polimi.LM39.model.excommunicationpermanenteffect;

import java.io.Serializable;

/**
 * Used by 1 excommunication
 * At the end of the game, you lose *victoryQty* Victory Point for *militaryQty* Military Point you have.
 */
public class MalusVictoryForMilitary extends ExcommunicationPermanentEffect implements Serializable{

	
	private static final long serialVersionUID = -2543251755879921743L;


    public Integer victoryQty;


    public Integer militaryQty;

}