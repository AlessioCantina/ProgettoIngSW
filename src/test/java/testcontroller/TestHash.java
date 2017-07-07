package testcontroller;

import org.junit.Test;

import it.polimi.LM39.credentials.Hash;
import junit.framework.TestCase;

public class TestHash extends TestCase{
	
	@Test
	public void testHash(){
		//registering a player with nickname and password
		Hash.register("nickname", "password");
		
		//logging with the correct nickname and password
		assertTrue(Hash.login("nickname", "password"));
		
		//trying to login with a wrong password
		assertFalse(Hash.login("nickname", "wrong_password"));
		
		//unregister a nickname
		Hash.unregister("nickname");
		
		//nickname and password were correct but the player was unregistered
		assertFalse(Hash.login("nickname", "password"));
	}

}
