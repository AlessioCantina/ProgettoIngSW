package it.polimi.LM39.exception;

public class CardNotFoundException extends Exception{

	private static final long serialVersionUID = 8810824073069983997L;

	public CardNotFoundException (String s){
		super(s);
	}
}