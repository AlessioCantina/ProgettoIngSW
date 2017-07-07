package testcontroller;

import org.junit.Before;
import org.junit.Test;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.controller.PersonalBoardHandler;
import it.polimi.LM39.exception.InvalidActionTypeException;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.ActionBonus;
import it.polimi.LM39.model.FamilyMember;
import junit.framework.TestCase;
import testmodel.FakeTestPlayer;

public class TestactivateProduction extends TestCase{
	GameHandler gameHandler = new GameHandler();
	PersonalBoardHandler personalBoardHandler = new PersonalBoardHandler();
	
	@Before
	public void setUp(){
		gameHandler.setPeriod(1);
		gameHandler.setRound(1);
		gameHandler.initializeTheGame();
		personalBoardHandler.setGameHandler(gameHandler);
	}
	
	@Test
	public void testActivateProduction() throws NotEnoughResourcesException, ReflectiveOperationException, NotEnoughPointsException, InvalidActionTypeException{
		FakeTestPlayer player = new FakeTestPlayer();
		player.playerColor="green";
		player.setResponse("yes");
		//reset coins
		player.resources.setCoins(-player.resources.getCoins());
		player.personalBoard.personalBonusTile.productionBonus=new ActionBonus();
		//give the Chapel building to the player
		player.personalBoard.setPossessions(1,"Building");
		
		//try to activate the Chapel with a family member not high enough
		FamilyMember familyMember = new FamilyMember();
		familyMember.playerColor=player.playerColor;
		familyMember.color="uncolored";
		assertTrue(!(personalBoardHandler.activateProduction(0, player, familyMember)));
		//the player will not get any resources from the card because the activation failed
		Integer testValue = 0;
		assertEquals(testValue,player.resources.getCoins());
		
		player.resources.setCoins(1);
		assertTrue(personalBoardHandler.activateProduction(2, player, familyMember));
		//the player will now get the resources from the card (1 faith point for 1 coin)
		testValue = 0;
		assertEquals(testValue,player.resources.getCoins());
		testValue=1;
		assertEquals(testValue,player.points.getFaith());
	}

}
