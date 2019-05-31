package com.mr.archer.controller;

import com.mr.archer.constant.SystemConstant;
import com.mr.archer.utils.RequestHelper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by feng on 2019/5/18
 */


public class BaseController {

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected HttpServletResponse response;

	public String getCookieParam(String key) {
		return RequestHelper.getCookieParam(request, key);
	}

	public void removeCookie(String key) {
		RequestHelper.removeCookie(response, key);
	}

	public String getToken() {
		return getCookieParam(SystemConstant.USER_TOKEN);
	}

	public void removeToken() {
		removeCookie(SystemConstant.USER_TOKEN);
	}

}
