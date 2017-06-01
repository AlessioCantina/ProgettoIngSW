package it.polimi.LM39.server;

/*
 * interface implemented by server to provide the basic methods to login player, get player, joining
 * a room and getting a room. implemented by server used by rmi and socket
 */
public interface ServerInterface {
	public void loginPlayer(String nickName, NetworkPlayer networkPlayer);
	NetworkPlayer getPlayer(String nickName);
	public void joinRoom(NetworkPlayer networkPlayer);
	public Room getRoom(Integer roomNumber);
}
