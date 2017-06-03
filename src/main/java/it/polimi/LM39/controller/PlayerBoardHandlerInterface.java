package it.polimi.LM39.controller;

import it.polimi.LM39.server.NetworkPlayer;

public interface PlayerBoardHandlerInterface {

	public void activateHarvest(Integer actionValue, NetworkPlayer player);
	public void activateProduction(Integer actionValue, NetworkPlayer player);
}
