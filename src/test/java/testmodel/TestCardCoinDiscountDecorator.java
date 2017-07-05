package testmodel;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.controller.decorator.CardCoinDiscountDecorator;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import junit.framework.TestCase;

public class TestCardCoinDiscountDecorator extends TestCase{

	private TestPlayer testPlayer = new TestPlayer();
	private GameHandler testGameHandler = new GameHandler();
	private DecoratedMethods testDecorator = new DecoratedMethods();
	private CardCoinDiscountDecorator testResourcesDecorator;
	private Integer testBonus;
	
	@Before
	public void setUp() throws IOException, NotEnoughResourcesException{
		testDecorator.setGameHandler(testGameHandler);
	    testGameHandler.setPeriod(1);
	    testGameHandler.setRound(1);
	    testGameHandler.initializeTheGame();
	    testPlayer.playerColor = "black";
	    testPlayer.resources.setCoins(5);
	    testPlayer.resources.setWoods(1);
	    testPlayer.resources.setStones(3);
	    testBonus = 1;
	    testResourcesDecorator = new CardCoinDiscountDecorator(testDecorator,testBonus);
	}
	
	@Test
	public void testCardCoinDiscount() throws NotEnoughResourcesException, NotEnoughPointsException{
		// bonus is 1 coin
		Integer expectedCoins = 3;
		// card 3 is Triumphal Arch (cost: 2 stones and 2 coins)
		testResourcesDecorator.resourcesForBuilding(testPlayer,testGameHandler.mainBoard.buildingMap.get(3));
		// card 1 is Preacher (cost: 2 coins)
		testResourcesDecorator.coinsForCharacter(testPlayer,testGameHandler.mainBoard.characterMap.get(1));
		// card 3 is Repairing the Church (cost: 1 wood, 1 stone, 1 coin)
		testResourcesDecorator.resourcesForVenture(testPlayer,testGameHandler.mainBoard.ventureMap.get(3));
		assertEquals(expectedCoins,testPlayer.resources.getCoins());
		
	}
	
}
