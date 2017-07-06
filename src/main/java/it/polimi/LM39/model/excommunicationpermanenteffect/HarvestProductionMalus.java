package it.polimi.LM39.model.excommunicationpermanenteffect;

import java.io.Serializable;

/**
 * Used by 2 excommunications
 * Each time you perform a Harvest/Production action (through the action space or as a Card effect), decrease its value by *malus*.
 */
public class HarvestProductionMalus extends ExcommunicationPermanentEffect implements Serializable {


	private static final long serialVersionUID = -8935259831196047187L;


    public Integer malus;


    public String actionType;

}