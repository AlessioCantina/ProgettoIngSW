package it.polimi.LM39.controller.decorator;

import it.polimi.LM39.controller.DecoratedMethods;
import it.polimi.LM39.exception.NotEnoughPointsException;
import it.polimi.LM39.model.CardPoints;
import it.polimi.LM39.model.PlayerPoints;
import it.polimi.LM39.server.NetworkPlayer;

/**
 * this class decorates DecoratedMethods with the decorator of addCardPoints
 */
public class MilitaryPointsMalusDecorator extends DecoratedMethodsDecorator{

	private Integer militaryMalus;
	
	public MilitaryPointsMalusDecorator (DecoratedMethods decoratedMethods, Integer militaryMalus) {
		super(decoratedMethods);
		this.militaryMalus = militaryMalus;
	}
	
	/**
	 * this method overrides addCardPoints to set a malus on military points received from cards
	 */
	@Override
	public void addCardPoints (CardPoints points, NetworkPlayer player) throws NotEnoughPointsException{
			PlayerPoints playerPoints = player.points;
	    	playerPoints.setFaith(points.faith);
	    	playerPoints.setVictory(points.victory);
	    	if (points.military >= militaryMalus)
	    		playerPoints.setMilitary(points.military - militaryMalus);
	    	player.points=playerPoints;
	}
	
}
