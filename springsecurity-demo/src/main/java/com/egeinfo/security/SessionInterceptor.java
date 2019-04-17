package com.egeinfo.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.egeinfo.pojo.Users;
import com.egeinfo.utils.UtilString;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionInterceptor extends HandlerInterceptorAdapter {
	private static Log log = LogFactory.getLog(SessionInterceptor.class);

	private ResourceBundle resourceBundle;
	
	public SessionInterceptor() {
		 resourceBundle = ResourceBundle.getBundle("security-uri"); 
	}
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		HttpSessionRequestCache requestCache = new HttpSessionRequestCache();

		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {
			requestCache.removeRequest(request, response);
		}

		HttpSession session = request.getSession();

		String uri = UtilString.strNull(request.getRequestURI());

		if (uri.indexOf("/login.html") >= 0) {
			return true;
		}

        //登录页放开
        if (isNotNeedLogin(request)) {
            return true;
        }
		if (session == null
				|| (Users) session.getAttribute("LOGIN_USER") == null
				|| StringUtils.isEmpty(((Users) session
						.getAttribute("LOGIN_USER")).getUserName())) {
			try {
				log.debug("X-Requested-With="
						+ request.getHeader("X-Requested-With"));
				log.debug("ajax=" + request.getParameter("ajax"));
				if (isAjax(request)) {
					response.setCharacterEncoding("utf-8");
					response.setContentType("text/json");
					PrintWriter out = response.getWriter();
					out.println("{\"statusCode\":\"301\",\"message\":\""
							+ "会话超时，请重新登录！" + "\"}");
				} else {
					response.sendRedirect(request.getContextPath()
							+ "/login.html");
				}
			} catch (IOException e) {
				log.error("LoginController.welcome", e);
			}

			log.debug("当前未登陆或者会话超时，请重新登录！");
			return false;
		}
		return true;
	}

	private boolean isNotNeedLogin(HttpServletRequest request) {
		Enumeration<String> keys = resourceBundle.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			AntPathRequestMatcher matcher = new AntPathRequestMatcher(resourceBundle.getString(key));
			if (matcher.matches(request)) {
				if (log.isDebugEnabled()) {
					log.debug("=====current uri do not need login, current uri: " + request.getRequestURI());
				}
				return true;
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("=====current uri need login, current uri: " + request.getRequestURI());
		}
		return false;
	}

	private boolean isAjax(HttpServletRequest request) {
		if (request != null
				&& ("XMLHttpRequest".equalsIgnoreCase(request
						.getHeader("X-Requested-With")) || request
						.getParameter("ajax") != null))
			return true;
		return false;
	}
}
