package it.polimi.LM39.controller.decorator;

import java.io.IOException;

import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.Building;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.Character;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.model.PlayerResources;
import it.polimi.LM39.model.Venture;
import it.polimi.LM39.server.NetworkPlayer;

public class MalusForResourcesDecorator  extends GameHandler{

	private GameHandler decoratedGameHandler;
	private CardResources resourcesMalus;
	private NetworkPlayer player;
	
	public MalusForResourcesDecorator (GameHandler decoratedGameHandler, CardResources resourcesMalus, NetworkPlayer player) {
		this.decoratedGameHandler = decoratedGameHandler;
		this.resourcesMalus = resourcesMalus;
		this.player = player;
	}
	
	@Override
	public void addCardResources (CardResources resources, NetworkPlayer player) throws NotEnoughResourcesException{
		if(this.player == player){
			PlayerResources playerResources = player.resources;
			//if the player is receiving more resources than the malus give him resources - malus if not give him nothing
			if(resources.coins>=resourcesMalus.coins)
				playerResources.setCoins(resources.coins - resourcesMalus.coins);
			if(resources.woods>=resourcesMalus.woods)
				playerResources.setWoods(resources.woods - resourcesMalus.woods);
			if(resources.stones>=resourcesMalus.stones)
				playerResources.setWoods(resources.stones - resourcesMalus.stones);
			if(resources.servants>=resourcesMalus.servants)
				playerResources.setWoods(resources.servants - resourcesMalus.servants);
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
}
