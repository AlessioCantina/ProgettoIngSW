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
import it.polimi.LM39.server.NetworkPlayer;
import junit.framework.TestCase;
import testmodel.TestPlayer;

public class TestskipFirstTurn extends TestCase{
	GameHandler gameHandler = new GameHandler();
	
	@Before
	public void setUp() throws IOException  {
		gameHandler.setPeriod(1);
		gameHandler.setRound(1);
		gameHandler.initializeTheGame();
	}
	
	@Test
	public void testSkipFirstTurn() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException{
		TestPlayer player = new TestPlayer();
		ArrayList<NetworkPlayer> players = new ArrayList<NetworkPlayer>();
		players.add(player);
		Game game = new Game(1,players);
		
		//reflection to set a private attribute
		Field field = Game.class.getDeclaredField("gameHandler");
		field.setAccessible(true);
		field.set(game, gameHandler);
		
		//reflection to call a private method of Game
		Class[] cArg = new Class[1];
		cArg[0]=NetworkPlayer.class;
		Method method = Game.class.getDeclaredMethod("skipFirstTurn", cArg);
		method.setAccessible(true);
		
		//the player hasn't the skip first turn excommunication so the method returns false
		assertTrue(!((boolean)method.invoke(game,player)));
		
		//set skip first turn excommunication
		player.setExcommunications(9);
		//the player has the skip first turn excommunication so the method returns true
		assertTrue((boolean)method.invoke(game,player));
	}
}
