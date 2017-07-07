package testmodel;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.model.PlayerPoints;
import junit.framework.TestCase;
/**
 * player points class test
 *
 */
public class TestPlayerPoints extends TestCase{

	PlayerPoints testPoints;
	
	@Before
	public void setUp(){
		testPoints = new PlayerPoints();
	}
	
	/**
	 * test get and set points with a possible value
	 * @throws NotEnoughPointsException
	 */
	@Test
	public void testPoints() throws NotEnoughPointsException{
		Integer testValue = 3;
		testPoints.setFaith(testValue);
		assertEquals(testPoints.getFaith(),testValue);
		testPoints.setMilitary(testValue);
		assertEquals(testPoints.getMilitary(),testValue);
		testPoints.setVictory(testValue);
		assertEquals(testPoints.getVictory(),testValue);
		testPoints.setFinalVictory(testValue);
		assertEquals(testPoints.getFinalVictory(),testValue);
	}
	/**
	 * test get and set points with an invalid value to test the exception
	 */
	@Test
	public void testPointsException() {
		Integer negativeValue = -1;
	    try {
	        testPoints.setFaith(negativeValue);
	        fail("Expected a NotEnoughPointsException");
	    } catch (NotEnoughPointsException NotEnoughPointsException) {
	        assertTrue(("Not enough faith points!").equals(NotEnoughPointsException.getMessage()));
	    }
	    try {
	        testPoints.setMilitary(negativeValue);
	        fail("Expected a NotEnoughPointsException");
	    } catch (NotEnoughPointsException NotEnoughPointsException) {
	        assertTrue(("Not enough military points!").equals(NotEnoughPointsException.getMessage()));
	    }
	    
	}
}
