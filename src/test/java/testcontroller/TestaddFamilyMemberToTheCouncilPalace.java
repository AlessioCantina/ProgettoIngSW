package testcontroller;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.FamilyMember;
import junit.framework.TestCase;
import testmodel.FakeTestPlayer;

public class TestaddFamilyMemberToTheCouncilPalace extends TestCase{
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
	public void testAddFamilyMemberToTheCouncilPalace() throws IOException, NotEnoughResourcesException, NotEnoughPointsException{
		FakeTestPlayer player = new FakeTestPlayer();
		player.personalMainBoard=gameHandler.mainBoard;
		FamilyMember familyMember = new FamilyMember();
		familyMember.playerColor="green";
		//the player decide to take the first of the Council Tower bonuses
		player.setResponse("1");
		
		familyMember.color="orange";
		//any colored family member should be able to go to the council palace
		assertTrue(gameHandler.addFamilyMemberToTheCouncilPalace(familyMember, player));
		
		familyMember.color="uncolored";
		//an uncolored family member without a servant can't go to the council palace
		assertTrue(!(gameHandler.addFamilyMemberToTheCouncilPalace(familyMember, player)));
	}
}
