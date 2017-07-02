package junittest;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import junit.framework.TestCase;

public class TestdiscardLeader extends TestCase{
	GameHandler gameHandler = new GameHandler();
	
	@Test
	public void testDiscardLeader() throws NotEnoughResourcesException, NotEnoughPointsException{
		//case of a player that has not played the leader card he wants to discard
		TestPlayer player = new TestPlayer();
		ArrayList<String> possessedLeaders = new ArrayList<String>();
		possessedLeaders.add("Francesco Sforza");
		player.personalBoard.setPossessedLeaders(possessedLeaders);
		player.setResponse("1");
		gameHandler.discardLeader(player, "Francesco Sforza");
		Integer testValue1 = 0;
		Integer size = player.personalBoard.getPossessedLeaders().size();
		//check that the leader has been discarded
		assertEquals(testValue1,size);
		
		//case of a player that has played the leader card he wants to discard
		player = new TestPlayer();
		possessedLeaders = new ArrayList<String>();
		possessedLeaders.add("Francesco Sforza");
		player.personalBoard.setPossessedLeaders(possessedLeaders);
		ArrayList<String> playedLeaders = new ArrayList<String>();
		playedLeaders.add("Francesco Sforza");
		player.setPlayerInstantLeaderCards(playedLeaders);
		gameHandler.discardLeader(player, "Francesco Sforza");
		testValue1 = 1;
		size = player.personalBoard.getPossessedLeaders().size();
		//check that the leader has not been discarded
		assertEquals(testValue1,size);
		
		//case of a player that has played the leader card he wants to discard, in this case the leader has an Instant Effect
		player = new TestPlayer();
		possessedLeaders = new ArrayList<String>();
		possessedLeaders.add("Francesco Sforza");
		player.personalBoard.setPossessedLeaders(possessedLeaders);
		player.setPlayerInstantLeaderCard("Francesco Sforza");
		gameHandler.discardLeader(player, "Francesco Sforza");
		testValue1 = 1;
		size = player.personalBoard.getPossessedLeaders().size();
		//check that the leader has not been discarded
		assertEquals(testValue1,size);
		
		//case of a player that does not have the leader card he wants to discard
		player = new TestPlayer();
		possessedLeaders = new ArrayList<String>();
		possessedLeaders.add("Girolamo Savonarola");
		player.personalBoard.setPossessedLeaders(possessedLeaders);
		gameHandler.discardLeader(player, "Francesco Sforza");
		testValue1 = 1;
		size = player.personalBoard.getPossessedLeaders().size();
		//check that the leader has not been discarded
		assertEquals(testValue1,size);
		
		
		
		
		
	}

}
