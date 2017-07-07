package testcontroller;

import org.junit.Before;
import org.junit.Test;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.CardNotFoundException;
import junit.framework.TestCase;

public class TestcardNameToInteger extends TestCase{
	GameHandler gameHandler = new GameHandler();

	@Before
	public void setUp() {
		gameHandler.setPeriod(1);
		gameHandler.setRound(1);
		gameHandler.initializeTheGame();
		gameHandler.loadCardsOnTheMainBoard();
	}
	
	@Test
	public void testCardNameToInteger() throws CardNotFoundException{
		//try to get the number of card on the towers
		Integer testValue1 = gameHandler.mainBoard.getCardsOnTheTowers()[0][0];
		Integer cardNumber = gameHandler.cardNameToInteger(gameHandler.mainBoard.territoryMap.get(gameHandler.mainBoard.getCardsOnTheTowers()[0][0]).cardName);
		assertEquals(testValue1,cardNumber);
		
		//try to get the number of an unexisting card
		try{
			gameHandler.cardNameToInteger("unexisting card");
		}
		catch(CardNotFoundException e){
			assertTrue(("Card not found!").equals(e.getMessage()));
		}
		
		
		
		
	}
}
