package testcontrollerdecorator;

import org.junit.Before;
import org.junit.Test;
import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.controller.decorator.ServantsMalusDecorator;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import junit.framework.TestCase;
import testmodel.FakeTestPlayer;
/**
 * test the servants malus decorator
 *
 */
public class TestServantsMalusDecorator extends TestCase{
	private FakeTestPlayer testPlayer = new FakeTestPlayer();
	private GameHandler testGameHandler = new GameHandler();
	private DecoratedMethods testDecorator = new DecoratedMethods();
	private ServantsMalusDecorator testResourcesDecorator;
	private Integer testMalus;
	
	/**
	 * set up a fake game to test the decorator
	 */
	@Before
	public void setUp() throws NotEnoughPointsException, NotEnoughResourcesException{
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
	
	/**
	 * we can't test the decorator properly because it requires iteration with the player
	 * so we won't add servants to the action
	 * @throws NotEnoughResourcesException
	 */
	@Test
	public void testServantsMalusDecorator() throws NotEnoughResourcesException{
		// malus is 1 servant
		Integer expectedServants = 5;
		testResourcesDecorator.addServants(testPlayer);
		assertEquals(expectedServants,testPlayer.resources.getServants());
		
	}
	
}
