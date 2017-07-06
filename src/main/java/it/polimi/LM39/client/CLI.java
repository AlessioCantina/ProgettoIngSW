package it.polimi.LM39.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.LM39.exception.InvalidActionTypeException;
import it.polimi.LM39.model.ActionBonus;
import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.model.FamilyMembersLocation;
import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.model.PersonalBoard;
import it.polimi.LM39.model.PlayerRank;
import it.polimi.LM39.model.Rankings;
import it.polimi.LM39.server.NetworkPlayer;


/**
 * COMMAND LINE INTERFACE
 */
public class CLI extends UserInterface{

	/**
	 * input scanner, logger, mainboard and timeout variables
	 * 2 boolean needed to use timeout when necessary , and to display helper at the start
	 */
	private MainBoard mainBoard;
	private BufferedReader userInput;
	private Logger logger = Logger.getLogger(CLI.class.getName());
	private boolean displayAction = false;
	private boolean timeOutActive = false;
	private boolean excommunicationRequest = false;
	private boolean error = false;
	private Long moveTimeout;
	
	/**
	 * set the clientmove timeout
	 *
	 */
	@Override
	public void setMoveTimeout(Long timeout){
		this.moveTimeout = timeout;
	}

	/**
	 * set the current mainboard
	 */
	@Override
	public void setCurrentMainBoard(MainBoard mainBoard){
		if(this.mainBoard == null)
			displayAction = true;
		this.mainBoard = mainBoard;
		if(!error)
			this.displaymainboard();
	}
	/**
	 * display player's current resources
	 * @param player
	 */
	public void displayresources(NetworkPlayer player){
		System.out.println("Available resources:\n");
		System.out.println("Woods:" + player.resources.getWoods());
		System.out.println("Stones:" + player.resources.getStones());
		System.out.println("Coins:" + player.resources.getCoins());
		System.out.println("Servants:" + player.resources.getServants());
		System.out.printf("%n");
	}
    /**
     * Default constructor: initialize the inputstream
     */
    public CLI() {
    	userInput = new BufferedReader(new InputStreamReader(System.in));
    }
    /**
     * show action menu
     */
    public void displaymenu(){
    	Action.printAvailableActions();
    }
    /**
     * print the mainboard (only towers)
     */
	public void displaymainboard() {
		String[][] cardsOnTowers = mainBoard.getCardNamesOnTheTowers();
		Integer [][] cardsNumberOnTowers = mainBoard.getCardsOnTheTowers();
		FamilyMembersLocation familyMemberPositions = mainBoard.familyMembersLocation;
		FamilyMember[][] familyOnTowers = familyMemberPositions.getFamilyMembersOnTheTowers();
		System.out.println("╔═══════════════════════╦════════════════╦═══════════════════════╦════════════════╦═══════════════════════╦════════════════╦═══════════════════════╦════════════════╗");
		System.out.printf("║%-23s║%-16s║%-23s║%-16s║%-23s║%-16s║%-23s║%-16s║%n"
		,this.getCardOnTower(cardsOnTowers[0][0],cardsNumberOnTowers[0][0])," "+this.tilePrinter(familyOnTowers[0][0],new int[]{0,0},"tower").substring(0, this.tilePrinter(familyOnTowers[0][0],new int[]{0,0},"tower").indexOf(' '))
		,this.getCardOnTower(cardsOnTowers[0][1],cardsNumberOnTowers[0][1])," "+this.tilePrinter(familyOnTowers[0][1],new int[]{0,1},"tower").substring(0, this.tilePrinter(familyOnTowers[0][1],new int[]{0,1},"tower").indexOf(' '))
		,this.getCardOnTower(cardsOnTowers[0][2],cardsNumberOnTowers[0][2])," "+this.tilePrinter(familyOnTowers[0][2],new int[]{0,2},"tower").substring(0, this.tilePrinter(familyOnTowers[0][2],new int[]{0,2},"tower").indexOf(' '))
		,this.getCardOnTower(cardsOnTowers[0][3],cardsNumberOnTowers[0][3])," "+this.tilePrinter(familyOnTowers[0][3],new int[]{0,3},"tower").substring(0, this.tilePrinter(familyOnTowers[0][3],new int[]{0,3},"tower").indexOf(' ')));
		System.out.printf("║                       ║%-16s║                       ║%-16s║                       ║%-16s║                       ║%-16s║%n"
		,this.tilePrinter(familyOnTowers[0][0], new int[]{0,0},"familymember").substring(this.tilePrinter(familyOnTowers[0][0], new int[]{0,0},"familymember").indexOf(' '))
		,this.tilePrinter(familyOnTowers[0][1], new int[]{0,1},"familymember").substring(this.tilePrinter(familyOnTowers[0][1], new int[]{0,1},"familymember").indexOf(' '))
		,this.tilePrinter(familyOnTowers[0][2], new int[]{0,2},"familymember").substring(this.tilePrinter(familyOnTowers[0][2], new int[]{0,2},"familymember").indexOf(' '))
		,this.tilePrinter(familyOnTowers[0][3], new int[]{0,3},"familymember").substring(this.tilePrinter(familyOnTowers[0][3], new int[]{0,3},"familymember").indexOf(' ')));
		System.out.println("╠═══════════════════════╬════════════════╬═══════════════════════╬════════════════╬═══════════════════════╬════════════════╬═══════════════════════╬════════════════╣");
		System.out.printf("║%-23s║%-16s║%-23s║%-16s║%-23s║%-16s║%-23s║%-16s║%n"
		,this.getCardOnTower(cardsOnTowers[1][0],cardsNumberOnTowers[1][0])," "+this.tilePrinter(familyOnTowers[1][0],new int[]{1,0},"tower").substring(0, this.tilePrinter(familyOnTowers[1][0],new int[]{1,0},"tower").indexOf(' '))
		,this.getCardOnTower(cardsOnTowers[1][1],cardsNumberOnTowers[1][1])," "+this.tilePrinter(familyOnTowers[1][1],new int[]{1,1},"tower").substring(0, this.tilePrinter(familyOnTowers[1][1],new int[]{1,1},"tower").indexOf(' '))
		,this.getCardOnTower(cardsOnTowers[1][2],cardsNumberOnTowers[1][2])," "+this.tilePrinter(familyOnTowers[1][2],new int[]{1,2},"tower").substring(0, this.tilePrinter(familyOnTowers[1][2],new int[]{1,2},"tower").indexOf(' '))
		,this.getCardOnTower(cardsOnTowers[1][3],cardsNumberOnTowers[1][3])," "+this.tilePrinter(familyOnTowers[1][3],new int[]{1,3},"tower").substring(0, this.tilePrinter(familyOnTowers[1][3],new int[]{1,3},"tower").indexOf(' ')));
		System.out.printf("║                       ║%-16s║                       ║%-16s║                       ║%-16s║                       ║%-16s║%n"
		,this.tilePrinter(familyOnTowers[1][0], new int[]{1,0},"familymember").substring(this.tilePrinter(familyOnTowers[1][0], new int[]{1,0},"familymember").indexOf(' '))
		,this.tilePrinter(familyOnTowers[1][1], new int[]{1,1},"familymember").substring(this.tilePrinter(familyOnTowers[1][1], new int[]{1,1},"familymember").indexOf(' '))
		,this.tilePrinter(familyOnTowers[1][2], new int[]{1,2},"familymember").substring(this.tilePrinter(familyOnTowers[1][2], new int[]{1,2},"familymember").indexOf(' '))
		,this.tilePrinter(familyOnTowers[1][3], new int[]{1,3},"familymember").substring(this.tilePrinter(familyOnTowers[1][3], new int[]{1,3},"familymember").indexOf(' ')));
		System.out.println("╠═══════════════════════╬════════════════╬═══════════════════════╬════════════════╬═══════════════════════╬════════════════╬═══════════════════════╬════════════════╣");
		System.out.printf("║%-23s║%-16s║%-23s║%-16s║%-23s║%-16s║%-23s║%-16s║%n"
		,this.getCardOnTower(cardsOnTowers[2][0],cardsNumberOnTowers[2][0])," "+this.tilePrinter(familyOnTowers[2][0],new int[]{2,0},"tower").substring(0, this.tilePrinter(familyOnTowers[2][0],new int[]{2,0},"tower").indexOf(' '))
		,this.getCardOnTower(cardsOnTowers[2][1],cardsNumberOnTowers[2][1])," "+this.tilePrinter(familyOnTowers[2][1],new int[]{2,1},"tower").substring(0, this.tilePrinter(familyOnTowers[2][1],new int[]{2,1},"tower").indexOf(' '))
		,this.getCardOnTower(cardsOnTowers[2][2],cardsNumberOnTowers[2][2])," "+this.tilePrinter(familyOnTowers[2][2],new int[]{2,2},"tower").substring(0, this.tilePrinter(familyOnTowers[2][2],new int[]{2,2},"tower").indexOf(' '))
		,this.getCardOnTower(cardsOnTowers[2][3],cardsNumberOnTowers[2][3])," "+this.tilePrinter(familyOnTowers[2][3],new int[]{2,3},"tower").substring(0, this.tilePrinter(familyOnTowers[2][3],new int[]{2,3},"tower").indexOf(' ')));
		System.out.printf("║                       ║%-16s║                       ║%-16s║                       ║%-16s║                       ║%-16s║%n"
		,this.tilePrinter(familyOnTowers[2][0], new int[]{2,0},"familymember").substring(this.tilePrinter(familyOnTowers[2][0], new int[]{2,0},"familymember").indexOf(' '))
		,this.tilePrinter(familyOnTowers[2][1], new int[]{2,1},"familymember").substring(this.tilePrinter(familyOnTowers[2][1], new int[]{2,1},"familymember").indexOf(' '))
		,this.tilePrinter(familyOnTowers[2][2], new int[]{2,2},"familymember").substring(this.tilePrinter(familyOnTowers[2][2], new int[]{2,2},"familymember").indexOf(' '))
		,this.tilePrinter(familyOnTowers[2][3], new int[]{2,3},"familymember").substring(this.tilePrinter(familyOnTowers[2][3], new int[]{2,3},"familymember").indexOf(' ')));
		System.out.println("╠═══════════════════════╬════════════════╬═══════════════════════╬════════════════╬═══════════════════════╬════════════════╬═══════════════════════╬════════════════╣");
		System.out.printf("║%-23s║%-16s║%-23s║%-16s║%-23s║%-16s║%-23s║%-16s║%n"
		,this.getCardOnTower(cardsOnTowers[3][0],cardsNumberOnTowers[3][0])," "+this.tilePrinter(familyOnTowers[3][0],new int[]{3,0},"tower").substring(0, this.tilePrinter(familyOnTowers[3][0],new int[]{3,0},"tower").indexOf(' '))
		,this.getCardOnTower(cardsOnTowers[3][1],cardsNumberOnTowers[3][1])," "+this.tilePrinter(familyOnTowers[3][1],new int[]{3,1},"tower").substring(0, this.tilePrinter(familyOnTowers[3][1],new int[]{3,1},"tower").indexOf(' '))
		,this.getCardOnTower(cardsOnTowers[3][2],cardsNumberOnTowers[3][2])," "+this.tilePrinter(familyOnTowers[3][2],new int[]{3,2},"tower").substring(0, this.tilePrinter(familyOnTowers[3][2],new int[]{3,2},"tower").indexOf(' '))
		,this.getCardOnTower(cardsOnTowers[3][3],cardsNumberOnTowers[3][3])," "+this.tilePrinter(familyOnTowers[3][3],new int[]{3,3},"tower").substring(0, this.tilePrinter(familyOnTowers[3][3],new int[]{3,3},"tower").indexOf(' ')));
		System.out.printf("║                       ║%-16s║                       ║%-16s║                       ║%-16s║                       ║%-16s║%n"
		,this.tilePrinter(familyOnTowers[3][0], new int[]{3,0},"familymember").substring(this.tilePrinter(familyOnTowers[3][0], new int[]{3,0},"familymember").indexOf(' '))
		,this.tilePrinter(familyOnTowers[3][1], new int[]{3,1},"familymember").substring(this.tilePrinter(familyOnTowers[3][1], new int[]{3,1},"familymember").indexOf(' '))
		,this.tilePrinter(familyOnTowers[3][2], new int[]{3,2},"familymember").substring(this.tilePrinter(familyOnTowers[3][2], new int[]{3,2},"familymember").indexOf(' '))
		,this.tilePrinter(familyOnTowers[3][3], new int[]{3,3},"familymember").substring(this.tilePrinter(familyOnTowers[3][3], new int[]{3,3},"familymember").indexOf(' ')));
		System.out.println("╚═══════════════════════╩════════════════╩═══════════════════════╩════════════════╩═══════════════════════╩════════════════╩═══════════════════════╩════════════════╝");
		System.out.printf("%n");
	}
	/**
	 * print the market zone, if a space is empty print the space bonuses
	 */
	public void displaymarket(){
		FamilyMembersLocation location = mainBoard.familyMembersLocation;
		FamilyMember[] market = location.getFamilyMembersOnTheMarket();
		int i = 0;
		while(i < mainBoard.marketSize){
			System.out.println("╔═══════════════════════════════╗");
			if(("").equals(this.getFamilyMemberColor(market[i])))
				System.out.printf("║%-31s║%n",this.tilePrinter(market[i], new int[]{i,0}, "market"));
			else
				System.out.printf("║%-15s %-15s║%n",this.tilePrinter(market[i], new int[]{i,0}, "market"),this.getFamilyMemberColor(market[i]));
			System.out.println("╚═══════════════════════════════╝");
			i++;
		}
		System.out.printf("%n");
	}
	/**
	 * print faith track, victory track and military track
	 */
	public void displayrankings(){
		Rankings rankings = mainBoard.rankings;
		ArrayList<PlayerRank> victoryRank = rankings.getVictoryRanking();
		ArrayList<PlayerRank> militaryRank = rankings.getMilitaryRanking();
		ArrayList<PlayerRank> faithRank = rankings.getFaithRanking();
		Iterator<PlayerRank> iterator = victoryRank.iterator();
		PlayerRank currentRank;
		System.out.println("Victory Rankings:");
		while(iterator.hasNext()){
			currentRank = iterator.next();
			System.out.println(currentRank.playerColor + ": " + currentRank.getPlayerPoints());
		}
		iterator = militaryRank.iterator();
		System.out.println("Military Rankings:");
		while(iterator.hasNext()){
			currentRank = iterator.next();
			System.out.println(currentRank.playerColor + ": " + currentRank.getPlayerPoints());
		}
		iterator = faithRank.iterator();
		System.out.println("Faith Rankings:");
		while(iterator.hasNext()){
			currentRank = iterator.next();
			System.out.println(currentRank.playerColor + ": " + currentRank.getPlayerPoints());
		}
		System.out.printf("%n");
	}
	/**
	 * this method print the family member on the selected position
	 * if there is no family member it will print the position bonus
	 * @param familyMember
	 * @param index
	 * @param tileType
	 * @return
	 */
	private String tilePrinter(FamilyMember familyMember, int[] index, String tileType){
		ActionBonus bonus = new ActionBonus();
		ActionBonus[][] bonusesMatrix;
		String bonuses;
		if(("").equals(familyMember.playerColor)){
			if(("market").equals(tileType))
				bonus = mainBoard.marketBonuses[index[0]];
			else if(("tower").equals(tileType) || ("familymember").equals(tileType)){
				bonusesMatrix = mainBoard.getTowersBonuses();
				bonus = bonusesMatrix[index[0]][index[1]];
			}
			if((" ").equals(this.getResources(bonus.resources)) && !(" ").equals(this.getPoints(bonus.points)))
				bonuses = this.getPoints(bonus.points);
			else
				bonuses = this.getResources(bonus.resources).concat(this.getPoints(bonus.points));
			return bonuses;
		}
		else if(("tower").equals(tileType) || ("market").equals(tileType))
			return "player:" + familyMember.playerColor + " ";
		else
			return " color:" + familyMember.color;
	}
	/**
	 * print the council palace with relative bonuses
	 */
	public void displaycouncilpalace(){
		FamilyMembersLocation location = mainBoard.familyMembersLocation;
		ArrayList<FamilyMember> councilPalace = location.getFamilyMembersAtTheCouncilPalace();
		int i = 0;
		ActionBonus palaceBonus = mainBoard.councilPalaceBonus;
		if(!councilPalace.isEmpty()){
			System.out.println("╔════════════════════════════════════╗");
			while(i < councilPalace.size()){
				System.out.printf("║%-15s%n",this.getPlayerColor(councilPalace.get(i)));
				System.out.printf("║%-15s%n",this.getFamilyMemberColor(councilPalace.get(i)));
				i++;
			}
			System.out.println("╚════════════════════════════════════╝");
		}
		else
			System.out.println("Nobody is at Council Palace");
		System.out.println(this.getResources(palaceBonus.resources));
		System.out.println(this.getPoints(palaceBonus.points));
		System.out.printf("%n");
	}
	/**
	 * support method which generate a string with the available resources
	 */
	private String getResources(CardResources resources){
		 String resourcesString = "";
		 if(resources.coins != 0)
			 resourcesString = resourcesString.concat("coins:" + resources.coins + " "); 
		 if(resources.council != 0)
			 resourcesString = resourcesString.concat("favor:" + resources.council + " ");
		 if(resources.servants != 0)
			 resourcesString = resourcesString.concat("servants:" + resources.servants + " ");
		 if(resources.woods != 0)
			 resourcesString = resourcesString.concat("woods:" + resources.woods + " ");
		 if(resources.stones != 0)
			 resourcesString = resourcesString.concat("stones:" + resources.stones + " ");
		 if(("").equals(resourcesString))
			 return " ";
		 return resourcesString;
	}
	/**
	 * support method which generate a string with the available points
	 */
	private String getPoints(CardPoints points){
		String pointsString = "";
		 if(points.faith != 0)
			 pointsString = pointsString.concat("faith:" + points.faith + " ");
		 if(points.victory != 0)
			 pointsString = pointsString.concat("victory:" + points.victory + " ");
		 if(points.military != 0)
			 pointsString = pointsString.concat("military:" + points.military + " "); 
		 if(("").equals(pointsString))
			 return " ";
		 return pointsString;
	}
	/**
	 * print harvest and production area
	 */
	public void displayharvestandproduction() {
		FamilyMembersLocation location = mainBoard.familyMembersLocation;
		ArrayList<FamilyMember> harvestArea = new ArrayList<FamilyMember>();
		ArrayList<FamilyMember> productionArea = new ArrayList<FamilyMember>();
		try{
			harvestArea = location.getFamilyMembersOnProductionOrHarvest("Harvest");
			productionArea = location.getFamilyMembersOnProductionOrHarvest("Production");
		}catch(InvalidActionTypeException e){
			logger.log(Level.WARNING, "Invalid Action Type", e);
		}
		int i = 0;
		if(!harvestArea.isEmpty()){
			System.out.println("═════════════════════════════");
			while(i < harvestArea.size()){
				System.out.printf("║%-15s║%n",this.getPlayerColor(harvestArea.get(i)));
				System.out.printf("║%-15s║%n",this.getFamilyMemberColor(harvestArea.get(i)));
				i++;
			}
			System.out.println("═════════════════════════════");
		}
		else
			System.out.println("Nobody is at harvest area");
		if(!productionArea.isEmpty()){
			i = 0;
			System.out.println("╔══════════════════════╗");	
			while(i < productionArea.size()){
				System.out.printf("║%-15s║%n",this.getPlayerColor(productionArea.get(i)));
				System.out.printf("║%-15s║%n",this.getFamilyMemberColor(productionArea.get(i)));
				i++;
			}
			System.out.println("╚══════════════════════╝");
		}
		else
			System.out.println("Nobody is at production area");
		System.out.printf("%n");
	}

	/**
	 * print player's possessed cards, including leaders
	 */
	public void displaypossessedcards(NetworkPlayer player){
		PersonalBoard board = player.personalBoard;
		ArrayList<Integer> buildings = board.getPossessions("Building");
		ArrayList<Integer> territories = board.getPossessions("Territory");
		ArrayList<Integer> ventures = board.getPossessions("Venture");
		ArrayList<Integer> characters = board.getPossessions("Character");
		ArrayList<String> leaders = board.getPossessedLeaders();
		System.out.println("Possessed buildings:");
		printCardType("Building",buildings);
		System.out.println("Possessed territories:");
		printCardType("Territory",territories);
		System.out.println("Possessed ventures:");
		printCardType("Venture",ventures);
		System.out.println("Possessed characters:");
		printCardType("Character",characters);
		System.out.println("Possessed leaders:");
		printCardType(leaders);
	}
	/**
	 * support method for printpossessedcards
	 *  print the card name based on card type
	 */
	private void printCardType(String cardType, ArrayList<Integer> cardMap){
		int i = 0;
		while (i < cardMap.size()){
			if(("Territory").equals(cardType))
				System.out.println(mainBoard.territoryMap.get(cardMap.get(i)).cardName);
			else if(("Building").equals(cardType))
				System.out.println(mainBoard.buildingMap.get(cardMap.get(i)).cardName);
			else if(("Venture").equals(cardType))
				System.out.println(mainBoard.ventureMap.get(cardMap.get(i)).cardName);
			else if(("Character").equals(cardType))
				System.out.println(mainBoard.characterMap.get(cardMap.get(i)).cardName);
			i++;
		}
	}
	/**
	 * support method for printpossessedcards
	 * used for leaders to print them
	 */
	private void printCardType(ArrayList<String> cardMap){
		Iterator<String> iterator = cardMap.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		System.out.printf("%n");
	}
	/**
	 * print dices values
	 */
	public void displaydicesvalues(){
		Integer[] diceValues = mainBoard.getDiceValues();
		System.out.printf("Black dice:"+ diceValues[0] + "%n"
		+"White dice:"+ diceValues[1] + "%n"
		+"Orange dice:"+ diceValues[2]);
		System.out.printf("%n");
	}
	/**
	 * support method: return free if there is no family member on the space, otherwise it returns the player's color
	 * @param familyMember
	 * @return
	 */
	private String getPlayerColor(FamilyMember familyMember){
		if(("").equals(familyMember.playerColor))
			return "free";
		return "Player:" + familyMember.playerColor;
	}
	/**
	 * support method: return free if there is no family member on the space, otherwise it returns the family member's color
	 * @param familyMember
	 * @return
	 */
	private String getFamilyMemberColor(FamilyMember familyMember){
		if(("").equals(familyMember.color))
			return "";
		return "Color:" + familyMember.color;
	}
	/**
	 * support method: return no card if there is no card on the selected space otherwise it returns the specific card
	 * @param cardOnTower
	 * @param cardNumber
	 * @return
	 */
	private String getCardOnTower(String cardOnTower, Integer cardNumber){
		if(cardNumber == -1)
			return "";
		else
			return cardOnTower;
	}
	/**
	 * print the message to the client
	 * 
	 */
	@Override
	public void printMessage(String message) {
		System.out.println(message);	
		if(("What action do you want to perform?").equals(message) || ("Do you want to support the Church? yes or no").equals(message))
			timeOutActive = true;
		else
			timeOutActive = false;
	}
	
	/**
	 * enable client's stream and wait for a response
	 * code from https://stackoverflow.com/questions/12803151/how-to-interrupt-a-scanner-nextline-call	(future)
	 * reflection used on client's response to automatically call the selected method 
	 */
	public String askClient(NetworkPlayer player){
    	FutureTask<String> readNextLine = new FutureTask<String>(() -> {
    		  return userInput.readLine();
    		});
      	ExecutorService executor = Executors.newFixedThreadPool(2);
      	executor.execute(readNextLine);
		String response = "";
		String stringController;
		if(displayAction){
			System.out.println("Write display menu to show available actions");
			displayAction = false;
		}
		try {
			if(timeOutActive)
				response = readNextLine.get(moveTimeout, TimeUnit.MILLISECONDS);
			else
				response = readNextLine.get();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			logger.log(Level.WARNING, "Future exception", e);
		} catch (TimeoutException e) {
			response = "timeout";
			moveTimeout = 0L;
		}
		response = response.trim();
		stringController = Action.isIn(response);
		if(stringController == Action.CLI.toString()){
			Method lMethod = null;
			try {
				lMethod = (this.getClass().getMethod(response.replace(" ", ""), new Class[] {}));
				lMethod.invoke(this);
			} catch (NoSuchMethodException e) {
				try {
					lMethod = (this.getClass().getMethod(response.replace(" ", ""), new Class[] {NetworkPlayer.class}));
					lMethod.invoke(this,player);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException reflectionError) {
					logger.log(Level.WARNING,"Wrong input",reflectionError);
				}
			} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException reflection2Error){
				logger.log(Level.WARNING,"Reflection error",reflection2Error);
			}
			response = this.askClient(player);
		}else
			return response;
		return response;
	}
}