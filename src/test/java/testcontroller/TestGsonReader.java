package testcontroller;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.GsonReader;
import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.server.Room;
import junit.framework.TestCase;
/**
 * test gsonreader class
 *
 */
public class TestGsonReader extends TestCase{
	/**
	 * we expect the gson reader to create hashmaps of those sizes
	 */
	int cardNumber;
	int excommunicationNumber;
	int leaderNumber;
	int faithBonusNumber;
	int marketBonusNumber;
	int towerNumber;
	MainBoard testMainBoard = new MainBoard();
	GsonReader testGson = new GsonReader();
	Room testRoom = new Room();
	
	/**
	 * hashmaps' sizes
	 */
	@Before
	public void setUp(){
		cardNumber = 24;
		excommunicationNumber = 21;
		leaderNumber = 20;
		faithBonusNumber = 16;
		marketBonusNumber = 4;
		towerNumber = 4;
	}
	/**
	 * test the cards' loader
	 * we just need to check if hashmaps are of correct sizes
	 * @throws IOException
	 */
	@Test
	public void testFileToCard() throws IOException{
		testGson.fileToCard(testMainBoard);
		if(testMainBoard.buildingMap.size() == cardNumber &&
		testMainBoard.characterMap.size() == cardNumber &&
		testMainBoard.excommunicationMap.size() == excommunicationNumber &&
		testMainBoard.leaderMap.size() == leaderNumber &&
		testMainBoard.ventureMap.size() == cardNumber &&
		testMainBoard.territoryMap.size() == cardNumber &&
		testMainBoard.faithBonuses.length == faithBonusNumber &&
		testMainBoard.marketBonuses.length == marketBonusNumber &&
		testMainBoard.getTowersBonuses().length == towerNumber)
			assertTrue(true);
		else
			assertTrue(false);
		
	}
	/**
	 * test config loader
	 * since we have no getter for the move timeout, we check only the room timeout
	 * @throws IOException
	 */
	@Test
	public void testConfigLoader() throws IOException{
		GsonReader.configLoader(testRoom);
		if(testRoom.getRoomTimeout() > 0)
			assertTrue(true);
		else
			assertTrue(false);
	}
}
