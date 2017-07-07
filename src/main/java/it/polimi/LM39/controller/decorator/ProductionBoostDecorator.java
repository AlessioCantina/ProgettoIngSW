package it.polimi.LM39.controller.decorator;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.controller.PersonalBoardHandler;
import it.polimi.LM39.exception.InvalidActionTypeException;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.server.NetworkPlayer;

/**
 * this class decorates DecoratedMethods with the decorator of activateProduction
 */
public class ProductionBoostDecorator extends DecoratedMethodsDecorator{
	
	private Integer productionBonus;
	
	public ProductionBoostDecorator (DecoratedMethods decoratedMethods, Integer boost) {
		super(decoratedMethods);
		this.productionBonus = boost;
	}

	/**
	 * this method overrides activateProduction to set a bonus on a production action value
	 */
	@Override
	public boolean activateProduction(Integer value,NetworkPlayer player,PersonalBoardHandler personalBoardHandler,FamilyMember familyMember) throws ReflectiveOperationException , NotEnoughResourcesException, NotEnoughPointsException, InvalidActionTypeException{
		return decoratedMethods.activateProduction(value + productionBonus,player,personalBoardHandler,familyMember);
	}

}

