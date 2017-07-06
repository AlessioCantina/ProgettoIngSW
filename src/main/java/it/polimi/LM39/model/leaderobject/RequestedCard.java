package it.polimi.LM39.model.leaderobject;

import java.io.Serializable;

/**
 * To activate this leader you need *cardQty* cards of type *cardType*
 */
public class RequestedCard extends LeaderRequestedObjects implements Serializable{

	private static final long serialVersionUID = 9005684571508176816L;

    public String cardType;

    public Integer cardQty;

}