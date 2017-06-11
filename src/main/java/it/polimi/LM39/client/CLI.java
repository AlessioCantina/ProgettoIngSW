package it.polimi.LM39.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.LM39.exception.InvalidActionTypeException;
import it.polimi.LM39.model.ActionBonus;
import it.polimi.LM39.model.Excommunication;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.model.FamilyMembersLocation;
import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.model.PersonalBoard;
import it.polimi.LM39.server.NetworkPlayer;


/*
 * COMMAND LINE INTERFACE
 */
public class CLI extends UserInterface {

	/*
	 * input scanner and logger
	 */
	MainBoard mainBoard;
	BufferedReader userInput;
	Logger logger;
	/*
	 * set the mainboard for the client and print
	 * 
	 */
	public void setCurrentMainBoard(MainBoard mainBoard){
		this.mainBoard = mainBoard;
		this.printMainBoard();
	}
	/*
	 * display player's resources
	 */
	public void displayResources(NetworkPlayer player){
		System.out.println("Available resources:\n");
		System.out.println("Woods:" + player.resources.getWoods());
		System.out.println("Stones:" + player.resources.getStones());
		System.out.println("Coins:" + player.resources.getCoins());
		System.out.println("Servants:" + player.resources.getServants());
	}
    /**
     * Default constructor: initialize the inputstream and the logger
     */
    public CLI() {
    	System.out.println("Cli started");
    	userInput = new BufferedReader(new InputStreamReader(System.in));
		Logger logger = Logger.getLogger(CLI.class.getName());
    }
    /*
     * show action menu
     */
    public void showMenu(){
    	Action.printAvailableActions();
    }
    /*
     * print the mainboard (only towers)
     * 
     */
	public void printMainBoard() {
		String[][] cardsOnTowers = mainBoard.getCardNamesOnTheTowers();
		FamilyMembersLocation familyMemberPositions = mainBoard.familyMembersLocation;
		FamilyMember[][] familyOnTowers = familyMemberPositions.getFamilyMembersOnTheTowers();
		System.out.println("╔══════════════════════╦═══════════════╦══════════════════════╦═══════════════╦══════════════════════╦═══════════════╦══════════════════════╦═══════════════╗%n");
		System.out.printf("║%-22s║%-15s║%-22s║%-15s║%-22s║%-15s║%-22s║%-15s║%n"
		,this.getCardOnTower(cardsOnTowers[0][0]),this.tilePrinter(familyOnTowers[0][0],new int[]{0,0},"tower"),this.getCardOnTower(cardsOnTowers[0][1])
		,this.tilePrinter(familyOnTowers[0][1],new int[]{0,1},"tower"),this.getCardOnTower(cardsOnTowers[0][2])
		,this.tilePrinter(familyOnTowers[0][2],new int[]{0,2},"tower"),this.getCardOnTower(cardsOnTowers[0][3]),this.tilePrinter(familyOnTowers[0][3],new int[]{0,3},"tower"));
		System.out.printf("║                      ║%-15s║                      ║%-15s║                      ║%-15s║                      ║%-15s║%n"
		,this.getFamilyMemberColor(familyOnTowers[0][0]),this.getFamilyMemberColor(familyOnTowers[0][1])
		,this.getFamilyMemberColor(familyOnTowers[0][2]),this.getFamilyMemberColor(familyOnTowers[0][3]));
		System.out.println("╠══════════════════════╬═══════════════╬══════════════════════╬═══════════════╬══════════════════════╬═══════════════╬══════════════════════╬═══════════════╣%n");
		System.out.printf("║%-22s║%-15s║%-22s║%-15s║%-22s║%-15s║%-22s║%-15s║%n"
		,this.getCardOnTower(cardsOnTowers[1][0]),this.tilePrinter(familyOnTowers[1][0],new int[]{1,0},"tower"),this.getCardOnTower(cardsOnTowers[1][1])
		,this.tilePrinter(familyOnTowers[1][1],new int[]{1,1},"tower"),this.getCardOnTower(cardsOnTowers[1][2]),this.tilePrinter(familyOnTowers[1][2],new int[]{1,2},"tower")
		,this.getCardOnTower(cardsOnTowers[1][3]),this.tilePrinter(familyOnTowers[1][3],new int[]{1,3},"tower"));
		System.out.printf("║                      ║%-15s║                      ║%-15s║                      ║%-15s║                      ║%-15s║%n"
		,this.getFamilyMemberColor(familyOnTowers[1][0]),this.getFamilyMemberColor(familyOnTowers[1][1])
		,this.getFamilyMemberColor(familyOnTowers[1][2]),this.getFamilyMemberColor(familyOnTowers[1][3]));
		System.out.println("╠══════════════════════╬═══════════════╬══════════════════════╬═══════════════╬══════════════════════╬═══════════════╬══════════════════════╬═══════════════╣%n");
		System.out.printf("║%-22s║%-15s║%-22s║%-15s║%-22s║%-15s║%-22s║%-15s║%n"
		,this.getCardOnTower(cardsOnTowers[2][0]),this.tilePrinter(familyOnTowers[2][0],new int[]{2,0},"tower"),this.getCardOnTower(cardsOnTowers[2][1])
		,this.tilePrinter(familyOnTowers[2][1],new int[]{2,1},"tower"),this.getCardOnTower(cardsOnTowers[2][2]),this.tilePrinter(familyOnTowers[2][2],new int[]{2,2},"tower")
		,this.getCardOnTower(cardsOnTowers[2][3]),this.tilePrinter(familyOnTowers[2][3],new int[]{2,3},"tower"));
		System.out.printf("║                      ║%-15s║                      ║%-15s║                      ║%-15s║                      ║%-15s║%n"
		,this.getFamilyMemberColor(familyOnTowers[2][0]),this.getFamilyMemberColor(familyOnTowers[2][1])
		,this.getFamilyMemberColor(familyOnTowers[2][2]),this.getFamilyMemberColor(familyOnTowers[2][3]));
		System.out.println("╠══════════════════════╬═══════════════╬══════════════════════╬═══════════════╬══════════════════════╬═══════════════╬══════════════════════╬═══════════════╣%n");
		System.out.printf("║%-22s║%-15s║%-22s║%-15s║%-22s║%-15s║%-22s║%-15s║%n"
		,this.getCardOnTower(cardsOnTowers[3][0]),this.tilePrinter(familyOnTowers[3][0],new int[]{3,0},"tower"),this.getCardOnTower(cardsOnTowers[3][1])
		,this.tilePrinter(familyOnTowers[3][1],new int[]{3,1},"tower"),this.getCardOnTower(cardsOnTowers[3][2]),this.tilePrinter(familyOnTowers[3][2],new int[]{3,2},"tower")
		,this.getCardOnTower(cardsOnTowers[3][3]),this.tilePrinter(familyOnTowers[3][3],new int[]{3,3},"tower"));
		System.out.printf("║                      ║%-15s║                      ║%-15s║                      ║%-15s║                      ║%-15s║%n"
		,this.getFamilyMemberColor(familyOnTowers[3][0]),this.getFamilyMemberColor(familyOnTowers[3][1])
		,this.getFamilyMemberColor(familyOnTowers[3][2]),this.getFamilyMemberColor(familyOnTowers[3][3]));
		System.out.println("╚══════════════════════╩═══════════════╩══════════════════════╩═══════════════╩══════════════════════╩═══════════════╩══════════════════════╩═══════════════╝%n");
		
	}
	/*
	 * print the market zone, if a space is empty print the space bonuses
	 */
	public void printMarket(){
		int marketSize = 4;					//TODO choose a class to put marketSize value (possibly room)
		FamilyMembersLocation location = mainBoard.familyMembersLocation;
		FamilyMember[] market = location.getFamilyMembersOnTheMarket();
		int i = 0;
		System.out.printf("╔═══════════════╦═══════════════╗%n");
		while(i < marketSize){
			System.out.printf("║%-15s║%-15s║%n",this.tilePrinter(market[i], new int[]{i,0}, "market"),this.getFamilyMemberColor(market[i]));
			i++;
		}
		System.out.printf("╚═══════════════╩═══════════════╝%n");
	}
	/*
	 * support method for printmarket and printmainboard , print the family member on the market or the
	 *  bonus if there is no family member
	 */
	public String tilePrinter(FamilyMember familyMember, int[] index, String tileType){
		ActionBonus bonus = new ActionBonus();
		ActionBonus[][] bonusesMatrix;
		if(familyMember == null){
			if(("market").equals(tileType))
				bonus = mainBoard.marketBonuses[index[0]];
			else if(("tower").equals(tileType)){
				bonusesMatrix = mainBoard.getTowersBonuses();
				bonus = bonusesMatrix[index[0]][index[1]];
			}
			 if(bonus.resources.coins != 0)
				 return "coins:" + bonus.resources.coins;
			 else if(bonus.resources.council != 0)
				 return "council:" + bonus.resources.council;
			 else if(bonus.resources.servants != 0)
				 return "servants:" + bonus.resources.servants;
			 else if(bonus.resources.woods != 0)
				 return "woods:" + bonus.resources.woods;
			 else if(bonus.resources.stones != 0)
				 return "stones:" + bonus.resources.stones;
			 else if(bonus.points.faith != 0)
				 return "faith:" + bonus.points.faith;
			 else if(bonus.points.victory != 0)
				 return "victory:" + bonus.points.victory;
			 else if(bonus.points.military != 0)
				 return "military:" + bonus.points.military; 
		}
		else
			return "player" + familyMember.playerColor;
		return "";
	}
	/*
	 * print the council palace with relative bonuses
	 */
	public void printCouncilPalace(){
		FamilyMembersLocation location = mainBoard.familyMembersLocation;
		ArrayList<FamilyMember> councilPalace = location.getFamilyMembersAtTheCouncilPalace();
		int i = 0;
		ActionBonus palaceBonus = mainBoard.councilPalaceBonus;
		System.out.printf("╔════════════════════════════════════╗%n");
		while(i < councilPalace.size()){
			System.out.printf("║%-15s%n",this.getPlayerColor(councilPalace.get(i)));
			System.out.printf("║%-15s%n",this.getFamilyMemberColor(councilPalace.get(i)));
			i++;
		}
		System.out.printf("╚════════════════════════════════════╝%n");
		if(palaceBonus.resources.coins != 0)
			System.out.println("Coins Bonus:" + palaceBonus.resources.coins);
		if(palaceBonus.resources.council != 0)
			System.out.println("Council Privilege Bonus:" + palaceBonus.resources.council);
		if(palaceBonus.resources.woods != 0)
			System.out.println("Woods Bonus:" + palaceBonus.resources.woods);
		if(palaceBonus.resources.stones != 0)
			System.out.println("Stones Bonus:" + palaceBonus.resources.stones);
		if(palaceBonus.resources.servants != 0)
			System.out.println("Servants Bonus:" + palaceBonus.resources.servants);
		if(palaceBonus.points.faith != 0)
			System.out.println("Faith Bonus:" + palaceBonus.points.faith);
		if(palaceBonus.points.military != 0)
			System.out.println("Military Bonus:" + palaceBonus.points.military);
		if(palaceBonus.points.victory != 0)
			System.out.println("Victory Bonus:" + palaceBonus.points.victory);
	}
	/*
	 * print harvest and production area
	 */
	public void printHarvestAndProduction() {
		FamilyMembersLocation location = mainBoard.familyMembersLocation;
		ArrayList<FamilyMember> harvestArea = new ArrayList<FamilyMember>();
		ArrayList<FamilyMember> productionArea = new ArrayList<FamilyMember>();
		try{
			harvestArea = location.getFamilyMembersOnProductionOrHarvest("harvest");
			productionArea = location.getFamilyMembersOnProductionOrHarvest("production");
		}catch(InvalidActionTypeException e){
			logger.log(Level.WARNING, "Invalid Action Type", e);
		}
		int harvestProductionSize = 4;
		int i = 0;
		System.out.printf("╔══════════════════════════════════════╗%n");	//TODO ask if also production/harvest space can have bonuses
		while(i < harvestProductionSize){
			System.out.printf("║%-15s║%n",this.getPlayerColor(harvestArea.get(i)));
			System.out.printf("║%-15s║%n",this.getFamilyMemberColor(harvestArea.get(i)));
			i++;
		}
		System.out.printf("╚══════════════════════════════════════╝%n");
		i = 0;
		System.out.printf("╔══════════════════════════════════════╗%n");	
		while(i < harvestProductionSize){
			System.out.printf("║%-15s║%n",this.getPlayerColor(productionArea.get(i)));
			System.out.printf("║%-15s║%n",this.getFamilyMemberColor(productionArea.get(i)));
			i++;
		}
		System.out.printf("╚══════════════════════════════════════╝%n");
		
	}

	/*
	 * print player's possessed cards, including leader and excommunications
	 */
	public void printPossesedCards(NetworkPlayer player){
		PersonalBoard board = player.personalBoard;
		ArrayList<Integer> buildings = board.getPossessions("Building");
		ArrayList<Integer> territories = board.getPossessions("Territory");
		ArrayList<Integer> ventures = board.getPossessions("Venture");
		ArrayList<Integer> characters = board.getPossessions("Character");
		ArrayList<String> leaders = board.getPossessedLeaders();
		System.out.println("Possesed buildings:%n");
		printCardType("Building",buildings);
		System.out.println("Possesed territories:%n");
		printCardType("Territory",territories);
		System.out.println("Possesed ventures:%n");
		printCardType("Venture",ventures);
		System.out.println("Possesed characters:%n");
		printCardType("Character",characters);
		System.out.println("Possesed leaders:%n");
		printCardType(leaders);
		System.out.println("Possesed excommunications:%n");
		//TODO print excommunication in some way
	}
	/*
	 * support method for printpossessedcards
	 */
	public void printCardType(String cardType, ArrayList<Integer> cardMap){
		int i = 0;
		while (cardMap.size() < i){
			if(("Territory").equals(cardType))
				System.out.println(MainBoard.territoryMap.get(cardMap.get(i)));
			else if(("Building").equals(cardType))
				System.out.println(MainBoard.buildingMap.get(cardMap.get(i)));
			else if(("Venture").equals(cardType))
				System.out.println(MainBoard.ventureMap.get(cardMap.get(i)));
			else if(("Character").equals(cardType))
				System.out.println(MainBoard.characterMap.get(cardMap.get(i)));
			i++;
			System.out.println("%n");
		}
	}
	/*
	 * support method for printpossessedcards
	 */
	public void printCardType(ArrayList<String> cardMap){
		Iterator<String> iterator = cardMap.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next() + "%n");
		}
	}
	/*
	 * print dices values
	 */
	public void printDicesValues(){
		Integer[] diceValues = mainBoard.getDiceValues();
		System.out.println("Black dice:"+ diceValues[0] + "%nWhite dice:"+ diceValues[1] + "%nOrange dice:"+ diceValues[2]);
	}
	/*
	 * print excommunications on the board
	 */
	public void printExcommunication(){
		Integer[] excommunications = mainBoard.excommunicationsOnTheBoard;
		System.out.println("First period:"+ excommunications[0] + "%nSecond period:"+ excommunications[1] + "%nThird period:"+ excommunications[2]);
	}
	/*
	 * support method: return free if there is no family member on the space, otherwise it returns the player's color
	 */
	public String getPlayerColor(FamilyMember familyMember){
		if(familyMember == null)
			return "free";
		return "Player:" + familyMember.playerColor;
	}
	/*
	 * support method: return free if there is no family member on the space, otherwise it returns the family member's color
	 */
	public String getFamilyMemberColor(FamilyMember familyMember){
		if(familyMember == null)
			return "";
		return "Family:" + familyMember.color;
	}
	/*
	 * support method: return no card if there is no card on the selected space otherwise it returns the specific card
	 */
	public String getCardOnTower(String cardOnTower){
		if(cardOnTower == null){
			return "No Card";
		}
		else
			return cardOnTower;
	}
	/*
	 * print the message to the client
	 * 
	 */
	@Override
	public void printMessage(String message) {
		System.out.println(message + "%n");	
	}
	/*
	 * enable client's stream and wait for a response
	 * 
	 */
	public String askClient(NetworkPlayer player){
		String response = "";
		//Action.printAvailableActions();
		String stringController;
		try {
			response = userInput.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, "Can't read input", e);
		}
	//	response = response.replace(" ","");
	//	response = response.toLowerCase();
		stringController = Action.isIn(response);
		if(stringController != Action.CLI.toString())
			return response;
		else if(stringController == Action.CLI.toString())
			selectCLIAction(response,player); 
		return "";
	}
	/*
	 * select the correct CLI method based on client's response
	 */
	public void selectCLIAction(String action, NetworkPlayer player){
		switch(action){
			case "printmainboard":
				this.printMainBoard();				
				break;
			case "printmarket":
				this.printMarket();
				break;
			case "printcouncil":
				this.printCouncilPalace();
				break;
			case "printharvestandproduction":
				this.printHarvestAndProduction();
				break;
			case "printpossesedcards":
				this.printPossesedCards(player);
				break;
			case "printdicesvalues":
				this.printDicesValues();
				break;	
			case "showmenu":
				this.showMenu();
				break;
		}
		this.askClient(player);
	} 
}