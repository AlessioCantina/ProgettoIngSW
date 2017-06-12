package it.polimi.LM39.controller;

import java.util.ArrayList;

import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.Council;
import it.polimi.LM39.server.NetworkPlayer;

public class CouncilHandler {
	Council council = new Council();
	public void getCouncil(Integer qty,NetworkPlayer player,GameHandler gameHandler,ArrayList<Integer> favorsList) throws NotEnoughResourcesException, NotEnoughPointsException{
		if(qty>=1){
			//ask to the player what Council Favor he wants
			player.setMessage("What Council Favor do you want to get? From 1 to 5");
			bonusInfo(player);
			//getting the player response
			Integer choice = Integer.parseInt(player.sendMessage());
			//check if he has already taken this Favor in case of he has multiple Favors to get
			if(!checkAlreadyTaken(favorsList,choice)){
				//if not add it to the list of gotten favors
				favorsList.add(choice);
				//add to the player the bonus he chose
				switch(choice){
				case 1: gameHandler.addCardResources(council.bonus1, player);
					break;
				case 2: gameHandler.addCardResources(council.bonus2, player);
					break;
				case 3: gameHandler.addCardResources(council.bonus3, player);
					break;
				case 4: gameHandler.addCardPoints(council.bonus4, player);
					break;
				case 5: gameHandler.addCardPoints(council.bonus5, player);
					break;
				default:
					//if the player chose a bonus that doesn't exist
					player.setMessage("You must choose a Favor between 1 and 5");
					getCouncil(qty,player,gameHandler,favorsList);
					}
					getCouncil(qty-1,player,gameHandler,favorsList);
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
		player.setMessage("Bonus 1 gives " + council.bonus1.stones + " stones " + council.bonus1.woods + " woods");
		player.setMessage("Bonus 2 gives " + council.bonus2.servants + " servants");
		player.setMessage("Bonus 3 gives " + council.bonus3.coins + " coins");
		player.setMessage("Bonus 4 gives " + council.bonus4.military + " military points");
		player.setMessage("Bonus 5 gives " + council.bonus5.faith + " faith points");
	}
	
	//useless if as we think the bonuses are not configured by file
	/*player.setMessage("Bonus 1 gives " + council.bonus1.coins + " coins " + council.bonus1.stones + " stones " + council.bonus1.woods + " woods " + council.bonus1.servants + " servants");
		player.setMessage("Bonus 2 gives " + council.bonus2.coins + " coins " + council.bonus2.stones + " stones " + council.bonus2.woods + " woods " + council.bonus2.servants + " servants");
		player.setMessage("Bonus 3 gives " + council.bonus3.coins + " coins " + council.bonus3.stones + " stones " + council.bonus3.woods + " woods " + council.bonus3.servants + " servants");
		player.setMessage("Bonus 4 gives " + council.bonus4.faith + " faith points " + council.bonus4.military + " military points " + council.bonus4.victory + " victory points");
		player.setMessage("Bonus 5 gives " + council.bonus5.faith + " faith points " + council.bonus5.military + " military points " + council.bonus5.victory + " victory points");
		*/
			
	
}
