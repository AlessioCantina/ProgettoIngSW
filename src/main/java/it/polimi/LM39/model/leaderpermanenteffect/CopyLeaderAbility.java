package it.polimi.LM39.model.leaderpermanenteffect;

import java.io.Serializable;

import it.polimi.LM39.model.Effect;

/**
 * Permanent effect used by Lorenzo de' Medici
 * this effect allows the player to copy the effect of another player leader card
 * the selected effect can't be changed
 */
public class CopyLeaderAbility extends LeaderPermanentEffect implements Serializable{

	private static final long serialVersionUID = 1081478849383492648L;

    public Effect copiedEffect;

}