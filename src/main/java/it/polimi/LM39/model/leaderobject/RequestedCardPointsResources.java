package it.polimi.LM39.model.leaderobject;


import java.io.Serializable;
import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.CardResources;


/**
 * To activate this leader you need *cardQty* cards of type *cardType*,
 * *resources* resources and *points* points
 */
public class RequestedCardPointsResources extends RequestedCard implements Serializable{


	private static final long serialVersionUID = 8210771088358346492L;

    public CardResources resources;

    public CardPoints points;

}