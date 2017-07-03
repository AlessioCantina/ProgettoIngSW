package junittest;

import java.io.IOException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import it.polimi.LM39.controller.CouncilHandler;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import junit.framework.TestCase;

public class TestCouncilHandler extends TestCase{
	CouncilHandler councilHandler = new CouncilHandler();
	GameHandler gameHandler = new GameHandler();

	@Before
	public void setUp() throws IOException  {
		gameHandler.setPeriod(1);
		gameHandler.setRound(1);
		gameHandler.initializeTheGame();
	}
	
	@Test
	public void testGetCouncil() throws NotEnoughResourcesException, NotEnoughPointsException{
		TestPlayer player = new TestPlayer();
		
		//the player decides to get the first favor (1 wood and 1 stone)
		player.setResponse("1");
		councilHandler.getCouncil(1, player, gameHandler, new ArrayList<Integer>());
		Integer testValue=1;
		assertEquals(testValue,player.resources.getStones());
		assertEquals(testValue,player.resources.getWoods());
		
		//the player decides to get the second favor (2 servants)
		player.setResponse("2");
		councilHandler.getCouncil(1, player, gameHandler, new ArrayList<Integer>());
		testValue=2;
		assertEquals(testValue,player.resources.getServants());
		
		//the player decides to get the third favor (2 coins)
		player.setResponse("3");
		councilHandler.getCouncil(1, player, gameHandler, new ArrayList<Integer>());
		testValue=2;
		assertEquals(testValue,player.resources.getCoins());
		
		//the player decides to get the fourth favor (2 military points)
		player.setResponse("4");
		councilHandler.getCouncil(1, player, gameHandler, new ArrayList<Integer>());
		testValue=2;
		assertEquals(testValue,player.points.getMilitary());
		
		//the player decides to get the fifth favor (1 faith point)
		player.setResponse("5");
		councilHandler.getCouncil(1, player, gameHandler, new ArrayList<Integer>());
		testValue=1;
		assertEquals(testValue,player.points.getFaith());
	}
}
