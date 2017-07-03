package junittest;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.controller.decorator.ResourcesMalusDecorator;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.CardResources;
import junit.framework.TestCase;

public class TestResourcesMalusDecorator extends TestCase{
	

	private TestPlayer testPlayer = new TestPlayer();
	private GameHandler testGameHandler = new GameHandler();
	private DecoratedMethods testDecorator = new DecoratedMethods();
	private ResourcesMalusDecorator testResourcesDecorator;
	private CardResources testMalus = new CardResources();
	private CardResources testResources = new CardResources();
	
	@Before
	public void setUp() throws IOException, NotEnoughResourcesException{
		testDecorator.setGameHandler(testGameHandler);
	    testGameHandler.setPeriod(1);
	    testGameHandler.setRound(1);
	    testGameHandler.initializeTheGame();
	    testPlayer.resources.setWoods(0);
	    testPlayer.resources.setStones(0);
	    testPlayer.setResponse("woods");
	    testMalus.woods = 1;
	    testMalus.stones = 1;
	    testResources.woods = 2;
	    testResources.stones = 2;
	    testResourcesDecorator = new ResourcesMalusDecorator(testDecorator,testGameHandler,testMalus,testPlayer);
	}
	
	@Test
	public void testBuildingResourcesDecorator() throws NotEnoughResourcesException, NotEnoughPointsException{
		// malus is 1 wood, 1 stone
		Integer expectedWoods = 1;
		// 2 woods earned (testResources)
		testResourcesDecorator.addCardResources(testResources,testPlayer);
		assertEquals(expectedWoods,testPlayer.resources.getWoods());
		
		testPlayer.setResponse("stones");
		Integer expectedStones = 3;
		// 3 stones earned (2 times from testResources, one with malus)
		testResourcesDecorator.addCardResources(testResources,testPlayer);
		
		assertEquals(expectedStones,testPlayer.resources.getStones());
	}
	
}

