package testcontroller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import org.junit.Test;
import it.polimi.LM39.controller.Game;
import it.polimi.LM39.server.NetworkPlayer;
import junit.framework.TestCase;
import testmodel.FakeTestPlayer;

public class TestsetPlayersColors extends TestCase{

	@Test
	public void testSetPlayersColors() throws ReflectiveOperationException{
		FakeTestPlayer player = new FakeTestPlayer();
		ArrayList<NetworkPlayer> players = new ArrayList<NetworkPlayer>();
		players.add(player);
		Game game = new Game(1,players);
		
		//reflection to call a private method of Game
		Class[] cArg = new Class[1];
		cArg[0] = ArrayList.class;
		Method method = Game.class.getDeclaredMethod("setPlayersColor", cArg);
		method.setAccessible(true);
		method.invoke(game, players);
		
		//the first player is always green
		assertTrue(("green").equals(player.playerColor));
	}
}
