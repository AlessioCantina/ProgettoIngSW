package testcontrollerdecorator;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.controller.decorator.BuildingResourcesDiscountDecorator;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.CardResources;
import junit.framework.TestCase;
import testmodel.FakeTestPlayer;

/**
 * test the building resources discount decorator class
 */
public class TestBuildingResourcesDiscountDecorator extends TestCase{

	private FakeTestPlayer testPlayer = new FakeTestPlayer();
	private GameHandler testGameHandler = new GameHandler();
	private DecoratedMethods testDecorator = new DecoratedMethods();
	private BuildingResourcesDiscountDecorator testResourcesDecorator;
	private CardResources buildingDiscount = new CardResources();
	
	/**
	 * set up a fake game to test the decorator
	 */
	@Before
	public void setUp() throws IOException, NotEnoughResourcesException{
		testDecorator.setGameHandler(testGameHandler);
	    testGameHandler.setPeriod(1);
	    testGameHandler.setRound(1);
	    testGameHandler.initializeTheGame();
	    testPlayer.resources.setWoods(5);
	    testPlayer.resources.setStones(5);
	    testPlayer.setResponse("woods");
	    buildingDiscount.woods = 1;
	    buildingDiscount.stones = 1;
	    testResourcesDecorator = new BuildingResourcesDiscountDecorator(testDecorator,buildingDiscount);
	}
	
	/**
	 * try to get two buildings with the decorator discount on two resources
	 * @throws NotEnoughResourcesException
	 */
	@Test
	public void testBuildingResourcesDecorator() throws NotEnoughResourcesException{
		// discount is 1 wood
		Integer expectedWoods = 4;
		// first card is Chapel (which costs 2 woods) 
		testResourcesDecorator.resourcesForBuilding(testPlayer, testGameHandler.mainBoard.buildingMap.get(1));
		assertEquals(expectedWoods,testPlayer.resources.getWoods());
		
		testPlayer.setResponse("stones");
		//discount is 1 stone
		Integer expectedStones = 3;
		// first card is Mint (which costs 3 stones)
		testResourcesDecorator.resourcesForBuilding(testPlayer, testGameHandler.mainBoard.buildingMap.get(2));
		assertEquals(expectedStones,testPlayer.resources.getStones());
	}
	/**
	 * try to get two buildings with the decorator discount on a single resource
	 * @throws NotEnoughResourcesException
	 */
	@Test
	public void testBuildingResourcesDecoratorOneResource() throws NotEnoughResourcesException{
		// discount is only 1 wood
		Integer expectedWoods = 4;
		buildingDiscount.stones = 0;
		
		// first card is Chapel (which costs 2 woods) 
		testResourcesDecorator.resourcesForBuilding(testPlayer, testGameHandler.mainBoard.buildingMap.get(1));
		assertEquals(expectedWoods,testPlayer.resources.getWoods());
		
		Integer expectedStones = 2;
		// first card is Mint (which costs 3 stones) but we have no discount
		testResourcesDecorator.resourcesForBuilding(testPlayer, testGameHandler.mainBoard.buildingMap.get(2));
		assertEquals(expectedStones,testPlayer.resources.getStones());
		

	}
}
