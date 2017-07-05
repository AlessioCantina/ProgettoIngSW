package testcontroller;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.controller.decorator.DecoratedMethodsDecorator;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.CardResources;
import junit.framework.TestCase;
import testmodel.TestPlayer;

public class TestDecoratedMethodsDecorator extends TestCase{
	
	private DecoratedMethods testDecoratedMethods = new DecoratedMethods();
	private TestPlayer testPlayer = new TestPlayer();
	private GameHandler testGameHandler = new GameHandler();
	
	@Before
	public void setUp() throws IOException, NotEnoughResourcesException{
		testDecoratedMethods.setGameHandler(testGameHandler);
		testPlayer.resources.setCoins(10);
		testPlayer.resources.setWoods(10);
	    testGameHandler.setPeriod(1);
	    testGameHandler.setRound(1);
	    testGameHandler.initializeTheGame();
	}
	
	@Test
	public void testDecoratedMethods() throws NotEnoughResourcesException, NotEnoughPointsException{
		Integer expectedCoins = 9;
		Integer expectedWoods = 5;
		Integer expectedFaith = 1;
		DecoratedMethodsDecorator testDecorator = new DecoratedMethodsDecorator(testDecoratedMethods);
		CardResources testResources = new CardResources();
		testResources.coins = 1;
		CardPoints testPoints = new CardPoints();
		testPoints.faith = 1;
		//first card is Preacher which costs 2 coins
		testDecorator.coinsForCharacter(testPlayer,testGameHandler.mainBoard.characterMap.get(1));
		//second card is Hosting Panhandlers which costs 3 woods
		testDecorator.resourcesForVenture(testPlayer,testGameHandler.mainBoard.ventureMap.get(2));
		//first card is Chapel which costs 2 woods
		testDecorator.resourcesForBuilding(testPlayer,testGameHandler.mainBoard.buildingMap.get(1));
		
		testDecorator.addCardResources(testResources,testPlayer);
		testDecorator.addCardPoints(testPoints,testPlayer);
		
		assertEquals(expectedCoins,testPlayer.resources.getCoins());
		assertEquals(expectedWoods,testPlayer.resources.getWoods());
		assertEquals(expectedFaith,testPlayer.points.getFaith());

		
	}

}
