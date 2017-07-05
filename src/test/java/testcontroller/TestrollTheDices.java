package testcontroller;

import org.junit.Test;

import it.polimi.LM39.controller.GameHandler;
import junit.framework.TestCase;

public class TestrollTheDices extends TestCase{

	private GameHandler gameHandler = new GameHandler();
	
	@Test
	public void testrRollTheDices() {
		boolean flag = true;
		gameHandler.rollTheDices();
		Integer[] dicesValues = gameHandler.mainBoard.getDiceValues();
		for(int i=0;i<3;i++){
			//check that the dices have values in between 1 and 6
			if(!(dicesValues[i]<=6 && dicesValues[i]>=1))
				flag = false;
		}
		//check that the fourth dice (not existing in the game), used by the uncolored Family Member is set to 0
		if(!(dicesValues[3] == 0))
			flag=false;
		
		assertTrue(flag);
	}
}
