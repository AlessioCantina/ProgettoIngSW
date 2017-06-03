package it.polimi.LM39.controller;

import java.util.ArrayList;

import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.Council;
import it.polimi.LM39.server.NetworkPlayer;

public class CouncilHandler {

	public void getCouncil(Integer qty,NetworkPlayer player,GameHandler gameHandler,ArrayList<Integer> favorsList) throws NotEnoughResourcesException, NotEnoughPointsException{
		//ask to the player what Council Favor he wants
		player.setMessage("What Council Favor do you want to get? From 1 to 5");
		bonusInfo(player);
		//hetting the player response
		Integer choice = Integer.parseInt(player.sendMessage());
		if(qty>=1){
			//check if he has already taken this Favor in case of he has multiple Favors to get
			if(!checkAlreadyTaken(favorsList,choice)){
				//if not add it to the list of gotten favors
				favorsList.add(choice);
				//add to the player the bonus he chose
				switch(choice){
				case 1: gameHandler.addCardResources(Council.bonus1, player);
					break;
				case 2: gameHandler.addCardResources(Council.bonus2, player);
					break;
				case 3: gameHandler.addCardResources(Council.bonus3, player);
					break;
				case 4: gameHandler.addCardPoints(Council.bonus4, player);
					break;
				case 5: gameHandler.addCardPoints(Council.bonus5, player);
					break;
				default:
					//if the player chose a bonus that doesn't exist
					player.setMessage("You must choose a Favor between 1 and 5");
					getCouncil(qty,player,gameHandler,favorsList);
					}
					getCouncil(qty--,player,gameHandler,favorsList);
					}
			else{
				//if the player is trying to take a Favor that he has already taken, in case of multiple Favors
				player.setMessage("You can't take two times the same Council Favor");
				getCouncil(qty,player,gameHandler,favorsList);
			}
		}
	}
	
	private boolean checkAlreadyTaken (ArrayList<Integer> list, Integer j){
		for(Integer i: list)
			if(list.get(i)==j)
				return true;
		return false;
	}
	
	private void bonusInfo(NetworkPlayer player){
		//send to the player what the bonuses give
		player.setMessage("Bonus 1 gives " + Council.bonus1.coins + " coins " + Council.bonus1.stones + " stones " + Council.bonus1.woods + " woods " + Council.bonus1.servants + " servants");
		player.setMessage("Bonus 2 gives " + Council.bonus2.coins + " coins " + Council.bonus2.stones + " stones " + Council.bonus2.woods + " woods " + Council.bonus2.servants + " servants");
		player.setMessage("Bonus 3 gives " + Council.bonus3.coins + " coins " + Council.bonus3.stones + " stones " + Council.bonus3.woods + " woods " + Council.bonus3.servants + " servants");
		player.setMessage("Bonus 4 gives " + Council.bonus4.faith + " faith points " + Council.bonus4.military + " military points " + Council.bonus4.victory + " victory points");
		player.setMessage("Bonus 5 gives " + Council.bonus5.faith + " faith points " + Council.bonus5.military + " military points " + Council.bonus5.victory + " victory points");
	}
			
	
}
