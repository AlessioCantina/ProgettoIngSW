package it.polimi.LM39.model;

import java.io.Serializable;

import it.polimi.LM39.model.characterpermanenteffect.CharacterPermanentEffect;
import it.polimi.LM39.model.instanteffect.InstantEffect;

/**
 * this class contains costs and effects of a character card
 */
public class Character extends Card implements Serializable{

	private static final long serialVersionUID = -116916025932442030L;

	public Integer costCoins = 0;

    public InstantEffect instantBonuses;

    public CharacterPermanentEffect permanentEffect;

}