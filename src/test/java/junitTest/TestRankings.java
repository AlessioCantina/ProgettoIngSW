package junitTest;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.model.PlayerRank;
import it.polimi.LM39.model.Rankings;
import junit.framework.TestCase;

public class TestRankings extends TestCase{

	Rankings testRankings = new Rankings();
	ArrayList<PlayerRank> testValues = new ArrayList<PlayerRank>();
	String testColor = "black";
	
	@Before
	public void setUp(){
		PlayerRank testRank = new PlayerRank();
		testRank.playerColor = testColor;
		testValues.add(testRank);
	}
	
	@Test
	public void testRankings(){
		testRankings.setFaithRanking(testValues);
		assertTrue(!testRankings.getFaithRanking().isEmpty());
		testRankings.setVictoryRanking(testValues);
		assertTrue(!testRankings.getVictoryRanking().isEmpty());
		testRankings.setMilitaryRanking(testValues);
		assertTrue(!testRankings.getMilitaryRanking().isEmpty());
	}
}
