package junittest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import junit.framework.TestCase;

public class TestgetCard extends TestCase{
	GameHandler gameHandler = new GameHandler();
	
	@Before
	public void setUp() throws IOException  {
		gameHandler.setPeriod(1);
		gameHandler.setRound(1);
		gameHandler.initializeTheGame();
	}
	
	@Test
	public void testGetCard() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NotEnoughResourcesException, NotEnoughPointsException{
		TestPlayer player = new TestPlayer();
		player.playerColor="green";
		//reset player resources
		player.resources.setCoins(-player.resources.getCoins());
		player.resources.setWoods(-player.resources.getWoods());
		//get a territory card (in this case the City)
		assertTrue(gameHandler.getCard(1, player, 0));
		//the player must receive 3 coins from this card
		Integer testValue1 = 3;
		assertEquals(testValue1,player.resources.getCoins());
		
		//get a character card (in this case the Preacher) that costs 2 coins ( the player now have 3 coins) and gives 4 faith points
		assertTrue(gameHandler.getCard(1, player, 1));
		testValue1=1;
		assertEquals(testValue1,player.resources.getCoins());
		testValue1=4;
		assertEquals(testValue1,player.points.getFaith());
		
		//get a building card (in this case the Chapel) it costs 2 woods and gives 1 faith points
		player.resources.setWoods(2);
		assertTrue(gameHandler.getCard(1, player, 2));
		testValue1=0;
		assertEquals(testValue1,player.resources.getWoods());
		testValue1=5;
		assertEquals(testValue1,player.points.getFaith());
		
		//get a venture card (in this case Fighting Heresies) it costs 3 military points but needed are 5 it gives 2 faith points and 5 victory points at the end of the game
		player.points.setMilitary(5);
		assertTrue(gameHandler.getCard(1, player, 3));
		testValue1=2;
		assertEquals(testValue1,player.points.getMilitary());
		testValue1=7;
		assertEquals(testValue1,player.points.getFaith());
		testValue1=5;
		assertEquals(testValue1,player.points.getFinalVictory());
	}

}
