package testmodel;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.controller.decorator.VentureResourcesDiscountDecorator;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.CardResources;
import junit.framework.TestCase;

public class TestVentureResourcesDiscountDecorator extends TestCase{
	
	private TestPlayer testPlayer = new TestPlayer();
	private GameHandler testGameHandler = new GameHandler();
	private DecoratedMethods testDecorator = new DecoratedMethods();
	private VentureResourcesDiscountDecorator testResourcesDecorator;
	private CardResources ventureDiscount = new CardResources();
	
	@Before
	public void setUp() throws IOException, NotEnoughResourcesException{
		testDecorator.setGameHandler(testGameHandler);
	    testGameHandler.setPeriod(1);
	    testGameHandler.setRound(1);
	    testGameHandler.initializeTheGame();
	    testPlayer.resources.setWoods(5);
	    testPlayer.resources.setStones(5);
	    testPlayer.setResponse("woods");
	    ventureDiscount.woods = 1;
	    ventureDiscount.stones = 1;
	    testResourcesDecorator = new VentureResourcesDiscountDecorator(testDecorator,ventureDiscount);
	}
	
	@Test
	public void testBuildingResourcesDecorator() throws NotEnoughResourcesException{
		// discount is 1 wood
		Integer expectedWoods = 3;
		// second card is Hosting Panhandlers (which costs 3 woods) 

		testResourcesDecorator.resourcesForVenture(testPlayer, testGameHandler.mainBoard.ventureMap.get(2));
		assertEquals(expectedWoods,testPlayer.resources.getWoods());
		
		testPlayer.setResponse("stones");
		//discount is 1 stone
		Integer expectedStones = 4;
		// sixth card is Raising a Statue (which costs 2 stones)
		testResourcesDecorator.resourcesForVenture(testPlayer, testGameHandler.mainBoard.ventureMap.get(6));
		assertEquals(expectedStones,testPlayer.resources.getStones());
	}
	
	@Test
	public void testBuildingResourcesDecoratorOneResource() throws NotEnoughResourcesException{
		// discount is only 1 wood
		Integer expectedWoods = 3;
		ventureDiscount.stones = 0;
		
		testResourcesDecorator.resourcesForVenture(testPlayer, testGameHandler.mainBoard.ventureMap.get(2));
		
		assertEquals(expectedWoods,testPlayer.resources.getWoods());
		testResourcesDecorator.resourcesForVenture(testPlayer, testGameHandler.mainBoard.ventureMap.get(6));

		assertEquals(expectedWoods,testPlayer.resources.getStones());
		

	}

}
