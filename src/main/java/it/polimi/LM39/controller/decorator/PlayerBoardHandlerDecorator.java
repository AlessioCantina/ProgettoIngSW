package it.polimi.LM39.controller.decorator;

import it.polimi.LM39.controller.PlayerBoardHandlerInterface;

public abstract class PlayerBoardHandlerDecorator implements PlayerBoardHandlerInterface{
	protected PlayerBoardHandlerInterface decoratedPlayerBoard;
	public PlayerBoardHandlerDecorator(PlayerBoardHandlerInterface decoratedPlayerBoard){
		this.decoratedPlayerBoard = decoratedPlayerBoard;
	}
}
