package it.polimi.LM39.model.leaderobject;


import java.io.Serializable;

import it.polimi.LM39.model.CardResources;

/**
 * 
 */
public class RequestedResources extends LeaderRequestedObjects implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6898111787196988487L;

	/**
     * Default constructor
     */
    public RequestedResources() {
    }

    /**
     * 
     */
    public CardResources resources;

}