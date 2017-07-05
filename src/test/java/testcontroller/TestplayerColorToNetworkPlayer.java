package testcontroller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.junit.Test;
import it.polimi.LM39.controller.Game;
import it.polimi.LM39.server.NetworkPlayer;
import junit.framework.TestCase;
import testmodel.TestPlayer;

public class TestplayerColorToNetworkPlayer extends TestCase{
	
	@Test
	public void testPlayerColorToNetworkPlayer() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		TestPlayer player = new TestPlayer();
		player.playerColor="green";
		ArrayList<NetworkPlayer> players = new ArrayList<NetworkPlayer>();
		players.add(player);
		Game game = new Game(1,players);
		
		//reflection to call a private method of Game
		Class[] cArg = new Class[1];
		cArg[0]=String.class;
		Method method = Game.class.getDeclaredMethod("playerColorToNetworkPlayer", cArg);
		method.setAccessible(true);
		//the player with color green is the player created above
		assertTrue(player == (TestPlayer)method.invoke(game, "green"));
	}

}
