package testcontroller;

import org.junit.Test;

import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.model.MainBoard;
import junit.framework.TestCase;
import testmodel.FakeTestPlayer;

public class TestresetPlayerPersonalMainBoard extends TestCase{
	
	MainBoard testMainBoard = new MainBoard();
	GameHandler testGameHandler = new GameHandler();
	FakeTestPlayer testPlayer = new FakeTestPlayer();
	
	@Test
	public void testResetPlayerPersonalMainBoard(){
		testGameHandler.mainBoard = testMainBoard;
		testGameHandler.resetPlayerPersonalMainBoard(testPlayer);
		assertNotSame(testMainBoard,testPlayer.personalMainBoard);
	}
}
