package it.polimi.LM39.model.excommunicationpermanenteffect;

import java.io.Serializable;

/**
 * Used by 3 excommunications
 * At the end of the game, you don’t score points for any of your *cardType*
 */
public class NoVictoryForCard extends ExcommunicationPermanentEffect implements Serializable{

	private static final long serialVersionUID = -2825078582133292652L;

    public String cardType;

}