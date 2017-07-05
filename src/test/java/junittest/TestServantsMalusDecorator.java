package junittest;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.controller.decorator.ServantsMalusDecorator;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import junit.framework.TestCase;

public class TestServantsMalusDecorator extends TestCase{
	private TestPlayer testPlayer = new TestPlayer();
	private GameHandler testGameHandler = new GameHandler();
	private DecoratedMethods testDecorator = new DecoratedMethods();
	private ServantsMalusDecorator testResourcesDecorator;
	private Integer testMalus;
	
	@Before
	public void setUp() throws IOException, NotEnoughPointsException, NotEnoughResourcesException{
		testDecorator.setGameHandler(testGameHandler);
	    testGameHandler.setPeriod(1);
	    testGameHandler.setRound(1);
	    testGameHandler.initializeTheGame();
	    testPlayer.playerColor = "black";
	    testPlayer.resources.setServants(5);
		testPlayer.setResponse("no");
	    testMalus = 1;
	    testResourcesDecorator = new ServantsMalusDecorator(testDecorator,testMalus);
	}
	
	@Test
	public void testMilitaryPointsMalus() throws IOException, NotEnoughResourcesException{
		// malus is 1 military
		Integer expectedServants = 5;
		testResourcesDecorator.addServants(testPlayer);
		assertEquals(expectedServants,testPlayer.resources.getServants());
		
	}
	
}
