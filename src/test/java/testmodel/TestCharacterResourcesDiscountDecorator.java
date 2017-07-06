package testmodel;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.controller.decorator.CharacterResourcesDiscountDecorator;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.CardResources;
import junit.framework.TestCase;
/**
 * test the character resources discount decorator class
 */
public class TestCharacterResourcesDiscountDecorator extends TestCase{

	private FakeTestPlayer testPlayer = new FakeTestPlayer();
	private GameHandler testGameHandler = new GameHandler();
	private DecoratedMethods testDecorator = new DecoratedMethods();
	private CharacterResourcesDiscountDecorator testResourcesDecorator;
	private CardResources buildingDiscount = new CardResources();
	
	@Before
	public void setUp() throws IOException, NotEnoughResourcesException{
		testDecorator.setGameHandler(testGameHandler);
	    testGameHandler.setPeriod(1);
	    testGameHandler.setRound(1);
	    testGameHandler.initializeTheGame();
	    testPlayer.resources.setCoins(5);
	    buildingDiscount.coins = 1;
	    testResourcesDecorator = new CharacterResourcesDiscountDecorator(testDecorator,buildingDiscount);
	}
	
	@Test
	public void testCharacterResourcesDecorator() throws NotEnoughResourcesException{
		// discount is 1 coin
		Integer expectedCoins = 4;
		// first card is Preacher (which costs 2 coins) 
		testResourcesDecorator.coinsForCharacter(testPlayer, testGameHandler.mainBoard.characterMap.get(1));
		assertEquals(expectedCoins,testPlayer.resources.getCoins());
		
	}
	
}