package it.polimi.LM39.model.excommunicationpermanenteffect;

import java.io.Serializable;

/**
 * 
 */
public class MilitaryPointsMalus extends ExcommunicationPermanentEffect implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7281901286320360301L;

	/*
	 * Each time you gain Military Points (from action spaces or from your Cards), gain *militaryQty* fewer Military Point.
	 */
    /**
     * Default constructor
     */
    public MilitaryPointsMalus() {
    }

    /**
     * 
     */
    
    public Integer militaryQty;

}