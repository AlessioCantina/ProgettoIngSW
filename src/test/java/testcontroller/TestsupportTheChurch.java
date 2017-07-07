package testcontroller;

import org.junit.Before;
import org.junit.Test;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import junit.framework.TestCase;
import testmodel.FakeTestPlayer;

public class TestsupportTheChurch extends TestCase{
	GameHandler gameHandler = new GameHandler();
	
	@Before
	public void setUp() {
		gameHandler.setPeriod(1);
		gameHandler.setRound(1);
		gameHandler.initializeTheGame();
		gameHandler.loadCardsOnTheMainBoard();
		gameHandler.rollTheDices();
	}
	
	@Test
	public void testSupportTheChurch() throws NotEnoughPointsException,ReflectiveOperationException, NotEnoughResourcesException{
		FakeTestPlayer player = new FakeTestPlayer();
		player.personalMainBoard=gameHandler.mainBoard;
		//set the necessary faith points needed to support the church at the end of the first period
		player.points.setFaith(3);
		//player decides to support the church
		player.setResponse("yes");
		gameHandler.supportTheChurch(player);
		Integer testValue1=0;
		//the player supported the church so he should have 0 faith points 
		assertEquals(testValue1,player.points.getFaith());
		Integer testValue2=3;
		//the player supported the church with 3 faith points so he should have 3 victory points 
		assertEquals(testValue2,player.points.getVictory());
		Integer testValue3=0;
		Integer size = player.getExcommunications().size();
		//the player should haven't any excommunications
		assertEquals(testValue3,size);
		
		player = new FakeTestPlayer();
		player.personalMainBoard=gameHandler.mainBoard;
		//set the necessary faith points needed to support the church at the end of the first period
		player.points.setFaith(3);
		//player decides not to support the church
		player.setResponse("no");
		gameHandler.supportTheChurch(player);
		testValue1=3;
		//the player does not supported the church so he should have 3 faith points 
		assertEquals(testValue1,player.points.getFaith());
		testValue2=0;
		//the player does not supported the church so he should have 0 victory points 
		assertEquals(testValue2,player.points.getVictory());
		testValue3=1;
		size = player.getExcommunications().size();
		//the player should have one excommunication
		assertEquals(testValue3,size);
		
		player = new FakeTestPlayer();
		player.personalMainBoard=gameHandler.mainBoard;
		gameHandler.supportTheChurch(player);
		testValue1=0;
		//the player does not supported the church so he should have 0 faith points 
		assertEquals(testValue1,player.points.getFaith());
		testValue2=0;
		//the player does not supported the church so he should have 0 victory points 
		assertEquals(testValue2,player.points.getVictory());
		testValue3=1;
		size = player.getExcommunications().size();
		//the player should have one excommunication
		assertEquals(testValue3,size);
		
		
		
		
	}
}
