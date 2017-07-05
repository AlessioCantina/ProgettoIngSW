package it.polimi.LM39.model;

import java.io.Serializable;

/**
 * this class contains the bonuses given by the bonus tiles
 */
public class PersonalBonusTile implements Serializable{

	private static final long serialVersionUID = 5420045668222496904L;
	
	public ActionBonus harvestBonus;
	public ActionBonus productionBonus;
}
