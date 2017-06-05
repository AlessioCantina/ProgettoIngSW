package it.polimi.LM39.model.leaderobject;


import java.io.Serializable;

import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.instanteffect.Points;
import it.polimi.LM39.model.instanteffect.Resources;

/**
 * 
 */
public class RequestedCardPointsResources extends RequestedCard implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8210771088358346492L;

	/**
     * Default constructor
     */
    public RequestedCardPointsResources() {
    }

    /**
     * 
     */
    public CardResources resources;

    /**
     * 
     */
    public CardPoints points;

}