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
import testmodel.FakeTestPlayer;

public class TestchooseBonusTile extends TestCase{
	GameHandler gameHandler = new GameHandler();
	
	@Before
	public void setUp() throws IOException  {
		gameHandler.setPeriod(1);
		gameHandler.setRound(1);
		gameHandler.initializeTheGame();
	}
	
	@Test
	public void testChooseBonusTile() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException{
		FakeTestPlayer player = new FakeTestPlayer();
		ArrayList<NetworkPlayer> players = new ArrayList<NetworkPlayer>();
		players.add(player);
		Game game = new Game(1,players);
		
		//reflection to set a private attribute
		Field field = Game.class.getDeclaredField("gameHandler");
		field.setAccessible(true);
		field.set(game, gameHandler);
				
		//reflection to call a private method of Game
		Class[] cArg = new Class[0];
		Method method = Game.class.getDeclaredMethod("chooseBonusTile", cArg);
		method.setAccessible(true);
		
		//player decides to get bonus tile 1
		player.setResponse("1");
		method.invoke(game);
	}

}
