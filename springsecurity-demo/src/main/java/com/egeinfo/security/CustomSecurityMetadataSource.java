package com.egeinfo.security;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;

public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	private final static Log LOG = LogFactory.getLog(CustomSecurityMetadataSource.class);

	private AntPathRequestMatcher pathMatcher;

	// 用来保存从数据库中读取的资源
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public CustomSecurityMetadataSource() {
		loadResourceDefine();
	}

	private void loadResourceDefine() {
		if (resourceMap == null) {
			resourceMap = new ConcurrentHashMap<String, Collection<ConfigAttribute>>();
		} else {
			resourceMap.clear();
		}
	}
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
		HttpServletResponse response = ((FilterInvocation) object).getHttpResponse();
		// SESSION过期检查机制
		try {
			if (request.getSession().getAttribute("SESSION_LAST_ACTION_TIME") != null) {

			}
		} catch (Exception e) {
			LOG.error("SESSION过期检查出现异常", e);

		}
		if (resourceMap == null) {
			loadResourceDefine();
		}

		Iterator<String> it = resourceMap.keySet().iterator();

		while (it.hasNext()) {
			String resURL = it.next();
			pathMatcher = new AntPathRequestMatcher(resURL);
			if (pathMatcher.matches(request)) {
				Collection<ConfigAttribute> returnCollection = resourceMap.get(resURL);
				return returnCollection;
			}
		}
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO 有差异
		return null;
	}

	public void refreshSecurityMetadataSource() {
		loadResourceDefine();
	}
}
