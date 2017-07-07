package testmodel;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.model.ActionBonus;
import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.MainBoard;
import junit.framework.TestCase;
/**
 * test main board class
 */
public class TestMainBoard extends TestCase{
	ActionBonus[][] testBonus = new ActionBonus[1][1];	
	MainBoard testMainBoard;
	String[][] testCards = new String[1][1];
	
	/**
	 * set up some bonuses, resources and points that will be set on the mainboard
	 */
	@Before
	public void setUp(){
		testBonus[0][0] = new ActionBonus();
		testCards[0][0] = new String();
		testMainBoard = new MainBoard();
		CardResources testResources = new CardResources();
		CardPoints testPoints = new CardPoints();
		testResources.coins = 2;
		testResources.servants = 1;
		testResources.council = 2;
		testResources.woods = 1;
		testResources.stones = 2;
		testPoints.faith = 3;
		testPoints.military = 4;
		testPoints.victory = 1;
		testBonus[0][0].points = testPoints;
		testBonus[0][0].resources = testResources;
		testCards[0][0] = "Hosting Panhandlers";
	}
	/**
	 * try to set bonuses on towers
	 */
	@Test
	public void testTowerBonuses(){
		ActionBonus[][] getBonusTest = new ActionBonus[4][4];
		testMainBoard.setTowersBonuses(testBonus);
		getBonusTest = testMainBoard.getTowersBonuses();
		if(getBonusTest.length != 0)
			assertTrue(true);
		else
			assertTrue(false);
	}
	/**
	 * try to set dices values
	 */
	@Test
	public void testDicesValues(){
		Integer[] testValues = {2,3,5};
		testMainBoard.setDiceValues(testValues);
		if(testMainBoard.getDiceValues() == testValues)
			assertTrue(true);
		else
			assertTrue(false);
		
	}
	/**
	 * try to set played leader cards
	 */
	@Test
	public void testPlayedLeaderCards(){
		String leaderTest = "Ludovico Ariosto";
		testMainBoard.setPlayedLeaderCard(leaderTest);
		assertTrue(!testMainBoard.getPlayedLeaderCard().isEmpty());
	}
	/**
	 * try to set cards on towers
	 */
	@Test
	public void testCardNameOnTowers(){
		testMainBoard.setCardNamesOnTheTowers(testCards);
		if(testMainBoard.getCardsOnTheTowers().length != 0)
			assertTrue(true);
		else
			assertTrue(false);
	}
	/**
	 * try to set values on towers
	 */
	@Test
	public void testTowersValues(){
		Integer[][] testTowersValues = new Integer [1][1];
		testTowersValues[0][0] = 2;
		testMainBoard.setTowersValue(testTowersValues);
		assertEquals(testTowersValues,testMainBoard.getTowersValue());
	}
	
}
