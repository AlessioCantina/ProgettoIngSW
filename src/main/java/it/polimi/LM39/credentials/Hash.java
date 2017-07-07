package it.polimi.LM39.credentials;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * The following is a snippet of code from https://stackoverflow.com/questions/18142745/how-do-i-generate-a-salt-in-java-for-salted-hash
 * I wrote the methods login, register and unregister
 * I (Alessio) like hashes and ciphers, so even if this level of security is not needed by the game, I decided to store the login passwords salted and hashed
 * in an hashmap
 */

/**
 * A utility class to hash passwords and check passwords vs hashed values. It uses a combination of hashing and unique
 * salt. The algorithm used is PBKDF2WithHmacSHA1 which, although not the best for hashing password (vs. bcrypt) is
 * still considered robust and recommended by NIST
 * The hashed value has 256 bits.
 */
public class Hash {

  private static final Random RANDOM = new SecureRandom();
  private static final int ITERATIONS = 10000;
  private static final int KEY_LENGTH = 256;
  private static HashMap<String,PswAndSalt> credentialsMap = new HashMap<String,PswAndSalt>();


  /**
   * Returns a random salt to be used to hash a password.
   *
   * @return a 16 bytes random salt
   */
  public static byte[] getNextSalt() {
    byte[] salt = new byte[16];
    RANDOM.nextBytes(salt);
    return salt;
  }

  /**
   * Returns a salted and hashed password using the provided hash.
   * Note - side effect: the password is destroyed (the char[] is filled with zeros)
   *
   * @param password the password to be hashed
   * @param salt     a 16 bytes salt, ideally obtained with the getNextSalt method
   *
   * @return the hashed password with a pinch of salt
   */
  public static byte[] hash(char[] password, byte[] salt) {
    PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
    Arrays.fill(password, Character.MIN_VALUE);
    try {
      SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      return skf.generateSecret(spec).getEncoded();
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
    } finally {
      spec.clearPassword();
    }
  }

  /**
   * Returns true if the given password and salt match the hashed value, false otherwise.
   * Note - side effect: the password is destroyed (the char[] is filled with zeros)
   *
   * @param password     the password to check
   * @param salt         the salt used to hash the password
   * @param expectedHash the expected hashed value of the password
   *
   * @return true if the given password and salt match the hashed value, false otherwise
   */
  public static boolean isExpectedPassword(char[] password, byte[] salt, byte[] expectedHash) {
    byte[] pwdHash = hash(password, salt);
    Arrays.fill(password, Character.MIN_VALUE);
    if (pwdHash.length != expectedHash.length) return false;
    for (int i = 0; i < pwdHash.length; i++) {
      if (pwdHash[i] != expectedHash[i]) return false;
    }
    return true;
  }
  
  /**
   * to register a player on the server
   * @param nickName   the player nick name
   * @param password   the password chosen by the player
   */
  public static void register(String nickName, String password){
	  PswAndSalt pswAndSalt = new PswAndSalt();
	  byte[] salt = getNextSalt();
	  pswAndSalt.setSalt(salt);
	  byte[] hashed_psw = hash(password.toCharArray(),salt);
	  pswAndSalt.setPassword(hashed_psw);
	  credentialsMap.put(nickName,pswAndSalt);
  }
  
  /**
   * to login a player on the server
   * @param nickName   the player nick name 
   * @param password   the player password to check
   * @return
   */
  public static boolean login(String nickName, String password){
	  if(credentialsMap.containsKey(nickName)){
		  PswAndSalt pswAndSalt = credentialsMap.get(nickName);
	  	return isExpectedPassword(password.toCharArray(), pswAndSalt.getSalt(), pswAndSalt.getPassword());
	  }
	  else
		  return false;
  }
  
  /**
   * to unregister a player at the end of a game
   * @param nickName
   */
  public static void unregister(String nickName){
	  credentialsMap.remove(nickName);
  }

}
