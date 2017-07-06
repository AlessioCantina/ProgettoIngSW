package it.polimi.LM39.model.leaderobject;

import java.io.Serializable;

import it.polimi.LM39.model.CardPoints;

/**
 * To activate this leader you need *points* points
 */
public class RequestedPoints extends LeaderRequestedObjects implements Serializable{

	
	private static final long serialVersionUID = -5456218164725905198L;

    public CardPoints points;

}