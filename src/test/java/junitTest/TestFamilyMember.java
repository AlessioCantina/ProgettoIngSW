package junitTest;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.model.FamilyMember;
import junit.framework.TestCase;

public class TestFamilyMember extends TestCase{
	
	FamilyMember testFamilyMember;

	@Before
	public void setUp(){
		testFamilyMember = new FamilyMember();
	}

	@Test
	public void testServants(){
		int servants = 3;
		testFamilyMember.setServants(servants);
		int getServants = testFamilyMember.getServants();
		assertEquals(servants,getServants);
	}
}
