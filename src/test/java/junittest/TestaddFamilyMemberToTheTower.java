package junittest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.CardNotFoundException;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.FamilyMember;
import junit.framework.TestCase;

public class TestaddFamilyMemberToTheTower extends TestCase{
	GameHandler gameHandler = new GameHandler();
	
	@Before
	public void setUp() throws IOException  {
		gameHandler.setPeriod(1);
		gameHandler.setRound(1);
		gameHandler.initializeTheGame();
		gameHandler.loadCardsOnTheMainBoard();
		gameHandler.rollTheDices();
	}
	
	@Test
	public void testAddFamilyMemberToTheTower() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NotEnoughResourcesException, NotEnoughPointsException, CardNotFoundException{
		TestPlayer player = new TestPlayer();
		player.personalMainBoard=gameHandler.mainBoard;
		FamilyMember familyMember = new FamilyMember();
		familyMember.playerColor="green";
		String[][] cardNames = gameHandler.mainBoard.getCardNamesOnTheTowers();
		
		familyMember.color="orange";
		//any colored family member should be able to get a territory of value 1
		assertTrue(gameHandler.addFamilyMemberToTheTower(familyMember, cardNames[3][0], player));
		
		familyMember.color="black";
		//the player can't place two family members on the same place
		assertTrue(!(gameHandler.addFamilyMemberToTheTower(familyMember, cardNames[3][0], player)));
		
		familyMember.color="uncolored";
		//the player can't get any card with the uncolored Family Member and not a servant
		assertTrue(!(gameHandler.addFamilyMemberToTheTower(familyMember, cardNames[3][1], player)));
	}

}
