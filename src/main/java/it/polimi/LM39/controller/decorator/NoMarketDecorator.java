package it.polimi.LM39.controller.decorator;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.server.NetworkPlayer;

/**
 * this class decorates DecoratedMethods with the decorator of addFamilyMemberToTheMarket
 */
public class NoMarketDecorator extends DecoratedMethodsDecorator{

	
	public NoMarketDecorator (DecoratedMethods decoratedMethods) {
		super(decoratedMethods);
	}

	
	/**
	 * this method overrides addFamilyMemberToTheMarket to prevent the player from going to the market if he has the excommunication that prevent that
	 */
	@Override
	public boolean addFamilyMemberToTheMarket(FamilyMember familyMember, Integer position, NetworkPlayer player) throws NotEnoughResourcesException, NotEnoughPointsException {
		player.setMessage("Because of the Excommunication you can’t place your Family Members in the Market action spaces");
		return false;
	}
	
}
