package it.polimi.LM39.server;

public interface ServerInterface {
	public void loginPlayer(String nickName, NetworkPlayer networkPlayer);
	NetworkPlayer getPlayer(String nickName);
	public void joinRoom(NetworkPlayer networkPlayer);
	public Room getRoom(Integer roomNumber);
}
