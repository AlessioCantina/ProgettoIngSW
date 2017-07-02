package junittest;

import it.polimi.LM39.model.MainBoard;
import it.polimi.LM39.server.NetworkPlayer;

public class TestPlayer extends NetworkPlayer{

	@Override
	public String sendMessage() {
		return null;
	}

	@Override
	public void setMessage(String message) {
	}

	@Override
	public void setMessage(MainBoard mainBoard) {
	}

}
