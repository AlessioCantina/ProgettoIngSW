package it.polimi.LM39.model.leaderobject;

import java.io.Serializable;

/**
 * 
 */
public class RequestedTwoCards extends RequestedCard implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4955990652829243939L;

	/**
     * Default constructor
     */
    public RequestedTwoCards() {
    }

    /**
     * 
     */
    public String cardType2;

    /**
     * 
     */
    public Integer cardQty2;

}