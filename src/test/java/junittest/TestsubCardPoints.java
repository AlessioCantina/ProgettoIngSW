package junittest;

import org.junit.Test;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.model.CardPoints;
import junit.framework.TestCase;

public class TestsubCardPoints extends TestCase{
	GameHandler gameHandler = new GameHandler();
	
	@Test
	public void testSubCardPoints() throws NotEnoughPointsException{
		TestPlayer player = new TestPlayer();
		CardPoints points = new CardPoints();
		player.points.setFaith(1);
		player.points.setMilitary(1);
		points.faith=1;
		points.military=0;
		gameHandler.subCardPoints(points, player);
		Integer testValue1 = 0;
		Integer testValue2 = 1;
		assertEquals(testValue1,player.points.getFaith());
		assertEquals(testValue2,player.points.getMilitary());
		
		//in case the player has not enough resources an exception will be thrown and he will not pay anything
		player = new TestPlayer();
		points = new CardPoints();
		player.points.setFaith(1);
		player.points.setMilitary(1);
		points.faith=1;
		points.military=2;
		try{
			gameHandler.subCardPoints(points, player);
		}
		catch(NotEnoughPointsException e){
			assertTrue(("Not enough military points!").equals(e.getMessage()));
			testValue1 = 1;
			assertEquals(testValue1,player.points.getFaith());
			assertEquals(testValue1,player.points.getMilitary());
		}
	}
	
}
