package testcontroller;

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
import testmodel.TestPlayer;

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
	public void testAddFamilyMemberToTheTower1() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NotEnoughResourcesException, NotEnoughPointsException, CardNotFoundException{
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
	
	@Test
	public void testAddFamilyMemberToTheTower2() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NotEnoughResourcesException, NotEnoughPointsException, CardNotFoundException{
		TestPlayer player = new TestPlayer();
		player.personalMainBoard=gameHandler.mainBoard;
		FamilyMember familyMember = new FamilyMember();
		familyMember.playerColor="green";
		String[][] cardNames = gameHandler.mainBoard.getCardNamesOnTheTowers();
		
		//get a card with an uncolored family member
		familyMember.color="uncolored";
		familyMember.setServants(1);
		assertTrue(gameHandler.addFamilyMemberToTheTower(familyMember, cardNames[3][0], player));
		
		//get a card on a tower where is present the uncolored family member o the player
		//set player coins to 0
		player.resources.setCoins(-player.resources.getCoins());
		//set player coins to 2
		player.resources.setCoins(2);
		familyMember.color="black";
		familyMember.setServants(2);
		//the player must have 3 coins to perform this action but he has 2
		assertTrue(!(gameHandler.addFamilyMemberToTheTower(familyMember, cardNames[2][0], player)));
		
		//set player coins to 3
		player.resources.setCoins(1);
		//with 3 coins he player can get the card
		assertTrue(gameHandler.addFamilyMemberToTheTower(familyMember, cardNames[2][0], player));
	}
	
	@Test
	public void testAddFamilyMemberToTheTower3() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NotEnoughResourcesException, NotEnoughPointsException, CardNotFoundException{
		TestPlayer player1 = new TestPlayer();
		TestPlayer player2 = new TestPlayer();
		player1.playerColor="green";
		player2.playerColor="yellow";
		player1.personalMainBoard=gameHandler.mainBoard;
		player2.personalMainBoard=gameHandler.mainBoard;
		FamilyMember familyMember1 = new FamilyMember();
		familyMember1.playerColor=player1.playerColor;
		familyMember1.color="black";
		FamilyMember familyMember2 = new FamilyMember();
		familyMember2.playerColor=player2.playerColor;
		familyMember2.color="black";
		familyMember2.setServants(2);
		String[][] cardNames = gameHandler.mainBoard.getCardNamesOnTheTowers();
	
		//add a family member in a tower where there is already a family member of another player
		gameHandler.addFamilyMemberToTheTower(familyMember1, cardNames[3][0], player1);
		player2.resources.setCoins(-player2.resources.getCoins());
		//set player2 coins to 2
		player2.resources.setCoins(2);
		//the player2 must have 3 coins to perform this action but he has 2
		assertTrue(!(gameHandler.addFamilyMemberToTheTower(familyMember2, cardNames[2][0], player2)));
		//set player2 coins to 3
		player2.resources.setCoins(1);
		//with 3 coins he player can get the card
		assertTrue(gameHandler.addFamilyMemberToTheTower(familyMember2, cardNames[2][0], player2));
		
		//add an uncolored family member in a tower where there is one of your colored family members and another player family member
		FamilyMember familyMember3 = new FamilyMember();
		familyMember3.playerColor=player2.playerColor;
		familyMember3.color="uncolored";
		familyMember3.setServants(5);
		//set player2 coins to 3
		player2.resources.setCoins(3);
		assertTrue(gameHandler.addFamilyMemberToTheTower(familyMember3, cardNames[1][0], player2));
	}
	
	@Test
	public void testAddFamilyMemberToTheTower4() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NotEnoughResourcesException, NotEnoughPointsException, CardNotFoundException{
		String[][] cardNames = gameHandler.mainBoard.getCardNamesOnTheTowers();
		TestPlayer player = new TestPlayer();
		player.personalMainBoard=gameHandler.mainBoard;
		FamilyMember familyMember1 = new FamilyMember();
		familyMember1.playerColor="green";
		familyMember1.color="black";
		FamilyMember familyMember2 = new FamilyMember();
		familyMember2.playerColor="green";
		familyMember2.color="white";
		
		//the player can't add two colored family members to the same tower
		gameHandler.addFamilyMemberToTheTower(familyMember1, cardNames[3][0], player);
		familyMember2.setServants(2);
		assertTrue(!(gameHandler.addFamilyMemberToTheTower(familyMember2, cardNames[2][0], player)));
		
	}
	
	@Test
	public void testAddFamilyMemberToTheTower5() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NotEnoughResourcesException, NotEnoughPointsException, CardNotFoundException{
		String[][] cardNames = gameHandler.mainBoard.getCardNamesOnTheTowers();
		TestPlayer player = new TestPlayer();
		player.personalMainBoard=gameHandler.mainBoard;
		FamilyMember familyMember = new FamilyMember();
		familyMember.playerColor="green";
		familyMember.color="black";
		//if the player tries to get a card in a position with a value higher than his family member value
		assertTrue(!(gameHandler.addFamilyMemberToTheTower(familyMember, cardNames[0][0], player)));
	}
	
	

}
