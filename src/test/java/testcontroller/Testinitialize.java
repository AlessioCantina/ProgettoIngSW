package testcontroller;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.junit.Test;
import it.polimi.LM39.controller.Game;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.server.NetworkPlayer;
import junit.framework.TestCase;
import testmodel.FakeTestPlayer;

public class Testinitialize extends TestCase{

	@Test
	public void testInitialize() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException{
		FakeTestPlayer player1 = new FakeTestPlayer();
		FakeTestPlayer player2 = new FakeTestPlayer();
		ArrayList<NetworkPlayer> players = new ArrayList<NetworkPlayer>();
		players.add(player1);
		players.add(player2);
		Game game = new Game(2,players);
		
		//reflection to call a private method of Game
		Class[] cArg = new Class[0];
		Method method = Game.class.getDeclaredMethod("initialize", cArg);
		method.setAccessible(true);
		method.invoke(game);
		
		//reflection to get a private attribute
		Field field = Game.class.getDeclaredField("gameHandler");
		field.setAccessible(true);
		GameHandler gameHandler = (GameHandler)field.get(game);
		
		//in a two players game the harvest and production size is set to 1
		Integer testValue = 1;
		assertEquals(testValue,gameHandler.mainBoard.harvestAndProductionSize);
	}

}
