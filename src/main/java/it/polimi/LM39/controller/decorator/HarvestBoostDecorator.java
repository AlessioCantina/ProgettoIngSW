package it.polimi.LM39.controller.decorator;

import it.polimi.LM39.server.NetworkPlayer;
import java.lang.reflect.InvocationTargetException;
import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.PersonalBoardHandler;
import it.polimi.LM39.exception.InvalidActionTypeException;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.FamilyMember;

public class HarvestBoostDecorator extends DecoratedMethodsDecorator{
	private Integer harvestBonus;
	
	public HarvestBoostDecorator (DecoratedMethods decoratedMethods, Integer boost) {
		super(decoratedMethods);
		this.harvestBonus = boost;
	}
	
	@Override
	public boolean activateHarvest(Integer value,NetworkPlayer player,PersonalBoardHandler personalBoardHandler,FamilyMember familyMember) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughResourcesException, NotEnoughPointsException, InvalidActionTypeException{
		return super.decoratedMethods.activateHarvest(value + harvestBonus,player,personalBoardHandler,familyMember);
	}

}
