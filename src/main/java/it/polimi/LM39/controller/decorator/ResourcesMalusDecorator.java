package it.polimi.LM39.controller.decorator;

import java.io.IOException;

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

public class ResourcesMalusDecorator  extends GameHandler{

	private GameHandler decoratedGameHandler;
	private CardResources resourcesMalus;
	private NetworkPlayer player;
	
	public ResourcesMalusDecorator (GameHandler decoratedGameHandler, CardResources resourcesMalus, NetworkPlayer player) {
		this.decoratedGameHandler = decoratedGameHandler;
		this.resourcesMalus = resourcesMalus;
		this.player = player;
	}
	
	@Override
	public void addCardResources (CardResources resources, NetworkPlayer player) throws NotEnoughResourcesException{
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
					decoratedGameHandler.addCardResources(resources,player);
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
			player.resources=playerResources;
		 }
		 else
			 decoratedGameHandler.addCardResources(resources,player);
	    }
	  
	  
	@Override
	public void coinsForCharacter(NetworkPlayer player ,Character character) throws NotEnoughResourcesException{
		decoratedGameHandler.coinsForCharacter(player,character);
	}
		
	@Override
	public void resourcesForBuilding(NetworkPlayer player, Building building) throws NotEnoughResourcesException{
		decoratedGameHandler.resourcesForBuilding(player,building);
	}
		
	@Override
	public void resourcesForVenture(NetworkPlayer player ,Venture venture) throws NotEnoughResourcesException{
		decoratedGameHandler.resourcesForVenture(player,venture);
	}
	
	@Override
	public boolean addFamilyMemberToTheMarket(FamilyMember familyMember, Integer position, NetworkPlayer player) throws IOException, NotEnoughResourcesException, NotEnoughPointsException {
		return decoratedGameHandler.addFamilyMemberToTheMarket(familyMember, position, player);
	}
	
	@Override
	public void addCardPoints (CardPoints points, NetworkPlayer player) throws NotEnoughPointsException{
		decoratedGameHandler.addCardPoints(points, player);
	}
	
	@Override
	public Integer addServants(NetworkPlayer player) throws IOException, NotEnoughResourcesException{
		return decoratedGameHandler.addServants(player);
	}
}
