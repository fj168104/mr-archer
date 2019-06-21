package com.mr.archer.config.interceptor;

import com.mr.archer.constant.SystemConstant;
import com.mr.archer.exception.UnauthenticatedException;
import com.mr.archer.service.SysUserService;
import com.mr.archer.utils.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mr.archer.utils.RequestHelper.getCookieParam;
import static com.mr.archer.utils.RequestHelper.setUsername;

public class PermissionInterceptor extends HandlerInterceptorAdapter {


	private SysUserService userService = SpringUtils.getBean(SysUserService.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return super.preHandle(request, response, handler);
		}

		if (!ifLogin(request)) {
			throw new UnauthenticatedException();
		}

		return super.preHandle(request, response, handler);
	}

	private boolean ifLogin(HttpServletRequest request){
		String token = getCookieParam(request, SystemConstant.USER_TOKEN);
		if(StringUtils.isEmpty(token)) return false;
		String username = userService.selectUsernameByToken(token);
		if(StringUtils.isEmpty(username)) return false;
		setUsername(username);
		return true;
	}
}