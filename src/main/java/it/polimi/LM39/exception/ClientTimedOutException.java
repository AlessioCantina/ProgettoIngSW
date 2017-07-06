package it.polimi.LM39.exception;

/**
 * used by client when the user exceeds the timeout
 */
public class ClientTimedOutException extends Exception{


	private static final long serialVersionUID = 4514700562528514089L;

	public ClientTimedOutException (String s){
		super(s);
	}
}
