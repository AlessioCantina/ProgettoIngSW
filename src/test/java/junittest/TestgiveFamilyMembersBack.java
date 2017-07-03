package junittest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.junit.Test;
import it.polimi.LM39.controller.Game;
import it.polimi.LM39.server.NetworkPlayer;
import junit.framework.TestCase;

public class TestgiveFamilyMembersBack extends TestCase{
	
	@Test
	public void testGiveFamilyMembersBack() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		TestPlayer player = new TestPlayer();
		//the player has played the black familyMember
		player.setPlayedFamilyMember("black");
		ArrayList<NetworkPlayer> players = new ArrayList<NetworkPlayer>();
		players.add(player);
		Game game = new Game(1,players);
		
		//reflection to call a private method of Game
		Class[] cArg = new Class[0];
		Method method = Game.class.getDeclaredMethod("giveFamilyMembersBack", cArg);
		method.setAccessible(true);
		method.invoke(game);
		
		//after giveFamilyMembersBack is called the player has all his family members back
		assertTrue(player.getPlayedFamilyMembers().isEmpty());
		
	}

}
