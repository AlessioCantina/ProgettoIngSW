package testcontroller;


import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.model.PlayerRank;
import it.polimi.LM39.model.Rankings;
import junit.framework.TestCase;
import testmodel.FakeTestPlayer;
/**
 * test update rankings method (gamehandler class)
 *
 */
public class TestupdateRankings extends TestCase{
	
	/**
	 * we need to set up a complete game to test this method
	 */
	private FakeTestPlayer testPlayer = new FakeTestPlayer();
	private GameHandler testGameHandler = new GameHandler();
	private Rankings testRankings = new Rankings();
	private PlayerRank testMilitaryRank = new PlayerRank();
	private PlayerRank testVictoryRank = new PlayerRank();
	private PlayerRank testFaithRank = new PlayerRank();
	private ArrayList<PlayerRank> testFaithList = new ArrayList<PlayerRank>();
	private ArrayList<PlayerRank> testVictoryList = new ArrayList<PlayerRank>();
	private ArrayList<PlayerRank> testMilitaryList = new ArrayList<PlayerRank>();
	
	/**
	 * initialize variables to use the updateRankings method
	 */
	@Before
	public void setUp() throws NotEnoughPointsException, IOException {
	
		testGameHandler.initializeTheGame();
		
		testPlayer.playerColor = "black";
		
		testVictoryRank.playerColor = "black";
		testVictoryRank.setPlayerPoints(5);
		testVictoryList.add(testVictoryRank);
		testRankings.setVictoryRanking(testVictoryList);
		
		testMilitaryRank.playerColor = "black";
		testMilitaryRank.setPlayerPoints(5);
		testMilitaryList.add(testMilitaryRank);
		testRankings.setMilitaryRanking(testMilitaryList);
		
		testFaithRank.playerColor = "black";
		testFaithRank.setPlayerPoints(5);
		testFaithList.add(testFaithRank);
		testRankings.setFaithRanking(testFaithList);
		
		testGameHandler.mainBoard.rankings = testRankings; 
	}

	/**
	 * test update rankings, it will set the current rankings to player's values
	 * @throws NotEnoughPointsException
	 */
	@Test
	public void testRankings() throws NotEnoughPointsException {
		testPlayer.points.setFaith(7);
		testPlayer.points.setMilitary(3);
		testPlayer.points.setVictory(5);
		Integer testResult;
		testGameHandler.updateRankings(testPlayer);
		testResult = testGameHandler.mainBoard.rankings.getFaithRanking().get(0).getPlayerPoints();
		assertEquals(testResult,testPlayer.points.getFaith());
		testResult = testGameHandler.mainBoard.rankings.getVictoryRanking().get(0).getPlayerPoints();
		assertEquals(testResult,testPlayer.points.getVictory());
		testResult = testGameHandler.mainBoard.rankings.getMilitaryRanking().get(0).getPlayerPoints();
		assertEquals(testResult,testPlayer.points.getMilitary());
	}
	
}
