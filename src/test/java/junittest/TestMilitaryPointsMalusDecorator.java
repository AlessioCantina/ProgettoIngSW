package junittest;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.controller.decorator.MilitaryPointsMalusDecorator;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.CardPoints;
import junit.framework.TestCase;

public class TestMilitaryPointsMalusDecorator extends TestCase{

	private TestPlayer testPlayer = new TestPlayer();
	private GameHandler testGameHandler = new GameHandler();
	private DecoratedMethods testDecorator = new DecoratedMethods();
	private MilitaryPointsMalusDecorator testResourcesDecorator;
	private Integer testMalus;
	
	@Before
	public void setUp() throws IOException, NotEnoughPointsException, NotEnoughResourcesException{
		testDecorator.setGameHandler(testGameHandler);
	    testGameHandler.setPeriod(1);
	    testGameHandler.setRound(1);
	    testGameHandler.initializeTheGame();
	    testPlayer.playerColor = "black";
	    testPlayer.points.setMilitary(5);
	    testMalus = 1;
	    testResourcesDecorator = new MilitaryPointsMalusDecorator(testDecorator,testMalus);
	}
	
	@Test
	public void testMilitaryPointsMalus() throws NotEnoughResourcesException, NotEnoughPointsException{
		// malus is 1 military
		Integer expectedMilitary = 7;
		CardPoints testPoints = new CardPoints();
		testPoints.military = 3;
		testResourcesDecorator.addCardPoints(testPoints,testPlayer);
		assertEquals(expectedMilitary,testPlayer.points.getMilitary());
		
	}
	
}
