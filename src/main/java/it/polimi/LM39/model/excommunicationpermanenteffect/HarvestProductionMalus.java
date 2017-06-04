package it.polimi.LM39.model.excommunicationpermanenteffect;


/**
 * 
 */
public class HarvestProductionMalus extends ExcommunicationPermanentEffect {

	/*
	 * Each time you perform a Harvest/Production action (through the action space or as a Card effect), decrease its value by *malus*.
	 */
    /**
     * Default constructor
     */
    public HarvestProductionMalus() {
    }

    /**
     * 
     */
    public Integer malus;

    /**
     * 
     */
    public String actionType;

}