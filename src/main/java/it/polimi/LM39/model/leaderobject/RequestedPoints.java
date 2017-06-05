package it.polimi.LM39.model.leaderobject;

import java.io.Serializable;

import it.polimi.LM39.model.CardPoints;

/**
 * 
 */
public class RequestedPoints extends LeaderRequestedObjects implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5456218164725905198L;

	/**
     * Default constructor
     */
    public RequestedPoints() {
    }

    /**
     * 
     */
    public CardPoints points;

}