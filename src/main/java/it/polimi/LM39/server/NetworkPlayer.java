package it.polimi.LM39.server;

public abstract class NetworkPlayer{
	private Room room;
	private String nickName;
	
	public void setRoom(Room room){
		this.room = room;
	}
	public Room getRoom(){
		return this.room;
	}
	public void setNickName(String nickName){
		this.nickName = nickName;
	}
}
