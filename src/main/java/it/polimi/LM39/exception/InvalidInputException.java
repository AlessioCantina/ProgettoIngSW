package it.polimi.LM39.exception;

public class InvalidInputException extends Exception{

	private static final long serialVersionUID = -746512926614504348L;

	public InvalidInputException(String s){
		super(s);
	}
}