package com.egeinfo.security;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.egeinfo.pojo.Users;
import com.egeinfo.service.BaseService;
import com.egeinfo.service.UserService;
import com.egeinfo.utils.Encry;
import com.egeinfo.utils.JodaTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

public class CustomUserAuthFilter extends UsernamePasswordAuthenticationFilter {
	private final static Log LOG = LogFactory.getLog(CustomUserAuthFilter.class);

	public static final String VALIDATE_CODE = "validateCode";

	/*
	 * public static final String USERNAME = "j_username";
	 *
	 * public static final String PASSWORD = "j_password";
	 *
	 * public static final String ID_CARD = "j_idCard"; // 身份证号。如果有身份证号，就证明是ukey登录
	 *
	 * public static final String TOKEN = "j_token"; // token信息，用于单点登录
	 */
	// 通过自动加载
	@Autowired
	private UserService sysUserService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		String username = obtainUsername(request);
		String password = obtainPassword(request);
		HttpSession session = request.getSession(false);
		if (session != null || getAllowSessionCreation()) {
			request.getSession().setAttribute("j_username", username);
			request.getSession().setAttribute("j_password", password);
		}

		username = username.trim();
		Users temp = new Users();
		Users sysUser = null;
		temp.setUserName(username);
		sysUser = (Users) sysUserService.selectOne("UserMapper.findByProperties", temp);
		// 对应用户不存在
		if (sysUser == null) {
			throw new UsernameNotFoundException(username);
		}
		boolean loginCodeMode = false;
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		if (session != null || getAllowSessionCreation()) {
			Map<String,String> roleMap = new HashMap<>();
			roleMap.put(sysUser.getRoleId()+"",sysUser.getRoleName());
			request.getSession().setAttribute("roles", roleMap);
			request.getSession().setAttribute("LOGIN_USER", sysUser);
		}
		// 允许子类设置详细属性
		setDetails(request, authRequest);

		// 运行UserDetailsService的loadUserByUsername 再次封装Authentication
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected void checkValidateCode(HttpServletRequest request) {
		HttpSession session = request.getSession();

		String sessionValidateCode = obtainSessionValidateCode(session);
		// 让上一次的验证码失效
		session.setAttribute(VALIDATE_CODE, null);
		String validateCodeParameter = obtainValidateCodeParameter(request);
		if (StringUtils.isEmpty(validateCodeParameter) || !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {
			throw new AuthenticationServiceException("validateCode.notEquals");
		}
	}

	private String obtainValidateCodeParameter(HttpServletRequest request) {
		Object obj = request.getParameter(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

	protected String obtainSessionValidateCode(HttpSession session) {
		Object obj = session.getAttribute(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}
	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter("j_username");
		return null == obj ? "" : obj.toString();
	}
	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter("j_password");
		return null == obj ? "" : obj.toString();
	}
}