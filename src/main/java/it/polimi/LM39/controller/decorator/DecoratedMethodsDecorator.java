package it.polimi.LM39.controller.decorator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import it.polimi.LM39.controller.DecoratedMethods;
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

/**
 * this class contains all the overrides of the DecoratedMethods, this is needed to allow a layer of decoration that overrides a single method
 * to call the other methods (that may or may not have been overridden) from the layer below
 */
public class DecoratedMethodsDecorator extends DecoratedMethods{

	public DecoratedMethods decoratedMethods;
	
	public DecoratedMethodsDecorator (DecoratedMethods decoratedMethods) {
		this.decoratedMethods = decoratedMethods;
	}
	
	
	@Override
	public boolean activateProduction(Integer value,NetworkPlayer player,PersonalBoardHandler personalBoardHandler,FamilyMember familyMember) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughResourcesException, NotEnoughPointsException, IOException, InvalidActionTypeException {
		return decoratedMethods.activateProduction(value,player,personalBoardHandler,familyMember);
	}
	
	@Override
	public boolean activateHarvest(Integer value,NetworkPlayer player,PersonalBoardHandler personalBoardHandler,FamilyMember familyMember) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughResourcesException, NotEnoughPointsException, InvalidActionTypeException {
		return decoratedMethods.activateHarvest(value,player,personalBoardHandler,familyMember);
	}
	
	@Override
	public void coinsForCharacter(NetworkPlayer player ,Character character) throws NotEnoughResourcesException{
		decoratedMethods.coinsForCharacter(player,character);
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
	
	@Override
	public void resourcesForBuilding(NetworkPlayer player, Building building) throws NotEnoughResourcesException{
		decoratedMethods.resourcesForBuilding(player,building);
	}
	
}
