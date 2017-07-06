package testcontroller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

public class TestactivateHarvest extends TestCase{
	GameHandler gameHandler = new GameHandler();
	PersonalBoardHandler personalBoardHandler = new PersonalBoardHandler();
	
	@Before
	public void setUp() throws IOException  {
		gameHandler.setPeriod(1);
		gameHandler.setRound(1);
		gameHandler.initializeTheGame();
		personalBoardHandler.setGameHandler(gameHandler);
	}
	
	@Test
	public void testActivateHarvest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughResourcesException, NotEnoughPointsException, InvalidActionTypeException{
		FakeTestPlayer player = new FakeTestPlayer();
		player.playerColor="green";
		//reset coins
		player.resources.setCoins(-player.resources.getCoins());
		player.personalBoard.personalBonusTile.harvestBonus=new ActionBonus();
		//give the Commercial Hub territory to the player
		player.personalBoard.setPossessions(2,"Territory");
		
		//try to activate the Commercial Hub with a family member not high enough
		FamilyMember familyMember = new FamilyMember();
		familyMember.playerColor=player.playerColor;
		familyMember.color="uncolored";
		assertTrue(!(personalBoardHandler.activateHarvest(0, player, familyMember)));
		//the player will not get any resources from the card because the activation failed
		Integer testValue = 0;
		assertEquals(testValue,player.resources.getCoins());
		
		assertTrue(personalBoardHandler.activateHarvest(1, player, familyMember));
		//the player will now get the resources from the card (1 coin)
		testValue = 1;
		assertEquals(testValue,player.resources.getCoins());
	}

}
