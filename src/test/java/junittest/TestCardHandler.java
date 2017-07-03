package junittest;

import java.io.IOException;
import it.polimi.LM39.model.instanteffect.*;
import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.LM39.controller.CardHandler;
import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.ActionBonus;
import it.polimi.LM39.model.MainBoard;
import junit.framework.TestCase;

public class TestCardHandler extends TestCase{

	int cardNumber;
	int leaderNumber;
	int excommunicationNumber;
	MainBoard testMainBoard = new MainBoard();
    GameHandler testGameHandler = new GameHandler();
    DecoratedMethods testMethods = new DecoratedMethods();
    CardHandler testCardHandler;
    TestPlayer testPlayer = new TestPlayer();
    TestPlayer testPlayer2 = new TestPlayer();
    ActionBonus testBonus = new ActionBonus();
    
    @Before
    public void setUp() throws IOException, NotEnoughResourcesException{
    	
    	
    	testPlayer.personalBoard.personalBonusTile.harvestBonus = testBonus;
    	testPlayer.personalBoard.personalBonusTile.productionBonus = testBonus;
    	testPlayer.resources.setCoins(10);
    	testPlayer2.resources.setCoins(10);
    	testPlayer.resources.setWoods(10);
    	testPlayer2.resources.setWoods(10);
    	testPlayer.resources.setStones(10);
    	testPlayer2.resources.setStones(10);
    	testPlayer.resources.setServants(10);
    	testPlayer2.resources.setServants(10);
    	
    	cardNumber = 24;
    	leaderNumber = 20;
    	excommunicationNumber = 21;
    	
	    testGameHandler.mainBoard = testMainBoard;
    	testGameHandler.rollTheDices();
	    testPlayer.personalMainBoard = testMainBoard;
	    
	    
	    testGameHandler.setPeriod(1);
	    testGameHandler.setRound(1);
	    testGameHandler.initializeTheGame();
	    testGameHandler.loadCardsOnTheMainBoard();
    	testPlayer.setResponse("1");
    	
        testCardHandler = new CardHandler(testGameHandler,testMethods);
    }
    @Test
    public void testGetInfo() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
    	for(int i =1;i<=cardNumber;i++){
    		
    		testCardHandler.getInfo(testGameHandler.mainBoard.territoryMap.get(i).activationReward,testPlayer);
    		testCardHandler.getInfo(testGameHandler.mainBoard.territoryMap.get(i).instantBonuses,testPlayer);testPlayer.setMessage(testGameHandler.mainBoard.territoryMap.get(i).activationCost.toString());

    		testCardHandler.getInfo(testGameHandler.mainBoard.characterMap.get(i).permanentEffect,testPlayer);
    		testCardHandler.getInfo(testGameHandler.mainBoard.characterMap.get(i).instantBonuses,testPlayer);
    		
    		testCardHandler.getInfo(testGameHandler.mainBoard.buildingMap.get(i).activationEffect,testPlayer);
    		
    		testCardHandler.getInfo(testGameHandler.mainBoard.ventureMap.get(i).instant,testPlayer);
    		
    	}
    	for(int i =0;i<leaderNumber;i++){
    		
    		testCardHandler.getInfo(testGameHandler.mainBoard.leaderMap.get(testGameHandler.mainBoard.leaderName.get(i)).effect,testPlayer);
    		testCardHandler.getInfo(testGameHandler.mainBoard.leaderMap.get(testGameHandler.mainBoard.leaderName.get(i)).requestedObjects,testPlayer);
    		
    	}
    	
    	for(int i =1;i<=excommunicationNumber;i++){
    		
    		testCardHandler.getInfo(testGameHandler.mainBoard.excommunicationMap.get(i).effect,testPlayer);

    	}
    	assertTrue(true);    	
    }
    
    @Test
    public void testDoInstantEffect() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
    	Integer expectedCoins = 92;
    	Integer expectedWoods = 60;
    	Integer expectedStones = 58;
    	Integer expectedServants = 60;
    	for(int i =1;i<=cardNumber;i++){
    		testCardHandler.doInstantEffect(testGameHandler.mainBoard.territoryMap.get(i).instantBonuses,testPlayer);
    		testCardHandler.doInstantEffect(testGameHandler.mainBoard.territoryMap.get(i).activationReward,testPlayer);
    		
    		testCardHandler.doInstantEffect(testGameHandler.mainBoard.buildingMap.get(i).activationEffect,testPlayer,testPlayer2);
    		
    		if((testGameHandler.mainBoard.ventureMap.get(i).instant instanceof Points ||
    		testGameHandler.mainBoard.ventureMap.get(i).instant instanceof Resources ||
    		testGameHandler.mainBoard.ventureMap.get(i).instant instanceof ResourcesAndPoints) &&
    		(!("Raising a Statue").equals(testGameHandler.mainBoard.ventureMap.get(i).cardName)))
    			testCardHandler.doInstantEffect(testGameHandler.mainBoard.ventureMap.get(i).instant,testPlayer);
    		
    		if((testGameHandler.mainBoard.characterMap.get(i).instantBonuses instanceof Points ||
    		testGameHandler.mainBoard.characterMap.get(i).instantBonuses instanceof Resources ||
    		testGameHandler.mainBoard.characterMap.get(i).instantBonuses instanceof ResourcesAndPoints ||
    		testGameHandler.mainBoard.characterMap.get(i).instantBonuses instanceof NoInstantEffect ||
    		testGameHandler.mainBoard.characterMap.get(i).instantBonuses instanceof VictoryForCard) &&
    		(!("Royal Messenger").equals(testGameHandler.mainBoard.characterMap.get(i).cardName)))
    			testCardHandler.doInstantEffect(testGameHandler.mainBoard.characterMap.get(i).instantBonuses,testPlayer);
    	}
    	
    	assertEquals(expectedCoins,testPlayer.resources.getCoins());
    	assertEquals(expectedWoods,testPlayer.resources.getWoods());
    	assertEquals(expectedStones,testPlayer.resources.getStones());
    	assertEquals(expectedServants,testPlayer.resources.getServants());
    }
    
    @Test
    public void testCheckLeaderRequestedObjects() throws SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
    	for(int i =0;i<leaderNumber;i++){	
    		testCardHandler.checkLeaderRequestedObject(testGameHandler.mainBoard.leaderMap.get(testGameHandler.mainBoard.leaderName.get(i)).requestedObjects,testPlayer);	
    	}
    	assertTrue(true);
    }
    
    public void testActivateCharacter() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	for(int i =1;i<=cardNumber;i++)
    		testCardHandler.activateCharacter(testGameHandler.mainBoard.characterMap.get(i).permanentEffect,testPlayer);
    	assertTrue(true);
    }
    
    @Test
    public void testActivateExcommunication() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	for(int i =1;i<=excommunicationNumber;i++)
    		testCardHandler.activateExcommunication(testGameHandler.mainBoard.excommunicationMap.get(i).effect,testPlayer);

    	assertTrue(true);
    }
    
    @Test
    public void testActivateLeader() throws SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException{
    	for(int i =0;i<leaderNumber;i++){

    		System.out.println(testGameHandler.mainBoard.leaderName.get(i));
    		if(("Federico da Montefeltro").equals(testGameHandler.mainBoard.leaderName.get(i)))
    			testPlayer.setResponse("black");
    		if(("Leonardo da Vinci").equals(testGameHandler.mainBoard.leaderName.get(i)) ||
    		("Francesco Sforza").equals(testGameHandler.mainBoard.leaderName.get(i)))
    			testPlayer.setResponse("no");
    		testCardHandler.activateLeader(testGameHandler.mainBoard.leaderMap.get(testGameHandler.mainBoard.leaderName.get(i)).effect,testPlayer,testGameHandler.mainBoard.leaderName.get(i));
    		
    	}
    	assertTrue(true);
    	
    }
    
}
