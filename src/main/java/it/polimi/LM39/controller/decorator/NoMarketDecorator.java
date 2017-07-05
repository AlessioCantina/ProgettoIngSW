package it.polimi.LM39.controller.decorator;

import java.io.IOException;
import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.exception.NotEnoughResourcesException;
import it.polimi.LM39.model.FamilyMember;
import it.polimi.LM39.server.NetworkPlayer;

public class NoMarketDecorator extends DecoratedMethodsDecorator{

	
	public NoMarketDecorator (DecoratedMethods decoratedMethods) {
		super(decoratedMethods);
	}

	
	@Override
	public boolean addFamilyMemberToTheMarket(FamilyMember familyMember, Integer position, NetworkPlayer player) throws IOException, NotEnoughResourcesException, NotEnoughPointsException {
		player.setMessage("Because of the Excommunication you can’t place your Family Members in the Market action spaces");
		return false;
	}
	
}
