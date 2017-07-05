package it.polimi.LM39.controller.decorator;

import java.util.ArrayList;
import it.polimi.LM39.controller.CouncilHandler;
import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.PlayerResources;
import it.polimi.LM39.server.NetworkPlayer;

/**
 * this class decorates DecoratedMethods with the decorator of addCardResources
 */
public class ResourcesMalusDecorator  extends DecoratedMethodsDecorator{

	private GameHandler gameHandler;
	private CardResources resourcesMalus;
	
	public ResourcesMalusDecorator (DecoratedMethods decoratedMethods,GameHandler gameHandler, CardResources resourcesMalus) {
		super(decoratedMethods);
		this.gameHandler = gameHandler;
		this.resourcesMalus = resourcesMalus;
	}
	
	/**
	 * this method overrides addCardResources to set a malus on resources received by a card
	 */
	@Override
	public void addCardResources (CardResources resources, NetworkPlayer player) throws NotEnoughResourcesException, NotEnoughPointsException{
			PlayerResources playerResources = player.resources;
			if(resources.woods>0 && resources.stones>0){
				player.setMessage("Do you want to have your Excommunication malus on woods or stones?");
				String response = player.sendMessage();
				if(("woods").equals(response)) {
					//if the player is receiving more resources than the malus give him resources - malus if not give him nothing
					if(resources.woods>=resourcesMalus.woods)
						playerResources.setWoods(resources.woods - resourcesMalus.woods);
					playerResources.setStones(resources.stones);
				}
				else if(("stones").equals(response)) { 
						if(resources.stones>=resourcesMalus.stones)
							playerResources.setStones(resources.stones - resourcesMalus.stones);
						playerResources.setWoods(resources.woods);
				}
				else{
					player.setMessage("You must choose between woods and stones");
					decoratedMethods.addCardResources(resources,player);
					return;
				}
			}
			else{
				if(resources.woods>=resourcesMalus.woods)
					playerResources.setWoods(resources.woods - resourcesMalus.woods);
				if(resources.stones>=resourcesMalus.stones)
					playerResources.setStones(resources.stones - resourcesMalus.stones);
			}
			if(resources.coins>=resourcesMalus.coins)
				playerResources.setCoins(resources.coins - resourcesMalus.coins);
			if(resources.servants>=resourcesMalus.servants)
				playerResources.setServants(resources.servants - resourcesMalus.servants);	
			if(resources.council>=resourcesMalus.council){
				CouncilHandler councilHandler = new CouncilHandler();
				councilHandler.getCouncil(resources.council,player,gameHandler,new ArrayList<Integer>());
			}
			player.resources=playerResources;
	    }
	
}
