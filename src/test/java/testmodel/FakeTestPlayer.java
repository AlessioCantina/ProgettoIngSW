package testmodel;

import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.server.NetworkPlayer;
/**
 * fake player that we use to make tests
 * 
 *
 */
public class FakeTestPlayer extends NetworkPlayer{
	private String response="1";
	
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String sendMessage() {
		return response;
	}

	@Override
	public void setMessage(String message) {
	}

	@Override
	public void setMessage(MainBoard mainBoard) {
	}

	@Override
	public void setMessage(Integer mainBoard) {
		
	}

}
