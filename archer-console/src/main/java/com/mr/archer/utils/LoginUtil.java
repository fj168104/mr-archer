package com.mr.archer.utils;

import com.mr.archer.constant.SystemConstant;
import com.mr.archer.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

/**
 * Created by feng on 2019/5/21
 */
public class LoginUtil {
	private final static Logger logger = LoggerFactory.getLogger(LoginUtil.class);

	public static boolean passportCheck(String username, String password) {
		//check super admin
		SysUserService userService = SpringUtils.getBean(SysUserService.class);
		if(userService.isAdmin(username)){
			return userService.checkLoginForAdmin(username, password);
		}else{

			//LDAP登陆地址, ldap为空时也均返回成功
			String ldapUrl = SystemConstant.LDAP_URL;
			if (StringUtils.isBlank(ldapUrl)) {
				logger.warn("ldap url is empty!!");
				return true;
			}
			if (SystemConstant.IS_DEBUG) {
				logger.warn("isDebug=true return");
				return true;
			}
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
			env.put("java.naming.provider.url", ldapUrl);
			env.put("java.naming.security.authentication", "simple");
			env.put("java.naming.security.principal", username + SystemConstant.EMAIL_SUFFIX);
			env.put("java.naming.security.credentials", password);
			DirContext ctx = null;
			try {
				ctx = new InitialDirContext(env);
				return true;
			} catch (Exception e) {
				logger.error("username {} passportCheck: " + e.getMessage(), username, e);
			} finally {
				if (ctx != null) {
					try {
						ctx.close();
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}


		return false;
	}
}
