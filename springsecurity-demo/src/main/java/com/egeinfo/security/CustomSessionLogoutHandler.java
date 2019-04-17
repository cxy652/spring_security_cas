/*
 * Copyright 2008 by LongTop Corporation.
 * Softpack ChuangXin Building 15F, XiaMen, FuJian, PRC 361005
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * LongTop Corporation ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with LongTop.
 *
 */

package com.egeinfo.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.egeinfo.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class CustomSessionLogoutHandler implements LogoutHandler {
	public void logout(HttpServletRequest request, HttpServletResponse arg1,
			Authentication arg2) {
		HttpSession session = request.getSession(false);
		// 记录用户退出日志
		if (session != null) {
			Users sysUser = (Users) session.getAttribute("LOGIN_USER");
		}
	}
}
