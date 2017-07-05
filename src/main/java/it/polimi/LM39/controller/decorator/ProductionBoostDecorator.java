package it.polimi.LM39.controller.decorator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.PersonalBoardHandler;
import it.polimi.LM39.exception.InvalidActionTypeException;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.server.NetworkPlayer;

public class ProductionBoostDecorator extends DecoratedMethodsDecorator{
	
	private Integer productionBonus;
	
	public ProductionBoostDecorator (DecoratedMethods decoratedMethods, Integer boost) {
		super(decoratedMethods);
		this.productionBonus = boost;
	}

	@Override
	public boolean activateProduction(Integer value,NetworkPlayer player,PersonalBoardHandler personalBoardHandler,FamilyMember familyMember) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotEnoughResourcesException, NotEnoughPointsException, IOException, InvalidActionTypeException{
		return decoratedMethods.activateProduction(value + productionBonus,player,personalBoardHandler,familyMember);
	}

}

