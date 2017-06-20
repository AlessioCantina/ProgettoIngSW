package it.polimi.LM39.controller.decorator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.GameHandler;
import it.polimi.LM39.controller.PersonalBoardHandler;
import it.polimi.LM39.exception.InvalidActionTypeException;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.Building;
import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.CardResources;
import it.polimi.LM39.model.Character;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.model.Venture;
import it.polimi.LM39.server.NetworkPlayer;

public class ServantsMalusDecorator extends DecoratedMethodsDecorator{

	private GameHandler gameHandler;
	private Integer servantsMalus;
	private NetworkPlayer player;
	
	public ServantsMalusDecorator (DecoratedMethods decoratedMethods,GameHandler gameHandler, Integer servantsMalus, NetworkPlayer player) {
		super(decoratedMethods);
		this.gameHandler = gameHandler;
		this.servantsMalus = servantsMalus;
		this.player = player;
	}
	
	@Override
	 public Integer addServants(NetworkPlayer player) throws IOException, NotEnoughResourcesException{
			player.setMessage("Do you want to add servants? yes or no \n You have an Excommunication so if you add "+ servantsMalus + " your action will increase by 1");
	    	String response = player.sendMessage();
	    	response = GameHandler.checkResponse(response, player);
	    	if(("yes").equals(response)){
	    		player.setMessage("How many?");
	    		Integer qty = Integer.parseInt(player.sendMessage());
	    		player.resources.setServants(-qty);
	    		return (qty/servantsMalus);
	    	}
	    	else if (("no").equals(response))
	    		return 0;
	    	//unreachable code
	    	return null;
    }

	/*
	@Override
	public void addCardResources (CardResources resources, NetworkPlayer player) throws NotEnoughResourcesException, NotEnoughPointsException{
		decoratedMethods.addCardResources (resources,player);
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
	public boolean activateHarvest(Integer value,NetworkPlayer player,PersonalBoardHandler personalBoardHandler,FamilyMember familyMember) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughResourcesException, NotEnoughPointsException, InvalidActionTypeException {
		return decoratedMethods.activateHarvest(value,player,personalBoardHandler,familyMember);
	}
	
	@Override
	public boolean activateProduction(Integer value,NetworkPlayer player,PersonalBoardHandler personalBoardHandler,FamilyMember familyMember) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughResourcesException, NotEnoughPointsException, IOException, InvalidActionTypeException {
		return decoratedMethods.activateProduction(value,player,personalBoardHandler,familyMember);
	}
	*/
}
