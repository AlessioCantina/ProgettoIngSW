package it.polimi.LM39.model.excommunicationpermanenteffect;

import java.io.Serializable;

/**
 * Used by 1 excommunication
 * Each time you gain Military Points (from action spaces or from your Cards), gain *militaryQty* fewer Military Point.
 */
public class MilitaryPointsMalus extends ExcommunicationPermanentEffect implements Serializable{

	private static final long serialVersionUID = -7281901286320360301L;

    public Integer militaryQty;

}