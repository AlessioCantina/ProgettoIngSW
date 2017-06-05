package it.polimi.LM39.model.excommunicationpermanenteffect;

import java.io.Serializable;

/**
 * 
 */
public class DiceMalus extends ExcommunicationPermanentEffect implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4998627043831063599L;

	/*
	 *All your colored Family Members receive a *malus* reduction of their value each time you place them.
	 */
    /**
     * Default constructor
     */
    public DiceMalus() {
    }

    /**
     * 
     */
    public Integer malus;

}