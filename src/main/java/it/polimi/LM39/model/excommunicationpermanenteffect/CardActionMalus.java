package it.polimi.LM39.model.excommunicationpermanenteffect;


/**
 * 
 */
public class CardActionMalus extends ExcommunicationPermanentEffect {

	/*
	 * Each time you take a *cardType* Card (through the action space or as a Card effect), your action receives a *malus* reduction of its value.
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