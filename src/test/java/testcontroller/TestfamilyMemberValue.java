package testcontroller;

import org.junit.Before;
import org.junit.Test;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.model.FamilyMember;
import junit.framework.TestCase;
import testmodel.FakeTestPlayer;

public class TestfamilyMemberValue extends TestCase{
	GameHandler gameHandler = new GameHandler();
	
	@Before
	public void setUp() {
		gameHandler.rollTheDices();
	}
	
	@Test
	public void testFamilyMemberValue() {
		FakeTestPlayer player = new FakeTestPlayer();
		player.personalMainBoard=gameHandler.mainBoard;
		FamilyMember familyMember = new FamilyMember();
		familyMember.color="black";
		familyMember.setServants(1);
		Integer testValue1 = gameHandler.mainBoard.getDiceValues()[0] + 1;
		assertEquals(testValue1,gameHandler.familyMemberValue(familyMember, player));
		
		familyMember = new FamilyMember();
		familyMember.color="white";
		familyMember.setServants(1);
		testValue1 = gameHandler.mainBoard.getDiceValues()[1] + 1;
		assertEquals(testValue1,gameHandler.familyMemberValue(familyMember, player));
		
		familyMember = new FamilyMember();
		familyMember.color="orange";
		familyMember.setServants(1);
		testValue1 = gameHandler.mainBoard.getDiceValues()[2] + 1;
		assertEquals(testValue1,gameHandler.familyMemberValue(familyMember, player));
		
		familyMember = new FamilyMember();
		familyMember.color="uncolored";
		familyMember.setServants(1);
		testValue1 = gameHandler.mainBoard.getDiceValues()[3] + 1;
		assertEquals(testValue1,gameHandler.familyMemberValue(familyMember, player));
	}

}
