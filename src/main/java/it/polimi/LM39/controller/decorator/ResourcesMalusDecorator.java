package it.polimi.LM39.controller.decorator;

import java.io.IOException;
import java.util.ArrayList;

import it.polimi.LM39.controller.CouncilHandler;
import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.Building;
import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.Character;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.model.PlayerResources;
import it.polimi.LM39.model.Venture;
import it.polimi.LM39.server.NetworkPlayer;

public class ResourcesMalusDecorator  extends DecoratedMethods{

	private DecoratedMethods decoratedMethods;
	private GameHandler gameHandler;
	private CardResources resourcesMalus;
	private NetworkPlayer player;
	
	public ResourcesMalusDecorator (DecoratedMethods decoratedMethods,GameHandler gameHandler, CardResources resourcesMalus, NetworkPlayer player) {
		this.decoratedMethods = decoratedMethods;
		this.gameHandler = gameHandler;
		this.resourcesMalus = resourcesMalus;
		this.player = player;
	}
	
	@Override
	public void addCardResources (CardResources resources, NetworkPlayer player) throws NotEnoughResourcesException, NotEnoughPointsException{
		if(this.player == player){
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
		 else
			 decoratedMethods.addCardResources(resources,player);
	    }
	  
	  
	@Override
	public void coinsForCharacter(NetworkPlayer player ,Character character) throws NotEnoughResourcesException{
		decoratedMethods.coinsForCharacter(player,character);
	}
		
	@Override
	public void resourcesForBuilding(NetworkPlayer player, Building building) throws NotEnoughResourcesException{
		decoratedMethods.resourcesForBuilding(player,building);
	}
		
	@Override
	public void resourcesForVenture(NetworkPlayer player ,Venture venture) throws NotEnoughResourcesException{
		decoratedMethods.resourcesForVenture(player,venture);
	}
	
	@Override
	public boolean addFamilyMemberToTheMarket(FamilyMember familyMember, Integer position, NetworkPlayer player) throws IOException, NotEnoughResourcesException, NotEnoughPointsException {
		return decoratedMethods.addFamilyMemberToTheMarket(familyMember, position, player);
	}
	
	@Override
	public void addCardPoints (CardPoints points, NetworkPlayer player) throws NotEnoughPointsException{
		decoratedMethods.addCardPoints(points, player);
	}
	
	@Override
	public Integer addServants(NetworkPlayer player) throws IOException, NotEnoughResourcesException{
		return decoratedMethods.addServants(player);
	}
}
