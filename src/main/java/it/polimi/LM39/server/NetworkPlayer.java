package it.polimi.LM39.server;
import it.polimi.LM39.model.Player;
/*
 * networkplayer can be extended by rmiplayer or socketplayer similarly to abstractserver and
 * socket and rmi servers
 */
public abstract class NetworkPlayer extends Player{
	private String nickName;
	/*
	 * get and set nickname
	 */
	public String getNickName(){
		return this.nickName;
	}
	public void setNickName(String nickName){
		this.nickName = nickName;
	}
	/*
	 * method that will be implemented by both rmi and socket to communicate with the controller
	 */
	public abstract String sendMessage();
}
