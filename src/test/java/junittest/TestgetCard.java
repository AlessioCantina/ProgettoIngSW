package junittest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

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
	public void testGetCard1() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NotEnoughResourcesException, NotEnoughPointsException{
		TestPlayer player = new TestPlayer();
		player.playerColor="green";
		//reset player resources
		player.resources.setCoins(-player.resources.getCoins());
		player.resources.setWoods(-player.resources.getWoods());
		player.resources.setStones(-player.resources.getStones());
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
		
		//get a venture card (in this case Support to the Cardinal) it costs 4 military points but needed are 7 or 2 woods, 2 stones and 3 coins it gives 3 faith points and 4 victory points at the end of the game
		//player decides to pay in military
		player.setResponse("1");
		player.points.setMilitary(7);
		assertTrue(gameHandler.getCard(9, player, 3));
		//check that the player has payed correctly
		testValue1=3;
		assertEquals(testValue1,player.points.getMilitary());
		testValue1=8;
		assertEquals(testValue1,player.points.getFaith());
		testValue1=4;
		assertEquals(testValue1,player.points.getFinalVictory());
		//check that the player has not paid any wood stone or coin
		testValue1=0;
		assertEquals(testValue1,player.resources.getWoods());
		assertEquals(testValue1,player.resources.getStones());
		testValue1=1;
		assertEquals(testValue1,player.resources.getCoins());
	}
		
	@Test
	public void testGetCard2() throws NotEnoughResourcesException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException{
		TestPlayer player = new TestPlayer();
		player.playerColor="green";
		//get a venture card (in this case Support to the Cardinal) it costs 4 military points but needed are 7 or 2 woods, 2 stones and 3 coins it gives 3 faith points and 4 victory points at the end of the game
		//player decides to pay in resources
		player.setResponse("2");
		player.resources.setCoins(3);
		player.resources.setWoods(2);
		player.resources.setStones(2);
		assertTrue(gameHandler.getCard(9, player, 3));
		//check that the player has payed correctly
		Integer testValue1=0;
		assertEquals(testValue1,player.resources.getWoods());
		assertEquals(testValue1,player.resources.getStones());
		assertEquals(testValue1,player.resources.getCoins());
		//check that the player has not payed any military point
		assertEquals(testValue1,player.points.getMilitary());
		testValue1=4;
		assertEquals(testValue1,player.points.getFinalVictory());
		testValue1=3;
		assertEquals(testValue1,player.points.getFaith());
	}


}
