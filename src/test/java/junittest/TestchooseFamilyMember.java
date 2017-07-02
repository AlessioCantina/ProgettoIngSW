package junittest;

import org.junit.Test;

import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.model.FamilyMember;
import junit.framework.TestCase;

public class TestchooseFamilyMember extends TestCase{
	GameHandler gameHandler = new GameHandler();
	
	@Test
	public void testChooseFamilyMember(){
		TestPlayer player = new TestPlayer();
		player.playerColor="green";
		player.setResponse("orange");
		FamilyMember familyMember = gameHandler.chooseFamilyMember(player);
		assertTrue(familyMember.playerColor.equals(player.playerColor));
		assertTrue(("orange").equals(familyMember.color));
		
		//if the player tries to reuse an already played Family Member he is forced to choose a valid one (this is untestable)
		
	}

}
