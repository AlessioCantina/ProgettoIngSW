package it.polimi.LM39.client;

import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.model.FamilyMembersLocation;
import it.polimi.LM39.model.MainBoard;

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