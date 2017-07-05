package testcontroller;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.FamilyMember;
import junit.framework.TestCase;
import testmodel.TestPlayer;

public class TestDecoratedMethods extends TestCase{

	private TestPlayer testPlayer = new TestPlayer();
	private GameHandler testGameHandler = new GameHandler();
	private DecoratedMethods testDecorator = new DecoratedMethods();
	private FamilyMember testMember = new FamilyMember();
	
	@Before
	public void setUp() throws IOException{
	    testGameHandler.setPeriod(1);
	    testGameHandler.setRound(1);
	    testGameHandler.initializeTheGame();
	    testMember.color = "black";
	    testMember.playerColor = "green";
	    testGameHandler.rollTheDices();
		testDecorator.setGameHandler(testGameHandler);
		testPlayer.personalMainBoard = testGameHandler.mainBoard;
		testGameHandler.mainBoard.marketSize = 2;
	}
	
	@Test
	public void testAddFamilyMemberToTheMarket() throws IOException, NotEnoughResourcesException, NotEnoughPointsException{
		// go to the first market position, which gives 5 coins
		Integer position = 1;
		Integer expectedCoins = 5;
		Integer expectedServants = 0;
		testDecorator.addFamilyMemberToTheMarket(testMember, position, testPlayer);
		assertEquals(expectedCoins,testPlayer.resources.getCoins());
		// try to go to the market with a family member with 0 action value
		testMember.color="uncolored";
		testDecorator.addFamilyMemberToTheMarket(testMember, position+1, testPlayer);
		assertEquals(expectedServants,testPlayer.resources.getServants());
		
	}
	
	@Test
	public void testAddFamilyMemberToTheMarketFailed() throws IOException, NotEnoughResourcesException, NotEnoughPointsException{
		// go to the third market position, which gives 3 coins and 2 military and is unavailable in 2 players matches
		Integer position = 3;
		Integer expectedCoins = 0;
		testDecorator.addFamilyMemberToTheMarket(testMember, position, testPlayer);
		assertEquals(expectedCoins,testPlayer.resources.getCoins());
		
	}
}
