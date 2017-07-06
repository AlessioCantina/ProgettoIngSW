package it.polimi.LM39.model.leaderobject;

import java.io.Serializable;

/**
 * To activate this leader you need *cardQty* cards of every type
 *
 */
public class RequestedSameCard extends LeaderRequestedObjects implements Serializable{

	private static final long serialVersionUID = 8848521776826040030L;

    public Integer cardQty;

}