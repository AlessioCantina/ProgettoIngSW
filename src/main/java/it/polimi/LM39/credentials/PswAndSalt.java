package it.polimi.LM39.credentials;

/**
 * this class stores the hashed password an the salt used for the login
 */
public class PswAndSalt {
	private byte[] password;
	
	private byte[] salt;
	
	
	public byte[] getPassword() {
		return password;
	}
	public void setPassword(byte[] password) {
		this.password = password;
	}
	
	public byte[] getSalt() {
		return salt;
	}
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

}
