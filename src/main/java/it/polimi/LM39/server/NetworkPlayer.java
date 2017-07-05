package it.polimi.LM39.server;
import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.model.Player;
/*
 * networkplayer can be extended by rmiplayer or socketplayer similarly to abstractserver and
 * socket and rmi servers
 */
public abstract class NetworkPlayer extends Player{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3267472590124957050L;
	protected static transient Object DISCONNECT_LOCK = new Object();
	private String nickName;
	private Boolean idleStatus = false;
	/*
	 * get and set nickname
	 */
	public String getNickName(){
		return this.nickName;
	}
	public void setNickName(String nickName){
		this.nickName = nickName;
	}
	public void setIdleStatus(Boolean status){
		this.idleStatus = status;
	}
	public Boolean getIdleStatus(){
		return this.idleStatus;
	}

	/*
	 * method that will be implemented by both rmi and socket to communicate with the controller
	 */
	public abstract String sendMessage();
	
	public abstract void setMessage(String message);
	public abstract void setMessage(MainBoard mainBoard);
}
