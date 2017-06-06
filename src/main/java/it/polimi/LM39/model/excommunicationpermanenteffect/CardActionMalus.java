package it.polimi.LM39.model.excommunicationpermanenteffect;

import java.io.Serializable;

/**
 * 
 */
public class CardActionMalus extends ExcommunicationPermanentEffect implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1592299992871687996L;

	/*
	 * Each time you take a *cardType* Card (through the action space or as a Card effect), your action receives a *malus* reduction.
	 */
    /**
     * Default constructor
     */
    public CardActionMalus() {
    }

    /**
     * 
     */
    public Integer malus;
    
    public String cardType;
}