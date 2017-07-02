package junittest;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.GsonReader;
import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.server.Room;
import junit.framework.TestCase;

public class TestGsonReader extends TestCase{
	
	int cardNumber;
	int excommunicationNumber;
	int leaderNumber;
	int faithBonusNumber;
	int marketBonusNumber;
	int towerNumber;
	MainBoard testMainBoard = new MainBoard();
	GsonReader testGson = new GsonReader();
	Room testRoom = new Room();
	
	@Before
	public void setUp(){
		cardNumber = 24;
		excommunicationNumber = 21;
		leaderNumber = 20;
		faithBonusNumber = 16;
		marketBonusNumber = 4;
		towerNumber = 4;
	}
	
	@Test
	public void testFileToCard() throws IOException{
		System.out.println(testMainBoard.marketBonuses.length);
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
	
	@Test
	public void testConfigLoader(){
		GsonReader.configLoader(testRoom);
		if(testRoom.getPlayerMoveTimeout() > 0)
			assertTrue(true);
		else
			assertTrue(false);
		if(testRoom.getRoomTimeout() > 0)
			assertTrue(true);
		else
			assertTrue(false);
	}
}
