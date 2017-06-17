package it.polimi.LM39.controller.decorator;

import java.io.IOException;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.Building;
import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.Character;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.model.Venture;
import it.polimi.LM39.server.NetworkPlayer;

public class NoMarketDecorator extends DecoratedMethods{

	private DecoratedMethods decoratedMethods;
	private GameHandler gameHandler;
	private NetworkPlayer player;
	
	public NoMarketDecorator (DecoratedMethods decoratedMethods,GameHandler gameHandler, NetworkPlayer player) {
		this.decoratedMethods = decoratedMethods;
		this.gameHandler = gameHandler;
		this.player = player;
	}

	
	@Override
	public boolean addFamilyMemberToTheMarket(FamilyMember familyMember, Integer position, NetworkPlayer player) throws IOException, NotEnoughResourcesException, NotEnoughPointsException {
		if(this.player == player){
			player.setMessage("Because of the Excommunication you can’t place your Family Members in the Market action spaces");
			return false;
		}
		else
			return decoratedMethods.addFamilyMemberToTheMarket(familyMember,position,player);
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
	public void addCardResources (CardResources resources, NetworkPlayer player) throws NotEnoughResourcesException, NotEnoughPointsException{
		decoratedMethods.addCardResources (resources,player);
	}
	
	@Override
	public void coinsForCharacter(NetworkPlayer player ,Character character) throws NotEnoughResourcesException{
		decoratedMethods.coinsForCharacter(player,character);
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
