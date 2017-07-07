package testmodel;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.model.Player;
import junit.framework.TestCase;
/**
 * test player class
 *
 */
public class TestPlayer extends TestCase{
	Player testPlayer;
	
	@Before
	public void setUp(){
		testPlayer = new Player();
	}
	/**
	 * test set and get on player's leader cards
	 */
	@Test
	public void testPlayerLeaderCard(){
		String testLeader = "Ludovico Ariosto";
		testPlayer.setPlayerPlayedLeaderCard(testLeader);
		assertEquals(testLeader,testPlayer.getPlayerPlayedLeaderCards().get(0));
		
	}

	/**
	 * test set and get on player's excommunications
	 */
	@Test
	public void testPlayerExcommunication(){
		Integer testExcommunication = 3;
		testPlayer.setExcommunications(testExcommunication);
		assertEquals(testExcommunication,testPlayer.getExcommunications().get(0));
	}
	/**
	 * test set and get on player's family members
	 */
	@Test
	public void testPlayedFamilyMember(){
		String testFamilyMember = "black";
		testPlayer.setPlayedFamilyMember(testFamilyMember);
		assertEquals(testFamilyMember,testPlayer.getPlayedFamilyMembers().get(0));
		ArrayList<String> testFamilyList = new ArrayList<String>();
		testFamilyList.add(testFamilyMember);
		testPlayer.getPlayedFamilyMembers().clear();
		testPlayer.setPlayedFamilyMembers(testFamilyList);
		assertEquals(testFamilyList,testPlayer.getPlayedFamilyMembers());
	}
	/**
	 * test get and set on player's leaders (this method works on leader which have an instant effect)
	 */
	@Test
	public void testPlayerInstantLeader(){
		String testInstantLeader = "Giovanni dalle Bande Nere";
		testPlayer.setPlayerInstantLeaderCard(testInstantLeader);
		assertEquals(testInstantLeader,testPlayer.getPlayerInstantLeaderCards().get(0));
		ArrayList<String> testInstantLeaders = new ArrayList<String>();
		testInstantLeaders.add(testInstantLeader);
		assertEquals(testInstantLeaders,testPlayer.getPlayerInstantLeaderCards());
	}

}
