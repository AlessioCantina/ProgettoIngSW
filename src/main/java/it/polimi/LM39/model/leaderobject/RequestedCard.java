package it.polimi.LM39.model.leaderobject;

import java.io.Serializable;

/**
 * 
 */
public class RequestedCard extends LeaderRequestedObjects implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 9005684571508176816L;

	/**
     * Default constructor
     */
    public RequestedCard() {
    }

    /**
     * 
     */
    public String cardType;

    /**
     * 
     */
    public Integer cardQty;

}