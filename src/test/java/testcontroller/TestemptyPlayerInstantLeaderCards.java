package testcontroller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.junit.Test;
import it.polimi.LM39.controller.Game;
import it.polimi.LM39.server.NetworkPlayer;
import junit.framework.TestCase;
import testmodel.TestPlayer;

public class TestemptyPlayerInstantLeaderCards extends TestCase{
	
	@Test
	public void testEmptyPlayerInstantLeaderCards() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		TestPlayer player = new TestPlayer();
		ArrayList<String> leaders = new ArrayList<String>();
		leaders.add("Santa Rita");
		player.setPlayerInstantLeaderCards(leaders);
		ArrayList<NetworkPlayer> players = new ArrayList<NetworkPlayer>();
		players.add(player);
		Game game = new Game(1,players);
		
		//reflection to call a private method of Game
		Class[] cArg = new Class[0];
		Method method = Game.class.getDeclaredMethod("emptyPlayerInstantLeaderCards", cArg);
		method.setAccessible(true);
		method.invoke(game);
		
		//the arraylist PlayerInstantLeaderCards mut be empty
		assertTrue(player.getPlayerInstantLeaderCards().isEmpty());
		
		
	}

}
