package testmodel;

import org.junit.Test;

import it.polimi.LM39.model.PlayerRank;
import junit.framework.TestCase;
/**
 * test player rank class
 *
 */
public class TestPlayerRank extends TestCase{
	
	private PlayerRank testPlayerRank = new PlayerRank();
	/**
	 * test get and set points
	 */
	@Test
	public void testPlayerRank(){
		Integer testValue = 4;
		testPlayerRank.setPlayerPoints(testValue);
		assertEquals(testPlayerRank.getPlayerPoints(),testValue);
	}
}
