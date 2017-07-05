package testcontroller;

import org.junit.Test;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.CardResources;
import junit.framework.TestCase;
import testmodel.TestPlayer;

public class TestsubCardResources extends TestCase{
	GameHandler gameHandler = new GameHandler();
	
	@Test
	public void testSubCardResources() throws NotEnoughResourcesException{
		TestPlayer player = new TestPlayer();
		CardResources resources = new CardResources();
		player.resources.setCoins(1);
		player.resources.setWoods(1);
		player.resources.setStones(1);
		player.resources.setServants(1);
		resources.coins=1;
		resources.woods=0;
		resources.stones=0;
		resources.servants=1;
		gameHandler.subCardResources(resources, player);
		Integer testValue1=0;
		Integer testValue2=1;
		assertEquals(testValue1,player.resources.getCoins());
		assertEquals(testValue2,player.resources.getWoods());
		assertEquals(testValue2,player.resources.getStones());
		assertEquals(testValue1,player.resources.getServants());
		
		//in case the player has not enough resources an exception will be thrown and he will not pay anything
		player = new TestPlayer();
		resources = new CardResources();
		player.resources.setCoins(1);
		player.resources.setWoods(1);
		player.resources.setStones(1);
		player.resources.setServants(1);
		resources.coins=1;
		resources.woods=0;
		resources.stones=0;
		resources.servants=2;
		testValue1=1;
		try{
		gameHandler.subCardResources(resources, player);
		}
		catch (NotEnoughResourcesException e){
			assertTrue(("Not enough servants!").equals(e.getMessage()));
			assertEquals(testValue1,player.resources.getCoins());
			assertEquals(testValue1,player.resources.getWoods());
			assertEquals(testValue1,player.resources.getStones());
			assertEquals(testValue1,player.resources.getServants());
		}
	}
}
