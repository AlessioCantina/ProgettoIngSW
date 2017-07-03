package junittest;

import org.junit.Test;

import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import junit.framework.TestCase;

public class TestsetFirstRoundBonuses extends TestCase{
	
	Integer testValue = 1;
	TestPlayer testPlayer = new TestPlayer();
	GameHandler gameHandler = new GameHandler();
	
	@Test
	public void testFirstRoundBonuses() throws NotEnoughResourcesException{
		gameHandler.setFirstRoundBonuses(testPlayer, testValue);
		if(testPlayer.resources.getCoins() == 5 &&
		testPlayer.resources.getServants() == 3 &&
		testPlayer.resources.getStones() == 2 &&
		testPlayer.resources.getWoods() == 2)
			assertTrue(true);
		else
			assertTrue(false);
		gameHandler.setFirstRoundBonuses(testPlayer, testValue+1);
		if(testPlayer.resources.getCoins() == 11)
			assertTrue(true);
		else
			assertTrue(false);
		gameHandler.setFirstRoundBonuses(testPlayer, testValue+2);
		if(testPlayer.resources.getCoins() == 18)
			assertTrue(true);
		else
			assertTrue(false);
		gameHandler.setFirstRoundBonuses(testPlayer, testValue+3);
		if(testPlayer.resources.getCoins() == 26)
			assertTrue(true);
		else
			assertTrue(false);
	}

}
