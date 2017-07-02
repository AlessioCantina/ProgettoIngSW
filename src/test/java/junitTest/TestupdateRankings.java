package junittest;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.model.PlayerPoints;
import junit.framework.TestCase;

public class TestupdateRankings extends TestCase{
	private TestPlayer testPlayer1 = new TestPlayer();
	private GameHandler testGame = new GameHandler();

	@Before
	public void setUp() throws NotEnoughPointsException {
	
		testPlayer1.points = new PlayerPoints();
		testPlayer1.points.setFaith(1);
		testPlayer1.points.setVictory(1);
		testPlayer1.points.setMilitary(1);
		testPlayer1.playerColor = "black";
	}

	@Test
	public void testRankings() {
		int player1Victory = 1;
		int player1Faith = 1;
		int player1Military = 1;
		int testResult;
		testGame.updateRankings(testPlayer1);
		testResult = testPlayer1.points.getFaith();
		assertEquals(testResult,player1Faith);
		testResult = testPlayer1.points.getVictory();
		assertEquals(testResult,player1Victory);
		testResult = testPlayer1.points.getMilitary();
		assertEquals(testResult,player1Military);
	}
	
}
