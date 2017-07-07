package testcontroller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import it.polimi.LM39.controller.Game;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.server.NetworkPlayer;
import junit.framework.TestCase;
import testmodel.FakeTestPlayer;

public class TestupdatePersonalMainBoards extends TestCase{
	GameHandler gameHandler = new GameHandler();
	
	@Before
	public void setUp() {
		gameHandler.setPeriod(1);
		gameHandler.setRound(1);
		gameHandler.initializeTheGame();
	}
	
	@Test
	public void testUpdatePersonalMainBoards() throws ReflectiveOperationException{
		FakeTestPlayer player = new FakeTestPlayer();
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
		Method method = Game.class.getDeclaredMethod("updatePersonalMainBoards", cArg);
		method.setAccessible(true);
		method.invoke(game, player);
		assertTrue(true);
	
	
	
	}

}
