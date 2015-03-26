package com.cyberway.core.utils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class EncryptionHelper {
    public static String PASS_PHRASE = "passphrase";
   
    private static void getPassPhase()
    {

    }
    public static String encrypt(String msg,String PASS_PHRASE){
    	if(PASS_PHRASE==null)
    		getPassPhase();
    	
        try {
            KeySpec keySpec = new DESKeySpec(PASS_PHRASE.getBytes());
            SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(keySpec);
            Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            //Encode the string into bytes using utf-8
            byte[] utf8 = msg.getBytes("UTF8");
            //Encrypt
            byte[] enc = ecipher.doFinal(utf8);
            //Encode bytes to base64 to get a string
            return new sun.misc.BASE64Encoder().encode(enc);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
   
    public static String decrypt(String msg,String PASS_PHRASE){
    	if(PASS_PHRASE==null)
    		getPassPhase();
    	
        try {
            KeySpec keySpec = new DESKeySpec(PASS_PHRASE.getBytes());
            SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(keySpec);
            Cipher decipher = Cipher.getInstance(key.getAlgorithm());
            decipher.init(Cipher.DECRYPT_MODE, key);
            // Decode base64 to get bytes
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(msg);
            //Decrypt
            byte[] utf8 = decipher.doFinal(dec);
            //Decode using utf-8
            return new String(utf8, "UTF8");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
   
    public static String encodeURL(String url){
        try {
            return URLEncoder.encode(url,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String decodeURL(String url){
        try {
            return URLDecoder.decode(url,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String args[])
    {
    	String url="This is test!";
    	
    	String after_encrypt=encrypt(url,"passphrase");
    	System.out.println(after_encrypt);
    	String after_encrypt_encode=encodeURL(after_encrypt);
    	
    	System.out.println(after_encrypt_encode);
    	System.out.println(decrypt(decodeURL(after_encrypt_encode),"passphra11"));
    	System.out.println(reverse("passphrase"));
    }
    //reverse
	public static String reverse(String s) {
		if (s == null) {
			return null;
		}

		char[] c = s.toCharArray();
		char[] reverse = new char[c.length];

		for (int i = 0; i < c.length; i++) {
			reverse[i] = c[c.length - i - 1];
		}

		return new String(reverse);
	}    
	//get time parameter
	public static long getTimeParm(Date date){
		long tp=0;
		tp=date.getTime()-(date.getTime()/(10000*100000))*(10000*100000);
		return tp/10000;
	}
	//revert time parameter
	public static long revertTimeParm(long tp){
		
		return tp*10000;
	}
	//get PassPhrase
	public  static String getPassPhrase(Date date,long tp){
		long tp1=(date.getTime()/(10000*100000))*(10000*100000)+tp*10000;
		//System.out.println("passPhrase:"+String.valueOf(tp1/10000));
		//System.out.println("reverse passPhrase:"+reverse(String.valueOf(tp1/10000)));
		return reverse(String.valueOf(tp1/10000));
	}    
}
