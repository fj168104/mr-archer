package com.mr.archer.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * Created by feng on 2019/5/20
 */
public class RequestHelper {


	public static String getHeaderParam(HttpServletRequest request, String key) {
		return request.getHeader(key);
	}

	public static String getCookieParam(HttpServletRequest request, String key) {
		if(StringUtils.isEmpty(key)) return EMPTY;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(key)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public static void removeCookie(HttpServletResponse response, String key) {
		Cookie newCookie=new Cookie(key, null); //假如要删除名称为username的Cookie
		newCookie.setMaxAge(0); //立即删除
		newCookie.setPath("/");
		response.addCookie(newCookie);
	}

}
