package com.cyberway.common.ftpservice.service;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

/**
 * com.cyberway.common.ftpservice.service.ThreeDES
 *
 * @author Janice Yang
 *
 * @createTime 2012-3-6 下午3:43:39 
 *
 * @Description:
 *
 */
public class ThreeDES {
	private Key	key; // 密钥

	public void getKey(String strKey) {
		/*try {
			KeyGenerator _generator = KeyGenerator.getInstance("DES");
			SecureRandom secureRandom = new SecureRandom(strKey.getBytes());  
			secureRandom.setSeed(strKey.getBytes());  
			_generator.init(128,secureRandom);
			this.key = _generator.generateKey();
			_generator = null;
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * 加密
	 * @param strMing
	 * @return
	 */
	public String getEncString(String strMing) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byteMing = strMing.getBytes("UTF8");
			//byteMi = this.getEncCode(byteMing);
			strMi = base64en.encode(byteMing);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			base64en = null;
			byteMing = null;
			byteMi = null;
		}
		return strMi;
	}

	/**
	 * 解密
	 * @param strMi
	 * @return
	 */
	public String getDesString(String strMi) {
		BASE64Decoder base64De = new BASE64Decoder();
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
			byteMi = base64De.decodeBuffer(strMi);
			//byteMing = this.getDesCode(byteMi);
			strMing = new String(byteMi, "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			base64De = null;
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}

	/*private byte[] getEncCode(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	private byte[] getDesCode(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	public static void main(String[] args) {
		ThreeDES des = new ThreeDES(); // 实例化一个对像
		des.getKey("abcdefgasiainfo"); // 生成密匙

		String strEnc = des.getEncString("abc");// 加密字符串,返回String的密文
		System.out.println(strEnc);

		String strDes = des.getDesString(strEnc);// 把String 类型的密文解密
		System.out.println(strDes);
	}*/
}
