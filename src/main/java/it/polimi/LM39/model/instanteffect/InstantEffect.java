package it.polimi.LM39.model.instanteffect;

import java.io.Serializable;

import it.polimi.LM39.model.Effect;

/**
 *  class (can't be abstract because RuntimeAdapterFactory needs a concrete class) that will be extended
 * 	by all instant effects
 */
public class InstantEffect extends Effect implements Serializable{

	private static final long serialVersionUID = -7226139782454268838L;


}