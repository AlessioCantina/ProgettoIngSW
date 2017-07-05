package testcontroller;

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
import testmodel.TestPlayer;

public class TestcalculateFinalPoints extends TestCase{
	GameHandler gameHandler = new GameHandler();
	
	@Before
	public void setUp() throws IOException  {
		gameHandler.setPeriod(1);
		gameHandler.setRound(1);
		gameHandler.initializeTheGame();
	}

	@Test
	public void testCalculateFinalPoints1() throws NotEnoughPointsException, NotEnoughResourcesException{
		TestPlayer player = new TestPlayer();
		player.playerColor="green";
		
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
	
	@Test
	public void testCalculateFinalPoints2(){
		TestPlayer player = new TestPlayer();
		player.playerColor="green";
		
		//give 4 territories to the player -> 4 victory point
		player.personalBoard.setPossessions(1, "Territory");
		player.personalBoard.setPossessions(2, "Territory");
		player.personalBoard.setPossessions(3, "Territory");
		player.personalBoard.setPossessions(4, "Territory");
		
		//give 2 character to the player -> 3 victory point
		player.personalBoard.setPossessions(1, "Character");
		player.personalBoard.setPossessions(2, "Character");
		
		ArrayList<NetworkPlayer> players = new ArrayList<NetworkPlayer>();
		players.add(player);
		ArrayList<PlayerRank> finalPoints = gameHandler.calculateFinalPoints(players);
		assertTrue((finalPoints.get(0).playerColor).equals(player.playerColor));
		Integer testValue = 7;
		assertEquals(testValue,finalPoints.get(0).getPlayerPoints());
	}
	@Test
	public void testCalculateFinalPoints3(){
		TestPlayer player = new TestPlayer();
		player.playerColor="green";
		
		//give 5 territories to the player -> 10 victory point
		player.personalBoard.setPossessions(1, "Territory");
		player.personalBoard.setPossessions(2, "Territory");
		player.personalBoard.setPossessions(3, "Territory");
		player.personalBoard.setPossessions(4, "Territory");
		player.personalBoard.setPossessions(5, "Territory");
		
		//give 3 character to the player -> 6 victory point
		player.personalBoard.setPossessions(1, "Character");
		player.personalBoard.setPossessions(2, "Character");
		player.personalBoard.setPossessions(3, "Character");
		
		ArrayList<NetworkPlayer> players = new ArrayList<NetworkPlayer>();
		players.add(player);
		ArrayList<PlayerRank> finalPoints = gameHandler.calculateFinalPoints(players);
		assertTrue((finalPoints.get(0).playerColor).equals(player.playerColor));
		Integer testValue = 16;
		assertEquals(testValue,finalPoints.get(0).getPlayerPoints());
	}
	
	@Test
	public void testCalculateFinalPoints4(){
		TestPlayer player = new TestPlayer();
		player.playerColor="green";
		
		//give 6 territories to the player -> 20 victory point
		player.personalBoard.setPossessions(1, "Territory");
		player.personalBoard.setPossessions(2, "Territory");
		player.personalBoard.setPossessions(3, "Territory");
		player.personalBoard.setPossessions(4, "Territory");
		player.personalBoard.setPossessions(5, "Territory");
		player.personalBoard.setPossessions(6, "Territory");
		
		//give 4 character to the player -> 10 victory point
		player.personalBoard.setPossessions(1, "Character");
		player.personalBoard.setPossessions(2, "Character");
		player.personalBoard.setPossessions(3, "Character");
		player.personalBoard.setPossessions(4, "Character");
		
		ArrayList<NetworkPlayer> players = new ArrayList<NetworkPlayer>();
		players.add(player);
		ArrayList<PlayerRank> finalPoints = gameHandler.calculateFinalPoints(players);
		assertTrue((finalPoints.get(0).playerColor).equals(player.playerColor));
		Integer testValue = 30;
		assertEquals(testValue,finalPoints.get(0).getPlayerPoints());
	}
	
	@Test
	public void testCalculateFinalPoints5(){
		TestPlayer player = new TestPlayer();
		player.playerColor="green";
		
		//give 5 character to the player -> 15 victory point
		player.personalBoard.setPossessions(1, "Character");
		player.personalBoard.setPossessions(2, "Character");
		player.personalBoard.setPossessions(3, "Character");
		player.personalBoard.setPossessions(4, "Character");
		player.personalBoard.setPossessions(5, "Character");
		
		ArrayList<NetworkPlayer> players = new ArrayList<NetworkPlayer>();
		players.add(player);
		ArrayList<PlayerRank> finalPoints = gameHandler.calculateFinalPoints(players);
		assertTrue((finalPoints.get(0).playerColor).equals(player.playerColor));
		Integer testValue = 15;
		assertEquals(testValue,finalPoints.get(0).getPlayerPoints());
	}
	

	@Test
	public void testCalculateFinalPoints6(){
		TestPlayer player = new TestPlayer();
		player.playerColor="green";
		
		//give 6 character to the player -> 21 victory point
		player.personalBoard.setPossessions(1, "Character");
		player.personalBoard.setPossessions(2, "Character");
		player.personalBoard.setPossessions(3, "Character");
		player.personalBoard.setPossessions(4, "Character");
		player.personalBoard.setPossessions(5, "Character");
		player.personalBoard.setPossessions(6, "Character");
		
		ArrayList<NetworkPlayer> players = new ArrayList<NetworkPlayer>();
		players.add(player);
		ArrayList<PlayerRank> finalPoints = gameHandler.calculateFinalPoints(players);
		assertTrue((finalPoints.get(0).playerColor).equals(player.playerColor));
		Integer testValue = 21;
		assertEquals(testValue,finalPoints.get(0).getPlayerPoints());
	}
	
	

}
