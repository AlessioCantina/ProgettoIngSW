package it.polimi.LM39.model.leaderpermanenteffect;

import java.io.Serializable;

import it.polimi.LM39.model.Effect;

/**
 *  class (can't be abstract because RuntimeAdapterFactory needs a concrete class) that will be extended
 * 	by all leader permanent effects
 */
public abstract class LeaderPermanentEffect extends Effect implements Serializable{

	private static final long serialVersionUID = 8543881286278249547L;


}