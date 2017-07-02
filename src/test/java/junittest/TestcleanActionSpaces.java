package junittest;

import org.junit.Test;

import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.InvalidActionTypeException;
import junit.framework.TestCase;

public class TestcleanActionSpaces extends TestCase{
	GameHandler gameHandler = new GameHandler();
	
	@Test
	public void testCleanActionSpaces() throws InvalidActionTypeException{
		gameHandler.cleanActionSpaces();
		Integer testValue1 = 0;
		
		Integer size = gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Production").size();
		//test if the production area is free
		assertEquals(testValue1,size);
		
		size = gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnProductionOrHarvest("Harvest").size();
		//test if the harvest area is free
		assertEquals(testValue1,size);
		
		String memberColor;
		for(int i=0;i<4;i++){
			memberColor = gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnTheMarket()[i].color;
			//test if the market is free
			assertTrue(("").equals(memberColor));
		}
		
		size = gameHandler.mainBoard.familyMembersLocation.getFamilyMembersAtTheCouncilPalace().size();
		//test if the council palace is free
		assertEquals(testValue1,size);
		
		
		for(int j=0;j<4;j++)
			for(int i=0;i<4;i++){
				memberColor = gameHandler.mainBoard.familyMembersLocation.getFamilyMembersOnTheTowers()[i][j].color;
				//test if the towers are free
				assertTrue(("").equals(memberColor));
			}

	}
}
