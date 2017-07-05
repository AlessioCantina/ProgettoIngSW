package junittest;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.controller.decorator.NoMarketDecorator;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.FamilyMember;
import junit.framework.TestCase;

public class TestNoMarketDecorator extends TestCase{
	
	private TestPlayer testPlayer = new TestPlayer();
	private GameHandler testGameHandler = new GameHandler();
	private DecoratedMethods testDecorator = new DecoratedMethods();
	private FamilyMember testMember = new FamilyMember();
	private NoMarketDecorator testNoMarket;
	
	@Before
	public void setUp() throws IOException, NotEnoughResourcesException{
		testDecorator.setGameHandler(testGameHandler);
	    testGameHandler.setPeriod(1);
	    testGameHandler.setRound(1);
	    testGameHandler.initializeTheGame();
	    testNoMarket = new NoMarketDecorator(testDecorator);
	}

	@Test
	public void testNoMarketDecorator() throws IOException, NotEnoughResourcesException, NotEnoughPointsException{
		int testPosition = 1;
		assertFalse(testNoMarket.addFamilyMemberToTheMarket(testMember, testPosition, testPlayer));
	}
}
