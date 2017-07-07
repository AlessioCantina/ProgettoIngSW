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

public class TestsearchCard extends TestCase{
	GameHandler gameHandler = new GameHandler();
	
	@Before
	public void setUp() {
		gameHandler.setPeriod(1);
		gameHandler.setRound(1);
		gameHandler.initializeTheGame();
		gameHandler.loadCardsOnTheMainBoard();
	}
	
	@Test
	public void testSearchCard() throws ReflectiveOperationException{
		FakeTestPlayer player = new FakeTestPlayer();
		ArrayList<NetworkPlayer> players = new ArrayList<NetworkPlayer>();
		players.add(player);
		Game game = new Game(1,players);
		
		//reflection to call a private method of Game
		Class[] cArg = new Class[2];
		cArg[0]=String.class;
		cArg[1]=NetworkPlayer.class;
		Method method = Game.class.getDeclaredMethod("searchCard", cArg);
		method.setAccessible(true);
		
		//reflection to set a private attribute
		Field field = Game.class.getDeclaredField("gameHandler");
		field.setAccessible(true);
		field.set(game, gameHandler);
		
		String[][] cardNames = gameHandler.mainBoard.getCardNamesOnTheTowers();
		
		//search a card for any type on the MainBoard
		method.invoke(game,cardNames[0][0],player);
		method.invoke(game,cardNames[0][1],player);
		method.invoke(game,cardNames[0][2],player);
		method.invoke(game,cardNames[0][3],player);
		
		//give a card of any type to the player
		player.personalBoard.setPossessions(1, "Territory");
		player.personalBoard.setPossessions(1, "Character");
		player.personalBoard.setPossessions(1, "Building");
		player.personalBoard.setPossessions(9, "Venture");
		player.personalBoard.setLeader("Santa Rita");
		
		//search the player possessed cards
		method.invoke(game,gameHandler.mainBoard.territoryMap.get(1).cardName,player);
		method.invoke(game,gameHandler.mainBoard.characterMap.get(1).cardName,player);
		method.invoke(game,gameHandler.mainBoard.buildingMap.get(1).cardName,player);
		method.invoke(game,gameHandler.mainBoard.ventureMap.get(9).cardName,player);
		method.invoke(game,"Santa Rita",player);
		
		assertTrue(true);
		
	}

}
