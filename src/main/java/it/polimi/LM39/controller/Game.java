package it.polimi.LM39.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

import it.polimi.LM39.exception.CardNotFoundException;
import it.polimi.LM39.exception.FailedToReadFileException;
import it.polimi.LM39.exception.FailedToRegisterEffectException;
import it.polimi.LM39.exception.InvalidActionTypeException;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.model.Player;
import it.polimi.LM39.model.PlayerRank;
import it.polimi.LM39.server.NetworkPlayer;
import it.polimi.LM39.server.Room;

/**
 * 
 */
public class Game implements Runnable{

    /**
     * Default constructor
     */
    public Game(Integer playerNumber, ArrayList<NetworkPlayer> players) {
    	this.playerNumber = playerNumber;
    	this.players = players;
    	gameHandler = new GameHandler();
    }
    
    private GameHandler gameHandler;
    private int playerNumber;

    /**
     * 
     */
    private ArrayList<NetworkPlayer> players = new ArrayList<NetworkPlayer>();

    /**
     * 
     */
    public Integer timeOutStart;

    /**
     * 
     */
    public Integer timeOutMove;

    private void initialize() throws FailedToReadFileException, FailedToRegisterEffectException, IOException{
    	if(playerNumber > 2)
    		gameHandler.harvestAndProductionSize = 4;
    	else
    		gameHandler.harvestAndProductionSize = 1;
    	if(playerNumber > 3)
    		gameHandler.marketSize = 4;
    	else
    		gameHandler.marketSize = 2;
    	gameHandler.setPeriod(1);
    	gameHandler.setRound(1);
    	gameHandler.initializeTheGame();
    	
    	//load the Rankings
    	loadRankings();
    	
    	//set players action order
    	Collections.shuffle(players);
    	ArrayList<String> order = new ArrayList<String>();
    	setPlayersColor(players);
    	for(NetworkPlayer player : players){
    		order.add(player.playerColor);
    	}
    		
    	gameHandler.setPlayersActionOrder(order);
    	
    	for(int i=0;i<order.size();i++){
			try {
				gameHandler.setFirstRoundBonuses(playerColorToNetworkPlayer(order.get(i)),i+1);
			} catch (NotEnoughResourcesException e) {
				e.printStackTrace();
			}
    	}
    	
    
    }
    private void setPlayersColor(ArrayList<NetworkPlayer> players){
    	String[] colors = {"green","blue","red","yellow"};
    	for(int i = 0; i < players.size(); i++){
    		players.get(i).playerColor = colors[i];
    	}
    }
    
    private void playerAction(NetworkPlayer player){
    	player.setMessage("What action do you want to perform?");
    	String response = player.sendMessage();
    	boolean flag = false;
    	if(("get card").equals(response)){
    		player.setMessage("What card do you want?");
    		response = player.sendMessage();
    		try {
				gameHandler.cardNameToInteger(response);
			} catch (CardNotFoundException e) {
				player.setMessage("This card is not on the Towers");
				playerAction(player);
				return;
			}
    		FamilyMember familyMember = handleFamilyMember(player);
    		try {
				flag = gameHandler.addFamilyMemberToTheTower(familyMember, gameHandler.cardNameToInteger(response) , player);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | IOException | NotEnoughResourcesException | NotEnoughPointsException
					| CardNotFoundException e) {
				e.printStackTrace();
				//give the servants back to the player if the action failed
				try {
					player.resources.setServants(familyMember.getServants());
				} catch (NotEnoughResourcesException e1) {
					e1.printStackTrace();
				}
				playerAction(player);
				return;
			}
    		if(flag==false){
				//give the servants back to the player if the action failed
				try {
					player.resources.setServants(familyMember.getServants());
				} catch (NotEnoughResourcesException e1) {
					e1.printStackTrace();
				}
				playerAction(player);
				return;
    		}
    		//ad the played family member to the played family fembers list
    		player.setPlayedFamilyMember(familyMember.color);
    	}
    	else if (("activate production").equals(response)){
    		FamilyMember familyMember = handleFamilyMember(player);
    		try {
				gameHandler.playerBoardHandler.activateProduction(gameHandler.familyMemberValue(familyMember, player), player);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | IOException | NotEnoughResourcesException
					| NotEnoughPointsException e) {
				e.printStackTrace();
				//give the servants back to the player if the action failed
				try {
					player.resources.setServants(familyMember.getServants());
				} catch (NotEnoughResourcesException e1) {
					e1.printStackTrace();
				}
				playerAction(player);
				return;
			}
    	}
    	else if (("activate harvest").equals(response)){
    		FamilyMember familyMember = handleFamilyMember(player);
    		try {
				gameHandler.playerBoardHandler.activateHarvest(gameHandler.familyMemberValue(familyMember, player), player);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NotEnoughResourcesException | NotEnoughPointsException
					| IOException e) {
				e.printStackTrace();
				//give the servants back to the player if the action failed
				try {
					player.resources.setServants(familyMember.getServants());
				} catch (NotEnoughResourcesException e1) {
					e1.printStackTrace();
				}
				playerAction(player);
				return;
			}
    	}
    	else if (("discard leader").equals(response)){
    		player.setMessage("Which leader card do you want to discard?");
    		response = player.sendMessage();
    		for(String card : player.personalBoard.getPossessedLeaders())
    			if((card).equals(response))
					try {
						gameHandler.discardLeader(player, card);
					} catch (NotEnoughResourcesException | NotEnoughPointsException e) {
						e.printStackTrace();
						playerAction(player);
						return;
					}
    	}
    	else if (("activate leader").equals(response)){
    		player.setMessage("Which leader do you want to activate?");
    		response = player.sendMessage();
    		flag = false;
    		for(String card : player.personalBoard.getPossessedLeaders())
    			if((card).equals(response)){
    				CardHandler cardHandler = new CardHandler(gameHandler);
    				try {
						flag = cardHandler.checkLeaderRequestedObject(MainBoard.leaderMap.get(card).requestedObjects, player);
					} catch (NoSuchMethodException | SecurityException | IllegalAccessException
							| IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
    				if(flag == false){
    					player.setMessage("You don't have the requirements to activate this leader!");
						playerAction(player);
						return;
    				}
    				else {
    					try {
							cardHandler.activateLeader(MainBoard.leaderMap.get(card).effect, player, MainBoard.leaderMap.get(card).cardName);
						} catch (SecurityException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException | NoSuchMethodException e) {
							e.printStackTrace();
						}
    				}
    			}
    	}
    	
    	else if (("go to the market").equals(response)){
    		FamilyMember familyMember = handleFamilyMember(player);
    		player.setMessage("In which position to you want to go? From 1 to 4");
    		flag = false;
    		response = player.sendMessage();
    		if(!("4").equals(response) && !("3").equals(response) && !("2").equals(response) && !("1").equals(response)){
    			player.setMessage("You must choose between 1 and 4");
    			//give the servants back to the player if the action failed
				try {
					player.resources.setServants(familyMember.getServants());
				} catch (NotEnoughResourcesException e) {
					e.printStackTrace();
				}
				playerAction(player);
				return;
    		}
    		else{
    			try {
					flag = gameHandler.addFamilyMemberToTheMarket(familyMember, Integer.parseInt(response), player);
				} catch (NumberFormatException | IOException | NotEnoughResourcesException
						| NotEnoughPointsException e) {
					e.printStackTrace();
				}
    			if(flag==false){
    				//give the servants back to the player if the action failed
    				try {
    					player.resources.setServants(familyMember.getServants());
    				} catch (NotEnoughResourcesException e) {
    					e.printStackTrace();
    				}
    				playerAction(player);
    				return;
    			}
    		}
    	}
    	
    	else if (("go to the council palace").equals(response)){
    		flag = false;
    		FamilyMember familyMember = handleFamilyMember(player);
    		try {
				flag = gameHandler.addFamilyMemberToTheCouncilPalace(familyMember, player);
			} catch (IOException | NotEnoughResourcesException | NotEnoughPointsException e) {
				e.printStackTrace();
			}
    		if(flag==false){
				//give the servants back to the player if the action failed
				try {
					player.resources.setServants(familyMember.getServants());
				} catch (NotEnoughResourcesException e) {
					e.printStackTrace();
				}
				playerAction(player);
				return;
    		}
    	}
    }


    
    private FamilyMember handleFamilyMember(NetworkPlayer player){
    	FamilyMember familyMember = gameHandler.chooseFamilyMember(player);
		try {
			familyMember.setServants(gameHandler.addServants(player));
		} catch (IOException | NotEnoughResourcesException e) {
			player.setMessage("You don't have enough servants!");
			return handleFamilyMember(player);
		}
		return familyMember;
    }

    private void loadRankings(){
    	ArrayList<PlayerRank> faithRanking = new ArrayList<PlayerRank>();
    	ArrayList<PlayerRank> victoryRanking = new ArrayList<PlayerRank>();
    	ArrayList<PlayerRank> militaryRanking = new ArrayList<PlayerRank>();
    	for (NetworkPlayer player : players){
    		PlayerRank playerRankFaith = new PlayerRank();
    		playerRankFaith.playerNickName = player.nickname;
    		playerRankFaith.setPlayerPoints(0);
    		faithRanking.add(playerRankFaith);
    		
    		PlayerRank playerRankVictory = new PlayerRank();
    		playerRankVictory.playerNickName = player.nickname;
    		playerRankVictory.setPlayerPoints(0);
    		victoryRanking.add(playerRankVictory);
    		
    		PlayerRank playerRankMilitary = new PlayerRank();
    		playerRankMilitary.playerNickName = player.nickname;
    		playerRankMilitary.setPlayerPoints(0);
    		militaryRanking.add(playerRankMilitary);
    	}
    		
    	gameHandler.mainBoard.rankings.setFaithRanking(faithRanking);
    	gameHandler.mainBoard.rankings.setVictoryRanking(victoryRanking);
    	gameHandler.mainBoard.rankings.setMilitaryRanking(militaryRanking);
    }
    
    
    //TODO handle SkipFirstTurn Excommunication
    //TODO players choose leader card
    //TODO players choose Personal Bonus Tile
    public void run() {
    	//initialize the game loading parameters and cards
    	try {
			initialize();
		} catch (FailedToReadFileException | FailedToRegisterEffectException | IOException e) {
			e.printStackTrace();
		}
    	//make the players choose a their four leader cards
    	chooseLeaderCard();
    	//the array list where the players actions order is stored
    	ArrayList <String> order;
    	for(int period=0;period<3;period++){
    		for(int round=0;round<2;round++){
    			order = gameHandler.getPlayersActionOrder();
    			for(int move=0;move<playerNumber;move++){
	    			NetworkPlayer player = playerColorToNetworkPlayer(order.get(move));
	    			player.setMessage(gameHandler.mainBoard);
	    			playerAction(player);
	    			gameHandler.updateRankings(player);
	    		
	    		}
	    		
	    		gameHandler.setPlayerActionOrder();
	    		gameHandler.setRound(round+1);
	    		
	    		//clean all the action spaces for a new round
	    		try {
					gameHandler.cleanActionSpaces();
				} catch (InvalidActionTypeException e1) {
					e1.printStackTrace();
				}
	    		
	    		//give the played family members back to the players
	    		giveFamilyMembersBack();
	    		try {
					gameHandler.loadCardsOnTheMainBoard();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    		//update the personalMainBoards of all players
	    		updatePersonalMainBoards();
    		}
    		//support the church at the end of a period
    		for(NetworkPlayer player : players)
    			try {
					gameHandler.supportTheChurch(player);
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NotEnoughResourcesException | NotEnoughPointsException e) {
					e.printStackTrace();
				}
    		
    		gameHandler.setPeriod(period+1);
    	}
    	//calculate and send the final points made by every player to every player
    	sendFinalPoints(gameHandler.calculateFinalPoints(players));
    	
    }

    private void sendFinalPoints (ArrayList<PlayerRank> finalScores){
    	for(NetworkPlayer player : players)
    		for(PlayerRank playerRank : finalScores){
    			player.setMessage(playerRank.playerNickName + " made " + playerRank.getPlayerPoints());
    		}
    }
    
    private void updatePersonalMainBoards (){
    	for(NetworkPlayer player : players){
    		gameHandler.resetPlayerPersonalMainBoard(player);
    		try {
				gameHandler.activatePermanentEffects(player);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
    	}
    		
    }
    
    private NetworkPlayer playerColorToNetworkPlayer (String color){
    	for(NetworkPlayer player: players)
    		if(player.playerColor.equals(color))
    			return(player);
    	return null;
    }
    
    private void giveFamilyMembersBack(){
    	for(NetworkPlayer player : players)
    		player.setPlayedFamilyMembers(new ArrayList<String>());
    }

    private void chooseLeaderCard(){
    	//creating an array list of leaders names randomly ordinated
    	ArrayList<String> leaders = new ArrayList<String>();
    	leaders = LeadersName.getLeaderArrayList();
    	Collections.shuffle(leaders);
    	int j=0;
    	String response = "";
    	for(int i=4,n=0;i>0;i--,n++)
    		//send to the players the cards he should choose one every time 
    		for(int playerNumber=0,k=0;playerNumber<players.size();){
    			if(playerNumber+n<players.size()){
    				System.out.println("playerNumber " + playerNumber + " " + n);
    				players.get(playerNumber + n).setMessage("Choose a leader card between:");
    				}
    			else if (((playerNumber + n)-players.size())<players.size()){
    				System.out.println("playerNumber " + playerNumber + " " + n + " " + (-players.size()));
    				players.get((playerNumber + n)-players.size()).setMessage("Choose a leader card between:");}
    			else
    				players.get((playerNumber + n)-players.size()-players.size()).setMessage("Choose a leader card between:");
    			//send to the player the list of leader card in which he must choose one card
    			System.out.println("da " + (i+k) + " a " + k);
    			for(j=i+k;j>0+k;j--){
    				if(playerNumber+n<players.size())
    					players.get(playerNumber + n).setMessage(leaders.get(j));
    				else if (((playerNumber + n)-players.size())<players.size())
    					players.get((playerNumber + n)-players.size()).setMessage(leaders.get(j));
    				else
        				players.get((playerNumber + n)-players.size()-players.size()).setMessage(leaders.get(j));
    			}
    			if(playerNumber+n<players.size())
    				response = players.get((playerNumber + n)).sendMessage();
    			else if (((playerNumber + n)-players.size())<players.size())
    				response = players.get((playerNumber + n)-players.size()).sendMessage();
    			else
    				response = players.get((playerNumber + n)-players.size()-players.size()).sendMessage();
	    		// if the player chose a leader card between the ones he could choose
	    		if(leaders.contains(response)){
	    			//give to the player the card
	    			players.get(playerNumber).personalBoard.setLeader(response);
	    			//remove the card from the array list so that no other players can get this same card
	 				leaders.remove(response);
	 				//go to the next player
	 				playerNumber++;
	 				//updating k this way ensures that going through the cicles the players send the cards they discarded to the next player like in the game rules
	 				k+=i;
	    		}
	    		//if the player response is not a leader card in between the ones he could choose keep sending the same list of cards
	   		}
    }

    
    /*
    public static void main(String[] args) {
        
    	
    	
    	//code to test the method loadCardsOnTheMainBoard();
    	
    	MainBoard mainBoard = new MainBoard();
        GameHandler gameHandler = new GameHandler();
        gameHandler.mainBoard = mainBoard;
        //calls the gsonReader to populate the hashmaps
        gameHandler.initializeTheGame();
        gameHandler.loadCardsOnTheMainBoard();
        String[][] cards = gameHandler.cardNameOnTheMainBoard();
        for(int i=0;i<4;i++){
            for (int j=0; j<4; j++){
            	System.out.println(cards[j][i]);
            }
            }
        System.out.println();
        gameHandler.setRound(2);
        gameHandler.loadCardsOnTheMainBoard();
        cards = gameHandler.cardNameOnTheMainBoard();
        for(int i=0;i<4;i++){
            for (int j=0; j<4; j++){
            	System.out.println(cards[j][i]);
            }
            } 
    } 
    */

}