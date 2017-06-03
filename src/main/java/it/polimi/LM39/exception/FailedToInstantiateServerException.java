package it.polimi.LM39.exception;



public class FailedToInstantiateServerException extends Exception{
	public FailedToInstantiateServerException(Exception e){
		super(e);
	}
}
