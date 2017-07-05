package testcontroller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import it.polimi.LM39.controller.Game;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.server.NetworkPlayer;
import junit.framework.TestCase;
import testmodel.TestPlayer;

public class TestloadRankings extends TestCase{
	GameHandler gameHandler = new GameHandler();
	
	@Before
	public void setUp() throws IOException  {
		gameHandler.setPeriod(1);
		gameHandler.setRound(1);
		gameHandler.initializeTheGame();
	}
	
	@Test
	public void testLoadRanking() throws NoSuchMethodException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, NotEnoughPointsException, InvocationTargetException{
		TestPlayer player = new TestPlayer();
		player.playerColor="green";
		player.points.setFaith(5);
		player.points.setMilitary(6);
		player.points.setVictory(7);
		ArrayList<NetworkPlayer> players = new ArrayList<NetworkPlayer>();
		players.add(player);
		Game game = new Game(1,players);
		
		//reflection to call a private method of Game
		Class[] cArg = new Class[0];
		Method method = Game.class.getDeclaredMethod("loadRankings", cArg);
		method.setAccessible(true);
		
		//reflection to set a private attribute
		Field field = Game.class.getDeclaredField("gameHandler");
		field.setAccessible(true);
		field.set(game, gameHandler);
		
		method.invoke(game);
		
		//get the private object gameHandler
		GameHandler gameHandler = (GameHandler)field.get(game);
		
		//if loaded correctly the ranking should contains the green player
		assertTrue(("green").equals(gameHandler.mainBoard.rankings.getFaithRanking().get(0).playerColor));
		assertTrue(("green").equals(gameHandler.mainBoard.rankings.getMilitaryRanking().get(0).playerColor));
		assertTrue(("green").equals(gameHandler.mainBoard.rankings.getVictoryRanking().get(0).playerColor));
	}

}
