package junittest;

import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.server.NetworkPlayer;

public class TestPlayer extends NetworkPlayer{
	private String response="no";
	
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

}
