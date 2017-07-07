package testmodel;

import org.junit.Test;

import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.Council;
import junit.framework.TestCase;

/**
 * test the enum council
 *
 */
public class TestCouncil extends TestCase{

	/**
	 * test council getresources and getpoints
	 */
	@Test
	public void testCouncil(){
		//first bonus is 1 wood, 1 stone
		Integer expectedBonus = 1;
		CardResources testResourcesBonus = Council.getResources(1);

		assertEquals(expectedBonus,testResourcesBonus.woods);
		assertEquals(expectedBonus,testResourcesBonus.stones);
		
		//fifth bonus is 1 faith
		CardPoints testPointsBonus = Council.getPoints(5);
		assertEquals(expectedBonus,testPointsBonus.faith);
		
		//there is no tenth council bonus, so it will return null
		Object testNull = Council.getPoints(10);
		assertEquals(null,testNull);
		
		//same for getresources
		testNull = Council.getResources(-1);
		assertEquals(null,testNull);
		
		//needed to fully cover if statement
		testNull = Council.getPoints(2);
		assertEquals(null,testNull);
		
		testNull = Council.getResources(10);
		assertEquals(null,testNull);
	}
}
