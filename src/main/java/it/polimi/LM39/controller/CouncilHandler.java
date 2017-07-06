package it.polimi.LM39.controller;

import java.util.ArrayList;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.Council;
import it.polimi.LM39.server.NetworkPlayer;

/**
 * this class handles the council favors
 */
public class CouncilHandler {
	
	/**
	 * to handle the council favors
	 * @param qty
	 * @param player
	 * @param gameHandler
	 * @param favorsList
	 * @throws NotEnoughResourcesException
	 * @throws NotEnoughPointsException
	 */
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
				case 1: gameHandler.decoratedMethods.addCardResources(Council.getResources(1), player);
					break;
				case 2: gameHandler.decoratedMethods.addCardResources(Council.getResources(2), player);
					break;
				case 3: gameHandler.decoratedMethods.addCardResources(Council.getResources(3), player);
					break;
				case 4: gameHandler.decoratedMethods.addCardPoints(Council.getPoints(4), player);
					break;
				case 5: gameHandler.decoratedMethods.addCardPoints(Council.getPoints(5), player);
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
	
	/**
	 * to check if a player is trying to get the same council favor twice
	 * @param list
	 * @param j
	 * @return
	 */
	private boolean checkAlreadyTaken (ArrayList<Integer> list, Integer j){
		for(Integer i: list)
			if(i==j)
				return true;
		return false;
	}
	
	/**
	 * to send to the player what bonuses the favors give
	 * @param player
	 */
	private void bonusInfo(NetworkPlayer player){
		//send to the player what the bonuses give
		player.setMessage("Bonus 1 gives " + Council.getResources(1).stones + " stones " + Council.getResources(1).woods + " woods");
		player.setMessage("Bonus 2 gives " + Council.getResources(2).servants + " servants");
		player.setMessage("Bonus 3 gives " + Council.getResources(3).coins + " coins");
		player.setMessage("Bonus 4 gives " + Council.getPoints(4).military + " military points");
		player.setMessage("Bonus 5 gives " + Council.getPoints(5).faith + " faith points");
	}
	
}
