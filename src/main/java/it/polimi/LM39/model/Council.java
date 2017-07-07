package it.polimi.LM39.model;

/**
 * this class contains the bonuses that a council favor can give
 */
public enum Council {

	BONUS1 (new CardResources(),1),
	BONUS2 (new CardResources(),2),
	BONUS3 (new CardResources(),3),
	BONUS4 (new CardPoints(),4),
	BONUS5 (new CardPoints(),5);
	
	private CardResources resources = new CardResources();
	private CardPoints points = new CardPoints();
	private Integer bonusNumber;
	
	/**
	 * static initializer to set the bonuses values
	 */
	static{
		BONUS1.resources.woods = 1;
		BONUS1.resources.stones = 1;
		BONUS1.resources.coins = 0;
		BONUS1.resources.servants = 0;
		BONUS1.resources.council = 0;
		
		BONUS2.resources.woods = 0;
		BONUS2.resources.stones = 0;
		BONUS2.resources.coins = 0;
		BONUS2.resources.servants = 2;
		BONUS2.resources.council = 0;
		
		BONUS3.resources.woods = 0;
		BONUS3.resources.stones = 0;
		BONUS3.resources.coins = 2;
		BONUS3.resources.servants = 0;
		BONUS3.resources.council = 0;
		
		BONUS4.points.military = 2;
		BONUS4.points.faith = 0;
		BONUS4.points.victory = 0;
		
		BONUS5.points.military = 0;
		BONUS5.points.faith = 1;
		BONUS5.points.victory = 0;
		
	}
	/**
	 * resources constructor needed by enum to set the value
	 * @param resources
	 * @param bonusNumber
	 */
	Council(CardResources resources, Integer bonusNumber){
		this.bonusNumber = bonusNumber;
	}
	/**
	 * points constructor needed by enum to set the value
	 * @param resources
	 * @param bonusNumber
	 */
	Council(CardPoints resources, Integer bonusNumber){
		this.bonusNumber = bonusNumber;
	}
	/**
	 * get the bonus resources
	 * @param bonusNumber
	 * @return
	 */
	public static CardResources getResources(Integer bonusNumber){
		for(Council councilFavor : Council.values()){
			if(bonusNumber < 4 && councilFavor.bonusNumber == bonusNumber)
				return councilFavor.resources;
		}
		return null;
	}
	/**
	 * get the bonus points
	 * @param bonusNumber
	 * @return
	 */
	public static CardPoints getPoints(Integer bonusNumber){
		for(Council councilFavor : Council.values()){
			if(bonusNumber > 3 && councilFavor.bonusNumber == bonusNumber)
				return councilFavor.points;
		}
		return null;
	}
	
}
