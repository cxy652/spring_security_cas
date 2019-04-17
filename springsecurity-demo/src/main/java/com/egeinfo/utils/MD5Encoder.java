package com.egeinfo.utils;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;

public class MD5Encoder implements PasswordEncoder {
	public String encodePassword(String origPwd, Object salt)
			throws DataAccessException {
		return Encry.md5(origPwd);
	}

	public boolean isPasswordValid(String encPwd, String origPwd, Object salt)
			throws DataAccessException {
		return encPwd.equals(encodePassword(origPwd, salt));
	}
}