package it.polimi.LM39.model.leaderpermanenteffect;

import java.io.Serializable;

import it.polimi.LM39.model.Effect;

/**
 * 
 */
public class CopyLeaderAbility extends LeaderPermanentEffect implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1081478849383492648L;

	/**
     * Default constructor
     */
    public CopyLeaderAbility() {
    }

    /**
     * 
     */
    public Effect copiedEffect;

}