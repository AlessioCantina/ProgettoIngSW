package it.polimi.LM39.server;
import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.model.Player;
/*
 * network player extended by socket player
 *
 */
public abstract class NetworkPlayer extends Player{

	private static final long serialVersionUID = 3267472590124957050L;
	protected static transient Object DISCONNECT_LOCK = new Object();
	private String nickName = "";
	private Boolean idleStatus = false;
	/**
	 * get and set nickname
	 */
	public String getNickName(){
		return this.nickName;
	}
	public void setNickName(String nickName){
		this.nickName = nickName;
	}
	/**
	 * get and set the player's idle state
	 * if idle server will ignore this player
	 * @param status
	 */
	public void setIdleStatus(Boolean status){
		this.idleStatus = status;
	}
	public Boolean getIdleStatus(){
		return this.idleStatus;
	}

	/**
	 * method that will be implemented by socket player to communicate with the controller
	 * @return
	 */
	public abstract String sendMessage();
	/**
	 * method that will be implemented by socket player
	 * will be used by controller to set message to send
	 */
	public abstract void setMessage(String message);
	/**
	 * method that will be implemented by socket to communicate with the controller
	 * will be used by controller to set mainboard to send
	 */
	public abstract void setMessage(MainBoard mainBoard);
}
