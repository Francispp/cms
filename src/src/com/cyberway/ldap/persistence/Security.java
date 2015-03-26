package com.cyberway.ldap.persistence;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class Security {
	  /**
	   * a message digest algorithm "MD5" to convert normal message to
	   * security message.
	   * @param str      normal message that you want to convert.
	   * @return         security message.
	   */
	  public static String encodeToMD5(String str) {
	    if (str == null) {
	      return null;
	    }
	    String digstr = "";
	    MessageDigest MD = null;
	    try {
	      MD = MessageDigest.getInstance("MD5");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    byte[] oldbyte = new byte[str.length()];
	    for (int i = 0; i < str.length(); i++) {
	      oldbyte[i] = (byte) str.charAt(i);
	    }
	    MD.update(oldbyte);
	    byte[] newbyte = null;
	    newbyte = MD.digest(oldbyte);
	    for (int i = 0; i < newbyte.length; i++) {
	      digstr = digstr + newbyte[i];
	    }
	    return digstr;
	  }

		/**
		 * Encrypt a string using the SHA-1 hash algorithm
		 * 
		 * @param x The String to encrypt
		 * @return The encrypted result
		 * @throws Exception
		 */
		public static String encryptSHA(String x) throws NoSuchAlgorithmException{
			MessageDigest d = java.security.MessageDigest.getInstance("SHA-1");
			d.reset();
			d.update(x.getBytes());
			//Base64 encode the result in case it contains non-printable characters
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(d.digest());
		}
}
