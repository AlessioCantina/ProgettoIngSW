package it.polimi.LM39.model.excommunicationpermanenteffect;

import java.io.Serializable;

/**
 * Used by 4 excommunications
 * Each time you take a *cardType* Card (through the action space or as a Card effect), your action receives a *malus* reduction.
 */
public class CardActionMalus extends ExcommunicationPermanentEffect implements Serializable{

	private static final long serialVersionUID = 1592299992871687996L;

    public Integer malus;
    
    public String cardType;
}