package testcontroller;

import org.junit.Before;
import org.junit.Test;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.InvalidActionTypeException;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.ActionBonus;
import it.polimi.LM39.model.FamilyMember;
import junit.framework.TestCase;
import testmodel.FakeTestPlayer;

public class TestaddFamilyMemberToProductionOrHarvest extends TestCase{
	GameHandler gameHandler = new GameHandler();
	
	@Before
	public void setUp() {
		gameHandler.setPeriod(1);
		gameHandler.setRound(1);
		gameHandler.initializeTheGame();
		gameHandler.loadCardsOnTheMainBoard();
		gameHandler.rollTheDices();
	}
	
	@Test
	public void testAddFamilyMemberToProductionOrHarvest1() throws ReflectiveOperationException, NotEnoughResourcesException, NotEnoughPointsException, InvalidActionTypeException{
		//match of 2 players
		gameHandler.mainBoard.harvestAndProductionSize=1;
		FakeTestPlayer player = new FakeTestPlayer();
		player.personalMainBoard=gameHandler.mainBoard;
		player.personalBoard.personalBonusTile.harvestBonus = new ActionBonus();
		player.personalBoard.personalBonusTile.productionBonus = new ActionBonus();
		FamilyMember familyMember = new FamilyMember();
		familyMember.playerColor="green";
		
		//the player try to add a family member with a value too low
		familyMember.color="uncolored";
		assertTrue(!(gameHandler.addFamilyMemberToProductionOrHarvest(familyMember, gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Harvest"), "Harvest", player)));
		
		//the player add a family member to the Harvest
		familyMember.color="white";
		assertTrue(gameHandler.addFamilyMemberToProductionOrHarvest(familyMember, gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Harvest"), "Harvest", player));
		
		//the player add a family member to the Production
		familyMember.color="black";
		assertTrue(gameHandler.addFamilyMemberToProductionOrHarvest(familyMember, gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Production"), "Production", player));
		
		//the player try to add a second family member in a match of two players
		familyMember.color="uncolored";
		familyMember.setServants(4);
		assertTrue(!(gameHandler.addFamilyMemberToProductionOrHarvest(familyMember,gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Production"), "Production", player)));
	}
	
	@Test
	public void testAddFamilyMemberToProductionOrHarvest2() throws ReflectiveOperationException, NotEnoughResourcesException, NotEnoughPointsException, InvalidActionTypeException{
		//match of >2 players
		gameHandler.mainBoard.harvestAndProductionSize=20;
		FakeTestPlayer player = new FakeTestPlayer();
		player.personalMainBoard=gameHandler.mainBoard;
		player.personalBoard.personalBonusTile.harvestBonus = new ActionBonus();
		FamilyMember familyMember = new FamilyMember();
		familyMember.playerColor="green";
		
		//the player add a family member to the Harvest
		familyMember.color="white";
		assertTrue(gameHandler.addFamilyMemberToProductionOrHarvest(familyMember, gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Harvest"), "Harvest", player));
	
		//the player tries to add to colored family members
		familyMember.color="black";
		familyMember.setServants(3);
		assertTrue(!(gameHandler.addFamilyMemberToProductionOrHarvest(familyMember, gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Harvest"), "Harvest", player)));
	
		//the player tries add a second family member uncolored but with a value too low
		familyMember.color="uncolored";
		assertTrue(!(gameHandler.addFamilyMemberToProductionOrHarvest(familyMember, gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Harvest"), "Harvest", player)));
	
		//the player add a second family member uncolored
		familyMember.color="uncolored";
		familyMember.setServants(4);
		assertTrue(gameHandler.addFamilyMemberToProductionOrHarvest(familyMember, gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Harvest"), "Harvest", player));
		
	}
	
	@Test
	public void testAddFamilyMemberToProductionOrHarvest3() throws ReflectiveOperationException , NotEnoughResourcesException, NotEnoughPointsException, InvalidActionTypeException{
		//match of >2 players
		gameHandler.mainBoard.harvestAndProductionSize=20;
		FakeTestPlayer player = new FakeTestPlayer();
		player.personalMainBoard=gameHandler.mainBoard;
		player.personalBoard.personalBonusTile.harvestBonus = new ActionBonus();
		FamilyMember familyMember = new FamilyMember();
		familyMember.playerColor="green";
		
		//one player add a family member to the Harvest
		familyMember.color="white";
		assertTrue(gameHandler.addFamilyMemberToProductionOrHarvest(familyMember, gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Harvest"), "Harvest", player));
		
		//the player add a second family member
		familyMember.color="uncolored";
		//to be sure the family member can be placed
		familyMember.setServants(4);
		assertTrue(gameHandler.addFamilyMemberToProductionOrHarvest(familyMember, gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Harvest"), "Harvest", player));
		
		//the player tries to add a third family member
		familyMember.color="black";
		//to be sure the family member can be placed
		familyMember.setServants(3);
		assertFalse(gameHandler.addFamilyMemberToProductionOrHarvest(familyMember, gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Harvest"), "Harvest", player));
		
		
		//another player add a family member to the occupied Harvest
		FakeTestPlayer player2 = new FakeTestPlayer();
		player2.personalMainBoard=gameHandler.mainBoard;
		player2.personalBoard.personalBonusTile.harvestBonus = new ActionBonus();
		FamilyMember familyMember2 = new FamilyMember();
		familyMember2.playerColor="blue";
		//to be sure the family member can be placed
		familyMember2.setServants(3);
		familyMember2.color="white";
		assertTrue(gameHandler.addFamilyMemberToProductionOrHarvest(familyMember2, gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Harvest"), "Harvest", player2));
	}
	
	@Test
	public void testAddFamilyMemberToProductionOrHarvest4() throws ReflectiveOperationException, NotEnoughResourcesException, NotEnoughPointsException, InvalidActionTypeException{
		//match of 2 players
		gameHandler.mainBoard.harvestAndProductionSize=1;
		FakeTestPlayer player = new FakeTestPlayer();
		player.personalMainBoard=gameHandler.mainBoard;
		player.personalBoard.personalBonusTile.harvestBonus = new ActionBonus();
		FamilyMember familyMember = new FamilyMember();
		familyMember.playerColor="green";
		
		//one player add a family member to the Harvest
		familyMember.color="white";
		assertTrue(gameHandler.addFamilyMemberToProductionOrHarvest(familyMember, gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Harvest"), "Harvest", player));
	
		//another player tries to add a family member to the occupied Harvest
		FakeTestPlayer player2 = new FakeTestPlayer();
		player2.personalMainBoard=gameHandler.mainBoard;
		player2.personalBoard.personalBonusTile.harvestBonus = new ActionBonus();
		FamilyMember familyMember2 = new FamilyMember();
		familyMember2.playerColor="blue";
		//to be sure the family member has a value high enough
		familyMember2.setServants(3);
		familyMember2.color="white";
		assertFalse(gameHandler.addFamilyMemberToProductionOrHarvest(familyMember2, gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Harvest"), "Harvest", player2));
	}
	
	@Test
	public void testAddFamilyMemberToProductionOrHarvest5() throws ReflectiveOperationException, NotEnoughResourcesException, NotEnoughPointsException, InvalidActionTypeException{
		//match of 2 players
		gameHandler.mainBoard.harvestAndProductionSize=1;
		FakeTestPlayer player = new FakeTestPlayer();
		player.personalMainBoard=gameHandler.mainBoard;
		player.personalBoard.personalBonusTile.productionBonus = new ActionBonus();
		FamilyMember familyMember = new FamilyMember();
		familyMember.playerColor="green";
		
		//one player add a family member to the Production
		familyMember.color="white";
		assertTrue(gameHandler.addFamilyMemberToProductionOrHarvest(familyMember, gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Production"), "Production", player));
	
		//another player tries to add a family member to the occupied Production
		FakeTestPlayer player2 = new FakeTestPlayer();
		player2.personalMainBoard=gameHandler.mainBoard;
		player2.personalBoard.personalBonusTile.productionBonus = new ActionBonus();
		FamilyMember familyMember2 = new FamilyMember();
		familyMember2.playerColor="blue";
		//to be sure the family member has a value high enough
		familyMember2.setServants(3);
		familyMember2.color="white";
		assertFalse(gameHandler.addFamilyMemberToProductionOrHarvest(familyMember2, gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Production"), "Production", player2));
	
	}
	
}
