package junittest;


import java.util.ArrayList;

import org.junit.Test;

import it.polimi.LM39.model.PersonalBoard;
import junit.framework.TestCase;

public class TestPersonalBoard extends TestCase{
	
	private PersonalBoard testPersonalBoard = new PersonalBoard();
	
	@Test
	public void testPossession(){
		Integer testCard = 5;
		String testCardType = "Territory";
		testPersonalBoard.setPossessions(testCard, testCardType);
		assertEquals(testPersonalBoard.getPossessions(testCardType).get(0),testCard);
		testCardType = "Building";
		testPersonalBoard.setPossessions(testCard, testCardType);
		assertEquals(testPersonalBoard.getPossessions(testCardType).get(0),testCard);
		testCardType = "Venture";
		testPersonalBoard.setPossessions(testCard, testCardType);
		assertEquals(testPersonalBoard.getPossessions(testCardType).get(0),testCard);
		testCardType = "Character";
		testPersonalBoard.setPossessions(testCard, testCardType);
		assertEquals(testPersonalBoard.getPossessions(testCardType).get(0),testCard);
	}

	@Test
	public void testPossessedLeader(){
		String testLeader = "Ludovico Ariosto";
		ArrayList<String> testLeaders = new ArrayList<String>();
		testLeaders.add(testLeader);
		testPersonalBoard.setLeader(testLeader);
		assertEquals(testPersonalBoard.getPossessedLeaders(),testLeaders);
		testPersonalBoard.getPossessedLeaders().clear();
		testPersonalBoard.setPossessedLeaders(testLeaders);
		assertEquals(testPersonalBoard.getPossessedLeaders(),testLeaders);
	}
}
