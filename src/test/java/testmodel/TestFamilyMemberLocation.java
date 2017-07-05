package testmodel;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.exception.InvalidActionTypeException;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.model.FamilyMembersLocation;
import junit.framework.TestCase;

public class TestFamilyMemberLocation extends TestCase{

	FamilyMember testFamilyMember;
	FamilyMembersLocation testLocation;
	
	@Before
	public void setUp(){
		testFamilyMember = new FamilyMember();
		testLocation = new FamilyMembersLocation();
	}
	
	@Test
	public void testFamilyMemberAtCouncil(){
		ArrayList <FamilyMember> testList = new ArrayList<FamilyMember>();
		testLocation.setFamilyMemberAtTheCouncilPalace(testFamilyMember);
		testList = testLocation.getFamilyMembersAtTheCouncilPalace();
		assertTrue(!testList.isEmpty());
		testList.clear();
		testList.add(testFamilyMember);
		testLocation.setFamilyMembersAtTheCouncilPalace(testList);
		testList = testLocation.getFamilyMembersAtTheCouncilPalace();
		assertTrue(!testList.isEmpty());
	}
	
	@Test
	public void testFamilyMemberOnTheTowers(){
		int testX = 0,testY = 0;
		FamilyMember[][] testMembersOnTheTowers;
		testLocation.setFamilyMemberOnTheTower(testFamilyMember, testX, testY);
		testMembersOnTheTowers = testLocation.getFamilyMembersOnTheTowers();
		if(testMembersOnTheTowers.length != 0)
			assertTrue(true);
		else
			assertTrue(false);
	}
	
	@Test
	public void testFamilyMemberOnTheMarket(){
		int testPosition = 0;
		FamilyMember[] testMembersOnTheMarket;
		testLocation.setFamilyMemberOnTheMarket(testFamilyMember, testPosition);
		testMembersOnTheMarket = testLocation.getFamilyMembersOnTheMarket();
		if(testMembersOnTheMarket.length != 0)
			assertTrue(true);
		else
			assertTrue(false);
	}
	
	@Test
	public void testFamilyMemberOnProductionOrHarvest() throws InvalidActionTypeException{
		ArrayList<FamilyMember> testHarvestPosition = new ArrayList<FamilyMember>();
		String testHarvest = "Harvest";
		testLocation.setFamilyMemberOnProductionOrHarvest(testFamilyMember, testHarvest);
		testHarvestPosition = testLocation.getFamilyMembersOnProductionOrHarvest(testHarvest);
		assertTrue(!testHarvestPosition.isEmpty());
		testHarvestPosition.clear();
		testLocation.changeFamilyMemberOnProductionOrHarvest(testHarvestPosition, testHarvest);
		assertTrue(testHarvestPosition.isEmpty());
		testHarvest = "";
		try{
			testLocation.setFamilyMemberOnProductionOrHarvest(testFamilyMember, testHarvest);
			fail("Expected a InvalidActionTypeException");
		}
		catch(InvalidActionTypeException invalidActionTypeException){
			assertTrue(("The actionType is invalid").equals(invalidActionTypeException.getMessage()));
		}
		try{
			testHarvestPosition = testLocation.getFamilyMembersOnProductionOrHarvest(testHarvest);
			fail("Expected a InvalidActionTypeException");
		}
		catch(InvalidActionTypeException invalidActionTypeException){
			assertTrue(("The actionType is invalid").equals(invalidActionTypeException.getMessage()));
		}
		try{
			testLocation.changeFamilyMemberOnProductionOrHarvest(testHarvestPosition, testHarvest);
			fail("Expected a InvalidActionTypeException");
		}
		catch(InvalidActionTypeException invalidActionTypeException){
			assertTrue(("The actionType is invalid").equals(invalidActionTypeException.getMessage()));
		}
	}
}
