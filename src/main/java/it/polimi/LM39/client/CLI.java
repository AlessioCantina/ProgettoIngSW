package it.polimi.LM39.client;

import java.util.ArrayList;
import java.util.Iterator;

import it.polimi.LM39.model.Building;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.model.FamilyMembersLocation;
import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.model.PersonalBoard;
import it.polimi.LM39.server.NetworkPlayer;

/**
 * 
 */
public class CLI extends UserInterface {

    /**
     * Default constructor
     */
    public CLI() {
    }

    /**
     * 
     */
    public void showNetworkMenu(){}
    
    

	@Override
	public void printMainBoard(MainBoard mainBoard) {
		String[][] cardsOnTowers = mainBoard.getCardNamesOnTheTowers();
//		FamilyMembersLocation familyMemberPositions = mainBoard.getFamilyMembersLocation();
//		FamilyMember[][] familyOnTowers = familyMemberPositions.getFamilyMembersOnTheTowers();
		FamilyMembersLocation familyMemberPositions = new FamilyMembersLocation();
		FamilyMember[][] familyOnTowers = familyMemberPositions.getFamilyMembersOnTheTowers();
		System.out.printf("╔════════════════════╦════════╦════════════════════╦════════╦════════════════════╦════════╦════════════════════╦════════╗\n");
		System.out.printf("║%-20s║%-8s║%-20s║%-8s║%-20s║%-8s║%-20s║%-8s║\n"
		,this.getCardOnTower(cardsOnTowers[0][0]),this.getPlayerColor(familyOnTowers[0][0]),this.getCardOnTower(cardsOnTowers[0][1])
		,this.getPlayerColor(familyOnTowers[0][1]),this.getCardOnTower(cardsOnTowers[0][2]),this.getPlayerColor(familyOnTowers[0][2])
		,this.getCardOnTower(cardsOnTowers[0][3]),this.getPlayerColor(familyOnTowers[0][3]));
		System.out.printf("║                    ║%-8s║                    ║%-8s║                    ║%-8s║                    ║%-8s║\n"
		,this.getFamilyMemberColor(familyOnTowers[0][0]),this.getFamilyMemberColor(familyOnTowers[0][1])
		,this.getFamilyMemberColor(familyOnTowers[0][2]),this.getFamilyMemberColor(familyOnTowers[0][3]));
		System.out.printf("╠════════════════════╬════════╬════════════════════╬════════╬════════════════════╬════════╬════════════════════╬════════╣\n");
		System.out.printf("║%-20s║%-8s║%-20s║%-8s║%-20s║%-8s║%-20s║%-8s║\n"
		,this.getCardOnTower(cardsOnTowers[1][0]),this.getPlayerColor(familyOnTowers[1][0]),this.getCardOnTower(cardsOnTowers[1][1])
		,this.getPlayerColor(familyOnTowers[1][1]),this.getCardOnTower(cardsOnTowers[1][2]),this.getPlayerColor(familyOnTowers[1][2])
		,this.getCardOnTower(cardsOnTowers[1][3]),this.getPlayerColor(familyOnTowers[1][3]));
		System.out.printf("║                    ║%-8s║                    ║%-8s║                    ║%-8s║                    ║%-8s║\n"
		,this.getFamilyMemberColor(familyOnTowers[1][0]),this.getFamilyMemberColor(familyOnTowers[1][1])
		,this.getFamilyMemberColor(familyOnTowers[1][2]),this.getFamilyMemberColor(familyOnTowers[1][3]));
		System.out.printf("╠════════════════════╬════════╬════════════════════╬════════╬════════════════════╬════════╬════════════════════╬════════╣\n");
		System.out.printf("║%-20s║%-8s║%-20s║%-8s║%-20s║%-8s║%-20s║%-8s║\n"
		,this.getCardOnTower(cardsOnTowers[2][0]),this.getPlayerColor(familyOnTowers[2][0]),this.getCardOnTower(cardsOnTowers[2][1])
		,this.getPlayerColor(familyOnTowers[2][1]),this.getCardOnTower(cardsOnTowers[2][2]),this.getPlayerColor(familyOnTowers[2][2])
		,this.getCardOnTower(cardsOnTowers[2][3]),this.getPlayerColor(familyOnTowers[2][3]));
		System.out.printf("║                    ║%-8s║                    ║%-8s║                    ║%-8s║                    ║%-8s║\n"
		,this.getFamilyMemberColor(familyOnTowers[2][0]),this.getFamilyMemberColor(familyOnTowers[2][1])
		,this.getFamilyMemberColor(familyOnTowers[2][2]),this.getFamilyMemberColor(familyOnTowers[2][3]));
		System.out.printf("╠════════════════════╬════════╬════════════════════╬════════╬════════════════════╬════════╬════════════════════╬════════╣\n");
		System.out.printf("║%-20s║%-8s║%-20s║%-8s║%-20s║%-8s║%-20s║%-8s║\n"
		,this.getCardOnTower(cardsOnTowers[3][0]),this.getPlayerColor(familyOnTowers[3][0]),this.getCardOnTower(cardsOnTowers[3][1])
		,this.getPlayerColor(familyOnTowers[3][1]),this.getCardOnTower(cardsOnTowers[3][2]),this.getPlayerColor(familyOnTowers[3][2])
		,this.getCardOnTower(cardsOnTowers[3][3]),this.getPlayerColor(familyOnTowers[3][3]));
		System.out.printf("║                    ║%-8s║                    ║%-8s║                    ║%-8s║                    ║%-8s║\n"
		,this.getFamilyMemberColor(familyOnTowers[3][0]),this.getFamilyMemberColor(familyOnTowers[3][1])
		,this.getFamilyMemberColor(familyOnTowers[3][2]),this.getFamilyMemberColor(familyOnTowers[3][3]));
		System.out.printf("╚════════════════════╩════════╩════════════════════╩════════╩════════════════════╩════════╩════════════════════╩════════╝\n");
		
	}
	
	public void printMarket(MainBoard mainBoard){
		int marketSize = 4;
		System.out.printf("╔════════════════════╦═══════════════════╗\n");
		System.out.printf("║%20s║%20s║\n");
		System.out.printf("║%20s║%20s║\n");
		System.out.printf("║%20s║%20s║\n");
		System.out.printf("║%20s║%20s║\n");
		System.out.printf("╚════════════════════╩═══════════════════╝\n");
		if(marketSize == 4){
			System.out.printf("╔════════════════════╦═══════════════════╗\n");
			System.out.printf("║%20s║%20s║\n");
			System.out.printf("║%20s║%20s║\n");
			System.out.printf("║%20s║%20s║\n");
			System.out.printf("║%20s║%20s║\n");
			System.out.printf("╚════════════════════╩═══════════════════╝\n");
		}
	}
	public void printCouncilPalace(MainBoard mainBoard){
		FamilyMembersLocation location = mainBoard.getFamilyMembersLocation();
		ArrayList<FamilyMember> councilPalace = location.getFamilyMembersAtTheCouncilPalace();
		int membersToPrint = councilPalace.size();
		int i = 0;
		System.out.printf("╔════════════════════════════════════════╗\n");
		while(membersToPrint != 0){
			System.out.printf("║%20s║\n",councilPalace.get(i).playerColor);
			System.out.printf("║%20s║\n",councilPalace.get(i).color);
			i++;
		}
		System.out.printf("╚════════════════════════════════════════╝\n");
	}
	public void printPossesedCards(NetworkPlayer player, MainBoard mainBoard){
		PersonalBoard board = player.personalBoard;
		ArrayList<Integer> buildings = board.possessedBuildings;
		ArrayList<Integer> territories = board.possessedTerritories;
		ArrayList<Integer> ventures = board.possessedVentures;
		ArrayList<Integer> characters = board.possessedCharacters;
		ArrayList<String> leaders = board.possessedLeaders;
		System.out.println("Possesed buildings:");
		printCardType("Building",buildings);
		System.out.println("Possesed territories:");
		printCardType("Territory",territories);
		System.out.println("Possesed ventures:");
		printCardType("Venture",ventures);
		System.out.println("Possesed characters:");
		printCardType("Character",characters);
		System.out.println("Possesed leaders:");
		//printCardType("Leader",leaders);
		
	}
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
			else if(("Leader").equals(cardType))
				System.out.println(MainBoard.leaderMap.get(i));
			i++;
			System.out.println("\n");
		}
	}
	public String getPlayerColor(FamilyMember familyOnTower){
		if(familyOnTower == null)
			return "free";
		return "Player:" + familyOnTower.playerColor;
	}
	public String getFamilyMemberColor(FamilyMember familyOnTower){
		if(familyOnTower == null)
			return "";
		return "Family:" + familyOnTower.color;
	}
	public String getCardOnTower(String cardOnTower){
		if(cardOnTower == null)
			return "No Card";
		return cardOnTower;
	}
	@Override
	public void printMessage(String message) {
		// TODO Autogenerated method stub
		
	}

}