package it.polimi.LM39.model.instanteffect;

import java.io.Serializable;

/**
 * 
 */
public class GetCard extends InstantEffect implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1388078448991697253L;

	/**
     * 
     */
    public String cardType;

    /**
     * 
     */
    public Integer cardValue;

}