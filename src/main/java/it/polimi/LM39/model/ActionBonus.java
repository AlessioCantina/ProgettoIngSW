package it.polimi.LM39.model;

import java.io.Serializable;

/**
 * the action bonuses are the bonuses that are given by an action space, they could be resources and/or points
 */
public class ActionBonus implements Serializable {

	private static final long serialVersionUID = 4262477847646997148L;

	public CardResources resources = new CardResources();

	public CardPoints points = new CardPoints();

}
