package testcontroller;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.FamilyMember;
import junit.framework.TestCase;
import testmodel.FakeTestPlayer;

public class TestsetPlayerActionOrder extends TestCase{
	GameHandler gameHandler = new GameHandler();
	
	@Before
	public void setUp() throws IOException {
		gameHandler.setPeriod(1);
		gameHandler.setRound(1);
		gameHandler.initializeTheGame();
		gameHandler.rollTheDices();
	}
	
	@Test
	public void testSetPlayerActionOrder() throws IOException, NotEnoughResourcesException, NotEnoughPointsException{
		FakeTestPlayer player1 = new FakeTestPlayer();
		player1.playerColor="green";
		FakeTestPlayer player2 = new FakeTestPlayer();
		player2.playerColor="yellow";
		ArrayList<String> playerActionOrder = new ArrayList<String>();
		playerActionOrder.add(player1.playerColor);
		playerActionOrder.add(player2.playerColor);
		gameHandler.setPlayersActionOrder(playerActionOrder);
		
		//none of the players is at the Council Palace so the order must remain unchanged
		gameHandler.setPlayerActionOrder(2);
		assertTrue(("green").equals(gameHandler.getPlayersActionOrder().get(0)));
		assertTrue(("yellow").equals(gameHandler.getPlayersActionOrder().get(1)));
		
		//if one of the players is at the Council Palace
		//response for the council favor
		player2.setResponse("1");
		player2.personalMainBoard=gameHandler.mainBoard;
		FamilyMember familyMember = new FamilyMember();
		familyMember.color="black";
		familyMember.playerColor=player2.playerColor;
		gameHandler.addFamilyMemberToTheCouncilPalace(familyMember, player2);
		gameHandler.setPlayerActionOrder(2);
		assertTrue(("green").equals(gameHandler.getPlayersActionOrder().get(1)));
		//the yellow player must be the first in the order now
		assertTrue(("yellow").equals(gameHandler.getPlayersActionOrder().get(0)));
		
		
	}
	
}
