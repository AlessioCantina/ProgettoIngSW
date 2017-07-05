package it.polimi.LM39.model;

import java.io.Serializable;
import it.polimi.LM39.model.excommunicationpermanenteffect.*;

/**
 * this class contains the period and the effect of an excommunication
 */
public class Excommunication implements Serializable{

	private static final long serialVersionUID = -8651501433925949712L;

	public Integer period;

    public ExcommunicationPermanentEffect effect;

}