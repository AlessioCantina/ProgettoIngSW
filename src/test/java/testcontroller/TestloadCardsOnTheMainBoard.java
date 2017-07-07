package testcontroller;

import org.junit.Before;
import org.junit.Test;
import it.polimi.LM39.controller.GameHandler;
import junit.framework.TestCase;

public class TestloadCardsOnTheMainBoard extends TestCase{
	GameHandler gameHandler = new GameHandler();
	
	@Before
	public void setUp() {
		gameHandler.setPeriod(1);
		gameHandler.setRound(1);
		gameHandler.initializeTheGame();
	}
	
	@Test
	public void testLoadCardsOnTheMainBoard() {
		gameHandler.loadCardsOnTheMainBoard();
		assertTrue(checkPeriod(1));
		
		gameHandler.setRound(2);
		gameHandler.loadCardsOnTheMainBoard();
		assertTrue(checkPeriod(1));
		
		gameHandler.setPeriod(2);
		gameHandler.setRound(1);
		gameHandler.loadCardsOnTheMainBoard();
		assertTrue(checkPeriod(2));
		
		gameHandler.setRound(2);
		gameHandler.loadCardsOnTheMainBoard();
		assertTrue(checkPeriod(2));
		
		gameHandler.setPeriod(3);
		gameHandler.setRound(1);
		gameHandler.loadCardsOnTheMainBoard();
		assertTrue(checkPeriod(3));
		
		gameHandler.setRound(2);
		gameHandler.loadCardsOnTheMainBoard();
		assertTrue(checkPeriod(3));
		
	}
	
	private boolean checkPeriod(Integer period){
		Integer[][] cards = gameHandler.mainBoard.getCardsOnTheTowers();
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++){
				if(period==1 && (cards[i][j]>8 || cards[i][j]<1))
					return false;
				else if (period==2 && (cards[i][j]>16 || cards[i][j]<9))
					return false;
				else if (period==3 && (cards[i][j]>24 || cards[i][j]<16))
					return false;
			}
		return true;
	}

}
