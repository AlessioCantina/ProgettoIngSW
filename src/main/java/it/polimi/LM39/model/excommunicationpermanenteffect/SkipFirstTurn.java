package it.polimi.LM39.model.excommunicationpermanenteffect;

import java.io.Serializable;

/**
 * 
 */
public class SkipFirstTurn extends ExcommunicationPermanentEffect implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5726124753862596809L;

	/**
     * Each round, you skip your first turn. When all players have taken all their turns, you may still place your last Family Member.
     */
    public SkipFirstTurn() {
    }

}