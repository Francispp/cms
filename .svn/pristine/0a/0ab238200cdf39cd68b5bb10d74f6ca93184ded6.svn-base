/*
 * 创建日期 2010-7-19
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.cyberway.common.login.edu;

/**
 * @author cn084602
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


public class Des {
	private byte[] desKey;

	
	//解密数据
	public static String decrypt(String message,String key) throws Exception {
		 
        byte[] bytesrc =convertHexString(message);   
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");    
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));   
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");   
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);   
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
            
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);      
      
        byte[] retByte = cipher.doFinal(bytesrc);     
        return new String(retByte); 
	}

	public static byte[] encrypt(String message, String key)
			throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return cipher.doFinal(message.getBytes("UTF-8"));
	}
	
	public static byte[] convertHexString(String ss) 
	{ 
		byte digest[] = new byte[ss.length() / 2]; 
		for(int i = 0; i < digest.length; i++) 
		{ 
		String byteString = ss.substring(2 * i, 2 * i + 2); 
		int byteValue = Integer.parseInt(byteString, 16); 
		digest[i] = (byte)byteValue; 
		} 
	
		return digest; 
	}
	
	public static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			else if(1==1){
				
			}
			hexString.append(plainText);
		}
		
		return hexString.toString();
	}
	
	public static String MD5EnCrypt(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7',
							'8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		String key = "11111111";
		
		{
		String value="3600000034470320100710120000";
		String jiami=java.net.URLEncoder.encode(value, "utf-8").toLowerCase();
		
		System.out.println("加密数据:"+jiami);
		String a=toHexString(encrypt(jiami, key)).toUpperCase();
		
	
		System.out.println("加密后的数据为:"+a);
		String b=java.net.URLDecoder.decode(decrypt(a,key), "utf-8") ;
		System.out.println("解密后的数据:"+b);
		}
//		
//		{			
//			String value="3600000021102120100710130000";
//			String jiami=java.net.URLEncoder.encode(value, "utf-8").toLowerCase();
//			
//			System.out.println("加密数据:"+jiami);
//			String a=toHexString(encrypt(jiami, key)).toUpperCase();
//			
//		
//			System.out.println("加密后的数据为:"+a);
//			String b=java.net.URLDecoder.decode(decrypt(a,key), "utf-8") ;
//			System.out.println("解密后的数据:"+b);
//		}
		
		{
			String value="344703";
			System.out.println(new String(encrypt(value, key)));
		}

	}
}
