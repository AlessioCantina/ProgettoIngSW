package testcontrollerdecorator;

import org.junit.Before;
import org.junit.Test;
import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.controller.decorator.MilitaryPointsMalusDecorator;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.CardPoints;
import junit.framework.TestCase;
import testmodel.FakeTestPlayer;
/**
 * test military points malus decorator class
 *
 */
public class TestMilitaryPointsMalusDecorator extends TestCase{

	private FakeTestPlayer testPlayer = new FakeTestPlayer();
	private GameHandler testGameHandler = new GameHandler();
	private DecoratedMethods testDecorator = new DecoratedMethods();
	private MilitaryPointsMalusDecorator testResourcesDecorator;
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
	    testPlayer.points.setMilitary(5);
	    testMalus = 1;
	    testResourcesDecorator = new MilitaryPointsMalusDecorator(testDecorator,testMalus);
	}
	/**
	 * try to get military points with the malus applied
	 * @throws NotEnoughResourcesException
	 * @throws NotEnoughPointsException
	 */
	@Test
	public void testMilitaryPointsMalus() throws NotEnoughResourcesException, NotEnoughPointsException{
		// malus is 1 militarye
		Integer expectedMilitary = 7;
		CardPoints testPoints = new CardPoints();
		testPoints.military = 3;
		testResourcesDecorator.addCardPoints(testPoints,testPlayer);
		assertEquals(expectedMilitary,testPlayer.points.getMilitary());
		
	}
	
}
