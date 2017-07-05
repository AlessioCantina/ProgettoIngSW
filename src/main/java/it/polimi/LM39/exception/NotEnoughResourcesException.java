package it.polimi.LM39.exception;

public class NotEnoughResourcesException extends Exception{

	private static final long serialVersionUID = -6620522530090261416L;

	public NotEnoughResourcesException(String s){
		super(s);
	}
}
