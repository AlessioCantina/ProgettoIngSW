package it.polimi.LM39.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.LM39.exception.InvalidActionTypeException;
import it.polimi.LM39.model.ActionBonus;
import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.Effect;
import it.polimi.LM39.model.Excommunication;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.model.FamilyMembersLocation;
import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.model.PersonalBoard;
import it.polimi.LM39.model.PlayerRank;
import it.polimi.LM39.model.Rankings;
import it.polimi.LM39.model.characterpermanenteffect.CardActionDiscount;
import it.polimi.LM39.model.characterpermanenteffect.CardActionResourcesDiscount;
import it.polimi.LM39.model.characterpermanenteffect.HarvestProductionBoost;
import it.polimi.LM39.model.characterpermanenteffect.NoBoardBonuses;
import it.polimi.LM39.model.characterpermanenteffect.NoCharacterPermanentEffect;
import it.polimi.LM39.model.excommunicationpermanenteffect.CardActionMalus;
import it.polimi.LM39.model.excommunicationpermanenteffect.DiceMalus;
import it.polimi.LM39.model.excommunicationpermanenteffect.HarvestProductionMalus;
import it.polimi.LM39.model.excommunicationpermanenteffect.MalusForResources;
import it.polimi.LM39.model.excommunicationpermanenteffect.MalusForResourcesCost;
import it.polimi.LM39.model.excommunicationpermanenteffect.MalusVictoryForMilitary;
import it.polimi.LM39.model.excommunicationpermanenteffect.MilitaryPointsMalus;
import it.polimi.LM39.model.excommunicationpermanenteffect.NoMarket;
import it.polimi.LM39.model.excommunicationpermanenteffect.ResourcesMalus;
import it.polimi.LM39.model.excommunicationpermanenteffect.ServantsMalus;
import it.polimi.LM39.model.excommunicationpermanenteffect.SkipFirstTurn;
import it.polimi.LM39.model.excommunicationpermanenteffect.VictoryMalus;
import it.polimi.LM39.model.instanteffect.CoinForCard;
import it.polimi.LM39.model.instanteffect.DoublePointsTransformation;
import it.polimi.LM39.model.instanteffect.DoubleResourcesTransformation;
import it.polimi.LM39.model.instanteffect.GetCard;
import it.polimi.LM39.model.instanteffect.GetCardAndPoints;
import it.polimi.LM39.model.instanteffect.GetCardAndResources;
import it.polimi.LM39.model.instanteffect.GetDiscountedCard;
import it.polimi.LM39.model.instanteffect.HarvestProductionAction;
import it.polimi.LM39.model.instanteffect.HarvestProductionAndPoints;
import it.polimi.LM39.model.instanteffect.NoInstantEffect;
import it.polimi.LM39.model.instanteffect.Points;
import it.polimi.LM39.model.instanteffect.PointsTransformation;
import it.polimi.LM39.model.instanteffect.Resources;
import it.polimi.LM39.model.instanteffect.ResourcesAndPoints;
import it.polimi.LM39.model.instanteffect.ResourcesAndPointsTransformation;
import it.polimi.LM39.model.instanteffect.ResourcesTransformation;
import it.polimi.LM39.model.instanteffect.SetFamilyMember;
import it.polimi.LM39.model.instanteffect.VictoryForCard;
import it.polimi.LM39.model.instanteffect.VictoryForMilitary;
import it.polimi.LM39.model.leaderobject.RequestedCard;
import it.polimi.LM39.model.leaderobject.RequestedCardPointsResources;
import it.polimi.LM39.model.leaderobject.RequestedPoints;
import it.polimi.LM39.model.leaderobject.RequestedResources;
import it.polimi.LM39.model.leaderobject.RequestedSameCard;
import it.polimi.LM39.model.leaderobject.RequestedTwoCards;
import it.polimi.LM39.model.leaderpermanenteffect.AlreadyOccupiedTowerDiscount;
import it.polimi.LM39.model.leaderpermanenteffect.CardCoinDiscount;
import it.polimi.LM39.model.leaderpermanenteffect.CopyLeaderAbility;
import it.polimi.LM39.model.leaderpermanenteffect.DoubleResourcesFromDevelopment;
import it.polimi.LM39.model.leaderpermanenteffect.NoMilitaryRequirementsForTerritory;
import it.polimi.LM39.model.leaderpermanenteffect.PlaceFamilyMemberOnOccupiedSpace;
import it.polimi.LM39.model.leaderpermanenteffect.SetColoredDicesValues;
import it.polimi.LM39.model.leaderpermanenteffect.UncoloredMemberBonus;
import it.polimi.LM39.model.leaderpermanenteffect.VictoryForSupportingTheChurch;
import it.polimi.LM39.server.NetworkPlayer;


/*
 * COMMAND LINE INTERFACE
 */
public class CLI extends UserInterface{

	/*
	 * input scanner and logger
	 */
	private MainBoard mainBoard;
	private BufferedReader userInput;
	private Logger logger;
	private boolean firstMessage = true;
	/*
	 * set the mainboard for the client and print
	 * 
	 */
	public void setCurrentMainBoard(MainBoard mainBoard){
		this.mainBoard = mainBoard;
		this.displaymainboard();
		this.firstMessage = true;
	}
	/*
	 * display player's resources
	 */
	public void displayresources(NetworkPlayer player){
		System.out.println("Available resources:\n");
		System.out.println("Woods:" + player.resources.getWoods());
		System.out.println("Stones:" + player.resources.getStones());
		System.out.println("Coins:" + player.resources.getCoins());
		System.out.println("Servants:" + player.resources.getServants());
		System.out.printf("%n");
		return;
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
    public void displaymenu(){
    	Action.printAvailableActions();
    }
    /*
     * print the mainboard (only towers)
     * 
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
	/*
	 * print the market zone, if a space is empty print the space bonuses
	 */
	public void displaymarket(){
		FamilyMembersLocation location = mainBoard.familyMembersLocation;
		FamilyMember[] market = location.getFamilyMembersOnTheMarket();
		int i = 0;
		while(i < mainBoard.marketSize){
			System.out.println("╔═══════════════════════════════╗");
			if(("").equals(this.getFamilyMemberColor(market[i])))
				System.out.printf("║%-30s║%n",this.tilePrinter(market[i], new int[]{i,0}, "market"));
			else
				System.out.printf("║%-15s %-15s║%n",this.tilePrinter(market[i], new int[]{i,0}, "market"),this.getFamilyMemberColor(market[i]));
			System.out.println("╚═══════════════════════════════╝");
			i++;
		}
		System.out.printf("%n");
	}
	/*
	 * print faith track, victory track and military track
	 */
	public void displayrankings(){
		Rankings rankings = mainBoard.getRankings();
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
	/*
	 * support method for printmarket and printmainboard , print the family member on the market or the
	 *  bonus if there is no family member
	 */
	public String tilePrinter(FamilyMember familyMember, int[] index, String tileType){
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
				bonuses = this.getResources(bonus.resources).concat((this.getPoints(bonus.points)));
			return bonuses;
		}
		else if(("tower").equals(tileType) || ("market").equals(tileType))
			return "player:" + familyMember.playerColor + " ";
		else
			return " color:" + familyMember.color;
	}
	/*
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
	/*
	 * support method which generate a string with the available resources
	 */
	public String getResources(CardResources resources){
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
	/*
	 * support method which generate a string with the available points
	 */
	public String getPoints(CardPoints points){
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
	/*
	 * print harvest and production area
	 */
	public void displayharvestandproduction() {
		FamilyMembersLocation location = mainBoard.familyMembersLocation;
		ArrayList<FamilyMember> harvestArea = new ArrayList<FamilyMember>();
		ArrayList<FamilyMember> productionArea = new ArrayList<FamilyMember>();
		try{
			harvestArea = location.getFamilyMembersOnProductionOrHarvest("harvest");
			productionArea = location.getFamilyMembersOnProductionOrHarvest("production");
		}catch(InvalidActionTypeException e){
			logger.log(Level.WARNING, "Invalid Action Type", e);
		}
		int i = 0;
		System.out.println("╔══════════════════════╗");	//TODO ask if also production/harvest space can have bonuses
		while(i < mainBoard.harvestAndProductionSize){
			System.out.printf("║%-15s║%n",this.getPlayerColor(harvestArea.get(i)));
			System.out.printf("║%-15s║%n",this.getFamilyMemberColor(harvestArea.get(i)));
			i++;
		}
		System.out.println("╚══════════════════════╝");
		i = 0;
		System.out.println("╔══════════════════════╗");	
		while(i < mainBoard.harvestAndProductionSize){
			System.out.printf("║%-15s║%n",this.getPlayerColor(productionArea.get(i)));
			System.out.printf("║%-15s║%n",this.getFamilyMemberColor(productionArea.get(i)));
			i++;
		}
		System.out.println("╚══════════════════════╝");
		System.out.printf("%n");
	}

	/*
	 * print player's possessed cards, including leader and excommunications
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
		System.out.println("Possessed excommunications:");
		System.out.printf("%n");
		//TODO print excommunication in some way
	}
	/*
	 * support method for printpossessedcards
	 */
	public void printCardType(String cardType, ArrayList<Integer> cardMap){
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
	/*
	 * support method for printpossessedcards
	 */
	public void printCardType(ArrayList<String> cardMap){
		Iterator<String> iterator = cardMap.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		System.out.printf("%n");
	}
	/*
	 * print dices values
	 */
	public void displaydicesvalues(){
		Integer[] diceValues = mainBoard.getDiceValues();
		System.out.printf("Black dice:"+ diceValues[0] + "%n"
		+"White dice:"+ diceValues[1] + "%n"
		+"Orange dice:"+ diceValues[2]);
		System.out.printf("%n");
	}
	/*
	 * print excommunications on the board
	 */
	public void displayexcommunication(){
		Integer[] excommunications = mainBoard.excommunicationsOnTheBoard;
		System.out.printf("First period:"+ excommunications[0] + "%n"
		+"Second period:"+ excommunications[1] + "%n"
		+"Third period:"+ excommunications[2]);
		System.out.printf("%n");
	}
	/*
	 * support method: return free if there is no family member on the space, otherwise it returns the player's color
	 */
	public String getPlayerColor(FamilyMember familyMember){
		if(("").equals(familyMember.playerColor))
			return "free";
		return "Player:" + familyMember.playerColor;
	}
	/*
	 * support method: return free if there is no family member on the space, otherwise it returns the family member's color
	 */
	public String getFamilyMemberColor(FamilyMember familyMember){
		if(("").equals(familyMember.color))
			return "";
		return "Color:" + familyMember.color;
	}
	/*
	 * support method: return no card if there is no card on the selected space otherwise it returns the specific card
	 */
	public String getCardOnTower(String cardOnTower, Integer cardNumber){
		if(cardNumber == -1)
			return "";
		else
			return cardOnTower;
	}
	/*
	 * print the message to the client
	 * 
	 */
	@Override
	public void printMessage(String message) {
		System.out.println(message);	
	}
	/*
	 * enable client's stream and wait for a response
	 * 
	 */
	public String askClient(NetworkPlayer player){
		
		String response = "";
	/*	if(firstMessage)
			Action.printAvailableActions();	*/
		String stringController;
			try {
				response = userInput.readLine();
			}catch (IOException e) {
				logger.log(Level.WARNING,"Unable to read input",e);
			}
			response = response.toLowerCase();
			stringController = Action.isIn(response);
			if(stringController == Action.CONTROLLER.toString() && firstMessage){
				firstMessage = false;
				return response;
			}
			else if((stringController == Action.CONTROLLER_SPECIAL.toString() && firstMessage)
					||(stringController != Action.CLI.toString() && !firstMessage))
				return response;
			else if(stringController == Action.CLI.toString()){
				Method lMethod = null;
				try {
					lMethod = (this.getClass().getMethod(response.replace(" ", ""), new Class[] {}));
					lMethod.invoke(this);
				} catch (NoSuchMethodException e) {
					try {
							lMethod = (this.getClass().getMethod(response.replace(" ", ""), new Class[] {NetworkPlayer.class}));
							lMethod.invoke(this,player);
						}catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException reflectionError) {
						logger.log(Level.WARNING,"Wrong input",reflectionError);
					}
				} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException reflection2Error){
					logger.log(Level.WARNING,"Reflection error",reflection2Error);
				}
				response = this.askClient(player);
			}
			else if(("Action Not Available").equals(stringController)){
				System.out.println("Action not recognized, please retry");
				response = this.askClient(player);
			}
		return response;
	}

/*	public void displaycardinfo(){
		String response = "";
		System.out.println("What card do you want to get info about?");
		try {
			response = userInput.readLine();
		} catch (IOException e) {
			logger.log(Level.WARNING,"Unable to read input",e);
		}
		//TODO nella mainboard ho sia numeri e nomi, cerco semplicemente il nome (cardNametoInteger gamehandler intero + stringa)
		//creo arraylist di nomi da arraylist di numeri 
	}
	public void getInfo(Effect effect) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException	{
		 Class[] cArg = new Class[2];
		 cArg[0] = effect.getClass();
		 cArg[1] = effect.getClass();
		 Method lMethod = (this.getClass().getMethod("getInfo",cArg));
		 lMethod.invoke(this,effect);
	 }
	 
	/*
	 * InstantEffect info
	 
	
	public void getInfo (CoinForCard effect){
		System.out.println("This Instant Effect gives " + effect.coinQty + "coins" + "for " + effect.cardType + " cards");
	}
	
	public void getInfo (DoublePointsTransformation effect){
		System.out.printf("The Transformation 1 gives you " + this.getPoints(effect.points) 
		+ " for %n" + this.getResources(effect.requestedForTransformation));
		System.out.printf("The Transformation 2 gives you " + this.getPoints(effect.points) 
		+ " for %n" + this.getResources(effect.requestedForTransformation2));
	}
	
	public void getInfo (DoubleResourcesTransformation effect){
		System.out.printf("The Transformation 1 gives you " + this.getResources(effect.resources) + " for %n" 
		+ this.getResources(effect.requestedForTransformation));
		System.out.println("The Transformation 2 gives you " + this.getResources(effect.resources2) + " for %n"
		+ this.getResources(effect.requestedForTransformation2));
	}
	
	public void getInfo (GetCard effect){
		System.out.println("This effect gives you a " + effect.cardType + " card of value " + effect.cardValue);
	}
	
	public void getInfo (GetCardAndPoints effect){
		System.out.printf("This effect gives you a " + effect.cardType + " card of value " + effect.cardValue
		+ "and %n" + getPoints(effect.points));
	}
	
	public void getInfo (GetCardAndResources effect){
		System.out.printf("This effect gives you a " + effect.cardType + " card of value " + effect.cardValue
		+ "and%n" + getResources(effect.resources));
	}
	
	public void getInfo (GetDiscountedCard effect){
		System.out.printf("This effect gives you a " + effect.cardType + " card of value " + effect.cardValue 
		+ "%n with a discount of " + getResources(effect.cardDiscount));
	}
	
	public void getInfo (HarvestProductionAction effect){
		System.out.println("This effect gives you a " + effect.actionType + " action of value " + effect.actionValue);
	}
	
	public void getInfo (HarvestProductionAndPoints effect){
		System.out.printf("This effect gives you a " + effect.actionType + " action of value " + effect.actionValue
		+ "and%n" + getPoints(effect.points));
	}
	
	public void getInfo (Points effect){
		System.out.println("This Instant Effect gives you " + getPoints(effect.points));
	}
	
	public void getInfo (PointsTransformation effect){
		System.out.printf("This Transformation gives you " + getPoints(effect.points)
		+ "for%n" + getResources(effect.requestedForTransformation));
	}
	
	public void getInfo (Resources effect){
		System.out.println("This Instant Effect gives you " + getResources(effect.resources));
	}
	
	public void getInfo (ResourcesAndPoints effect){
		System.out.printf("This Instant Effect gives you " + getResources(effect.resources) + " and%n"
		+ getPoints(effect.points));		
	}
	public void getInfo (ResourcesAndPointsTransformation effect){
		System.out.printf("This Transformation gives you " + getResources(effect.resources) + " and%n" + getPoints(effect.points)
		+ " for%n" + getPoints(effect.requestedForTransformation));
	}
	
	public void getInfo (ResourcesTransformation effect){
		System.out.printf("This Transformation gives you " + getResources(effect.resources) + " for%n" 
		+ getResources(effect.requestedForTransformation));
	}
	
	public void getInfo (SetFamilyMember effect){
		System.out.println("This effect set a colored family member you chose to this value: " + effect.familyMemberValue);
	}
	
	public void getInfo (VictoryForCard effect){
		System.out.printf("This Instant Effect gives you " + effect.victoryQty + " Victory Points%n" 
		+ "for each" + effect.cardType + " card you have");
	}
	
	public void getInfo (VictoryForMilitary effect){
		System.out.printf("This Instant Effect gives you " + effect.victoryQty + " Victory Points %n"
		+ "for each " + effect.militaryQty + " Military Points you have");
	}
	
	/*
	 * LeaderObject info
	 *
	
	public void getInfo (RequestedCard requested){
		System.out.println("To activate this Leader you need " + requested.cardQty + " " + requested.cardType + " cards");
	}
	
	public void getInfo (RequestedCardPointsResources requested){
		System.out.printf("To activate this Leader you need " + requested.cardQty + " " + requested.cardType + " cards and%n"
		+ getResources(requested.resources) + getPoints(requested.points));
	}
	
	public void getInfo (RequestedPoints requested){
		System.out.println("To activate this Leader you need "+ getPoints(requested.points));
	}
	
	public void getInfo (RequestedResources requested){
		System.out.println("To activate this Leader you need "+ getResources(requested.resources));
	}
	
	public void getInfo (RequestedSameCard requested){
		System.out.println("To activate this Leader you need " + requested.cardQty + " cards of the same type");
	}
	
	public void getInfo (RequestedTwoCards requested){
		System.out.printf("To activate this Leader you need " + requested.cardQty + " " + requested.cardType + " cards %n"
		+ "and " + requested.cardQty2 + " " + requested.cardType2 + " cards");
	}
	
	/*
	 * CharacterPermanentEffect info
	 *
	
	public void getInfo (CardActionDiscount bonus){
		System.out.println("This permanent effect gives you a discount of " + bonus.discount + " in action value to get a " + bonus.cardType + " card");
	}
	
	public void getInfo (CardActionResourcesDiscount bonus){
		System.out.printf("This permanent effect gives you a discount of " + bonus.discount + " in action value %n"
		+ "and a discount on the resources cost of " + getResources(bonus.resourcesDiscount) + "to get a " + bonus.cardType + " card ");
	}
	
	public void getInfo (HarvestProductionBoost bonus){
		System.out.println("This permanent effect gives you a boost of " + bonus.actionValue + " in action value on  " + bonus.actionType + " action");
	}
	
	public void getInfo (NoBoardBonuses bonus){
		System.out.println("This permanent effect blocks all the bonuses on the Towers action spaces");
	}
	
	/*
	 * ExcommunicationPermanentEffect info
	 *
	
	public void getInfo (CardActionMalus malus){
		System.out.printf("Each time you take a " + malus.cardType + " card (through the action space or as a Card effect),%n"
		+ "your action receives a " +  -malus.malus + " reduction");
	}
	
	public void getInfo (DiceMalus malus){
		System.out.println("All your colored Family Members receive a " + malus.malus + " reduction");
	}
	
	public void getInfo (HarvestProductionMalus malus){
		System.out.println("Each time you perform a " + malus.actionType + " action (through the action space or as a Card effect), its value is decreased by " +  malus.malus);
	}
	
	public void getInfo (MalusForResources malus){
		System.out.println("At the end of the game, you lose " + malus.victoryQty + " Victory Points for each " + malus.resourceQty + " resources (wood, stone, coin, servant) in your supply on your Personal Board.");
	}
	
	public void getInfo (MalusForResourcesCost malus){
		System.out.println("At the end of the game, you lose " + malus.victoryQty + " Victory Points for each " + malus.resourceQty + " wood and stone on your Building Cards’ costs.");
	}
	
	public void getInfo (MalusVictoryForMilitary malus){
		System.out.println("At the end of the game, you lose " + malus.victoryQty + " Victory Points for each" + malus.militaryQty + " Military Points you have.");
	}
	
	public void getInfo (MilitaryPointsMalus malus){
		System.out.println("Each time you gain Military Points (from action spaces or from your Cards), gain " + malus.militaryQty + " fewer Military Points.");
	}
	
	public void getInfo (NoMarket malus){
		System.out.println("You can’t permanently place your Family Members in the Market action spaces.");
	}
	
	public void getInfo (ResourcesMalus malus){
		System.out.printf("Each time you receive woods or stones (from action spaces or from your Cards), you receive fewer wood or stone,%n"
		+ " each time you receive servants and/or coins (from action spaces or from your Cards) you receive fewer coin and/or servants.%n"
		+ " In these quantities: " + getResources(malus.resources));
	}
	
	public void getInfo (ServantsMalus malus){
		System.out.println("You have to spend " + malus.servantsQty + " servants to increase your action value by 1");
	}
	
	public void getInfo (SkipFirstTurn malus){
		System.out.println("Each round, you skip your first turn. When all players have taken all their turns, you may still place your last Family Member");
	}
	
	public void getInfo (VictoryMalus malus){
		System.out.println("At the end of the game, before the Final Scoring, you lose " + malus.victoryMalus + " Victory Points for every " + malus.victoryQty + " Victory Points you have");
	}
	
	/*
	 * LeaderPermanentEffect info
	 /
	
	public void getInfo (AlreadyOccupiedTowerDiscount effect){
		System.out.println("You don’t have to spend 3 coins when you place your Family Members in a Tower that is already occupied");
	}
	
	public void getInfo (CardCoinDiscount effect){
		System.out.printf("When you take Development Cards, you get a discount of " + effect.coinQty + " coins (if the card you are taking has coins in its cost.)%n" +
		"This is not a discount on the coins you must spend if you take a Development Card from a Tower that’s already occupied");
	}
	
	public void getInfo (CopyLeaderAbility effect){
		System.out.println("Copy the ability of another Leader Card already played by another player. Once you decide the ability to copy, it can’t be changed");
	}
	
	public void getInfo (DoubleResourcesFromDevelopment effect){
		System.out.println("Each time you receive wood, stone, coins, or servants as an immediate effect from Development Cards (not from an action space), you receive the resources twice");
	}
	
	public void getInfo (NoMilitaryRequirementsForTerritory effect){
		System.out.println("You don’t need to satisfy the Military Points requirement when you take Territory Cards");
	}
	
	public void getInfo (PlaceFamilyMemberOnOccupiedSpace effect){
		System.out.println("You can place your Family Members in occupied action spaces");
	}
	
	public void getInfo (SetColoredDicesValues effect){
		if (effect.boostOrSet==true)
			System.out.printf("Your colored Family Members have a bonus of " + effect.diceValue + " on their value. %n"
			+"(You can increase their value by spending servants or if you have Character Cards with this effect)");
		else
			System.out.printf("Your colored Family Members have a value of " + effect.diceValue + ", regardless of their related dice. %n" 
			+ "(You can increase their value by spending servants or if you have Character Cards with this effect)");
	}
	
	public void getInfo (UncoloredMemberBonus effect){
		System.out.printf("Your uncolored Family Member has a bonus of " + effect.bonus + " on its value.%n"
		+ "(You can increase its value by spending servants or if you have Character Cards with this effect.)");
	}
	
	public void getInfo (VictoryForSupportingTheChurch effect){
		System.out.println("You gain " + effect.victoryQty + " additional Victory Points when you support the Church in a Vatican Report phase.");
	}
	
	public void getInfo (NoInstantEffect effect){
		System.out.println("This Card doesn't give any Instant Effect");
	}
	
	public void getInfo (NoCharacterPermanentEffect effect){
		System.out.println("This Card doesn't give any Permanent Effect");
	}	*/
}