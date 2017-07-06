package it.polimi.LM39.model.leaderobject;

import java.io.Serializable;

/**
 * To activate this leader you need *cardQty* cards of type *cardType* and
 * *cardQty2* cards of type *cardType2*
 */
public class RequestedTwoCards extends RequestedCard implements Serializable{

	private static final long serialVersionUID = 4955990652829243939L;

    public String cardType2;

    public Integer cardQty2;

}