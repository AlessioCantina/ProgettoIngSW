package it.polimi.LM39.exception;

public class FailedToReadFileException extends Exception{
	public FailedToReadFileException(Exception s){
		super(s);
	}
}
