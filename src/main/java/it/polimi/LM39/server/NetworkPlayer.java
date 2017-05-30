package it.polimi.LM39.server;
import it.polimi.LM39.model.Player;
public abstract class NetworkPlayer extends Player{
	private Room room;
	private String nickName;
	
	public void setRoom(Room room){
		this.room = room;
	}
	public Room getRoom(){
		return this.room;
	}
	public String getNickName(){
		return this.nickName;
	}
	public void setNickName(String nickName){
		this.nickName = nickName;
	}
}
