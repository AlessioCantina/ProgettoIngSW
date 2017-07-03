package junittest;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.PlayerRank;
import it.polimi.LM39.server.NetworkPlayer;
import junit.framework.TestCase;

public class TestcalculateFinalPoints extends TestCase{
	GameHandler gameHandler = new GameHandler();
	
	@Before
	public void setUp() throws IOException  {
		gameHandler.setPeriod(1);
		gameHandler.setRound(1);
		gameHandler.initializeTheGame();
	}

	@Test
	public void testCalculateFinalPoints() throws NotEnoughPointsException, NotEnoughResourcesException{
		TestPlayer player = new TestPlayer();
		player.playerColor="green";
		
		//reset player resources
		player.resources.setCoins(-player.resources.getCoins());
		player.resources.setWoods(-player.resources.getWoods());
		player.resources.setStones(-player.resources.getStones());
		player.resources.setServants(-player.resources.getServants());
		
		//give 3 woods and 3 servants to the player -> 1 victory points
		player.resources.setWoods(3);
		player.resources.setServants(3);
		
		//give 1 military point to the player so that he is the first in the military ranking -> 5 victory points
		ArrayList<PlayerRank> militaryRanking = new ArrayList<PlayerRank>();
		PlayerRank playerRank = new PlayerRank();
		playerRank.playerColor=player.playerColor;
		playerRank.setPlayerPoints(1);
		militaryRanking.add(playerRank);
		gameHandler.mainBoard.rankings.setMilitaryRanking(militaryRanking);
		
		//set 5 victory points to the player
		player.points.setVictory(5);
		
		//set 5 victory points that will be added only now at the end of the game
		player.points.setFinalVictory(5);
		
		//give 3 territories to the player -> 1 victory point
		player.personalBoard.setPossessions(1, "Territory");
		player.personalBoard.setPossessions(2, "Territory");
		player.personalBoard.setPossessions(3, "Territory");
		
		//give 1 character to the player -> 1 victory point
		player.personalBoard.setPossessions(1, "Character");	
		
		ArrayList<NetworkPlayer> players = new ArrayList<NetworkPlayer>();
		players.add(player);
		ArrayList<PlayerRank> finalPoints = gameHandler.calculateFinalPoints(players);
		assertTrue((finalPoints.get(0).playerColor).equals(player.playerColor));
		Integer testValue = 18;
		assertEquals(testValue,finalPoints.get(0).getPlayerPoints());
	}
	

}
