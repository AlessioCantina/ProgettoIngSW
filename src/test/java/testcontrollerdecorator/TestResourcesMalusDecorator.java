package testcontrollerdecorator;

import org.junit.Before;
import org.junit.Test;
import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.controller.decorator.ResourcesMalusDecorator;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.CardResources;
import junit.framework.TestCase;
import testmodel.FakeTestPlayer;

/**
 * test the resources malus decorator
 *
 */
public class TestResourcesMalusDecorator extends TestCase{
	

	private FakeTestPlayer testPlayer = new FakeTestPlayer();
	private GameHandler testGameHandler = new GameHandler();
	private DecoratedMethods testDecorator = new DecoratedMethods();
	private ResourcesMalusDecorator testResourcesDecorator;
	private CardResources testMalus = new CardResources();
	private CardResources testResources = new CardResources();
	
	/**
	 * set up a fake game to test the decorator
	 */
	@Before
	public void setUp() throws NotEnoughResourcesException{
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
	    testResourcesDecorator = new ResourcesMalusDecorator(testDecorator,testGameHandler,testMalus);
	}
	/**
	 * try to get a building with the malus applied on two resources
	 * @throws NotEnoughResourcesException
	 * @throws NotEnoughPointsException
	 */
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
	/**
	 * try to get a building with the malus applied on a single resource
	 * @throws NotEnoughResourcesException
	 * @throws NotEnoughPointsException
	 */
	@Test
	public void testBuildingResourcesDecoratorSingleResource() throws NotEnoughResourcesException, NotEnoughPointsException{
		// malus is 1 wood, 1 stone
		Integer expectedWoods = 1;
		testResources.stones = 0;
		// 2 woods earned (testResources)
		testResourcesDecorator.addCardResources(testResources,testPlayer);
		assertEquals(expectedWoods,testPlayer.resources.getWoods());
		
		testResources.stones = 2;
		testResources.woods = 0;
		
		Integer expectedStones = 1;
		// 3 stones earned (2 times from testResources, both without malus)
		testResourcesDecorator.addCardResources(testResources,testPlayer);
		
		assertEquals(expectedStones,testPlayer.resources.getStones());
	}
	
}

