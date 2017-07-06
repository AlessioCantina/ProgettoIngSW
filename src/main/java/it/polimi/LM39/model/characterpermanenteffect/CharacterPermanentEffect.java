package it.polimi.LM39.model.characterpermanenteffect;

import java.io.Serializable;

import it.polimi.LM39.model.Effect;

/**
 * class (can't be abstract because RuntimeAdapterFactory needs a concrete class) that will be extended
 * by all character permanent effects
 */
public class CharacterPermanentEffect extends Effect implements Serializable{

	private static final long serialVersionUID = -166066794452529174L;


}