package it.polimi.LM39.model.excommunicationpermanenteffect;

import java.io.Serializable;

/**
 * Used by 1 excommunication
 * You have to spend *servantsQty* servants to increase your action value by 1
 */
public class ServantsMalus extends ExcommunicationPermanentEffect implements Serializable{

	private static final long serialVersionUID = -7682191154457851386L;

    public Integer servantsQty;
}