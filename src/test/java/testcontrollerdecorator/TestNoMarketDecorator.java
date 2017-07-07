package testcontrollerdecorator;

import org.junit.Before;
import org.junit.Test;
import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.controller.decorator.NoMarketDecorator;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.FamilyMember;
import junit.framework.TestCase;
import testmodel.FakeTestPlayer;

/**
 * test the no market decorator
 *
 */
public class TestNoMarketDecorator extends TestCase{
	
	private FakeTestPlayer testPlayer = new FakeTestPlayer();
	private GameHandler testGameHandler = new GameHandler();
	private DecoratedMethods testDecorator = new DecoratedMethods();
	private FamilyMember testMember = new FamilyMember();
	private NoMarketDecorator testNoMarket;
	
	/**
	 * set up a fake game to test the decorator
	 */
	@Before
	public void setUp() throws NotEnoughResourcesException{
		testDecorator.setGameHandler(testGameHandler);
	    testGameHandler.setPeriod(1);
	    testGameHandler.setRound(1);
	    testGameHandler.initializeTheGame();
	    testNoMarket = new NoMarketDecorator(testDecorator);
	}

	/**
	 * try to add a family member to the market
	 * @throws NotEnoughResourcesException
	 * @throws NotEnoughPointsException
	 */
	@Test
	public void testNoMarketDecorator() throws NotEnoughResourcesException, NotEnoughPointsException{
		int testPosition = 1;
		assertFalse(testNoMarket.addFamilyMemberToTheMarket(testMember, testPosition, testPlayer));
	}
}
