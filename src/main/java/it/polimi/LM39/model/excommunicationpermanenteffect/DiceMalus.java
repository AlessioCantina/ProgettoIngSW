package it.polimi.LM39.model.excommunicationpermanenteffect;

import java.io.Serializable;

/**
 * 	Used by 1 excommunication
 *	All your colored Family Members receive a *malus* reduction of their value each time you place them.
 */
public class DiceMalus extends ExcommunicationPermanentEffect implements Serializable {

	private static final long serialVersionUID = 4998627043831063599L;

    public Integer malus;

}