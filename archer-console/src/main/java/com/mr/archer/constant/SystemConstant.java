package com.mr.archer.constant;

import static org.apache.commons.lang.StringUtils.EMPTY;

/**
 * Created by feng on 2019/5/20
 */
public class SystemConstant {

	public static String USER_TOKEN = "Archer-Token";

	public static String SYSTEM_NAME = "Archer-console";

	public final static String APP_CONTEXT = "/api";

	/**
	 * 管理员相关
	 */
	public static String DEFAULT_SUPER_ADMIN_NAME = "admin";
	public static String SUPER_ADMIN_NAME = DEFAULT_SUPER_ADMIN_NAME;

	public static String DEFAULT_SUPER_ADMIN_PASSWORD = "123456";
	public static String SUPER_ADMIN_PASSWORD = DEFAULT_SUPER_ADMIN_PASSWORD;

	public static String DEFAULT_SUPER_ADMIN_NICK="系统管理员";
	public static String SUPER_ADMIN_NICK = DEFAULT_SUPER_ADMIN_NICK;

	public static String DEFAULT_SUPER_ADMIN_MAIL="jiang.feng@mr.com";
	public static String SUPER_ADMIN_MAIL = DEFAULT_SUPER_ADMIN_MAIL;

	public static String DEFAULT_SUPER_ADMIN_PHONE="18800000000";
	public static String SUPER_ADMIN_PHONE = DEFAULT_SUPER_ADMIN_PHONE;

	public static String DEFAULT_SUPER_ADMIN_AVATAR="https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
	public static String SUPER_ADMIN_AVATAR = DEFAULT_SUPER_ADMIN_AVATAR;

	public static String DEFAULT_SUPER_ADMIN_ROLE_NAME="超级管理员";
	public static String SUPER_ADMIN_ROLE_NAME = DEFAULT_SUPER_ADMIN_ROLE_NAME;

	public static String DEFAULT_SUPER_ADMIN_ROLE_DESC="内置角色";
	public static String SUPER_ADMIN_ROLE_DESC = DEFAULT_SUPER_ADMIN_ROLE_DESC;

	public static String DEFAULT_SUPER_ADMIN_ROLE_VALUE="SuperAdmin";
	public static String SUPER_ADMIN_ROLE_VALUE = DEFAULT_SUPER_ADMIN_ROLE_VALUE;

	public static String DEFAULT_SUPER_ADMIN_PERM_VALUE="*";
	public static String SUPER_ADMIN_PERM_VALUE = DEFAULT_SUPER_ADMIN_PERM_VALUE;

	public static String DEFAULT_SUPER_ADMIN_PERM_NAME="所有权限";
	public static String SUPER_ADMIN_PERM_NAME = DEFAULT_SUPER_ADMIN_PERM_NAME;


	/**
	 * ldap登陆
	 */
	public static String DEFAULT_LDAP_URL = EMPTY;
	public static String LDAP_URL = DEFAULT_LDAP_URL;

	/**
	 * 登陆邮箱后缀
	 */
	public static String EMAIL_SUFFIX = "";

	/**
	 * 是否为调试
	 */
	public static boolean IS_DEBUG;

	/**
	 * 联系人
	 */
	public static String DEFAULT_CONTACT = "jiang.feng@mr.com";
	public static String CONTACT = DEFAULT_CONTACT;


	/**
	 * 文档地址
	 */
	public static String DEFAULT_DOCUMENT_URL = "http://wiki.mr.com";
	public static String DOCUMENT_URL = DEFAULT_DOCUMENT_URL;

	/**
	 * 报警相关
	 */
	public static String DEFAULT_EMAILS = "jiang.feng@mr.com";
	public static String EMAILS = DEFAULT_EMAILS;

	public static String DEFAULT_PHONES = "13812345678,13787654321";
	public static String PHONES = DEFAULT_SUPER_ADMIN_PHONE;

	/**
	 * 邮箱报警接口
	 */
	public static String DEFAULT_EMAIL_ALERT_INTERFACE = EMPTY;
	public static String EMAIL_ALERT_INTERFACE = DEFAULT_EMAIL_ALERT_INTERFACE;

	/**
	 * 短信报警接口
	 */
	public static String DEFAULT_MOBILE_ALERT_INTERFACE = EMPTY;
	public static String MOBILE_ALERT_INTERFACE = DEFAULT_MOBILE_ALERT_INTERFACE;

	/**
	 * 客户端可用版本
	 */
	public static String DEFAULT_GOOD_CLIENT_VERSIONS = "1.0-SNAPSHOT";
	public static String GOOD_CLIENT_VERSIONS = DEFAULT_GOOD_CLIENT_VERSIONS;

	/**
	 * 客户端警告版本
	 */
	public static String DEFAULT_WARN_CLIENT_VERSIONS = "0.1";
	public static String WARN_CLIENT_VERSIONS = DEFAULT_WARN_CLIENT_VERSIONS;


	/**
	 * 客户端错误版本
	 */
	public static String DEFAULT_ERROR_CLIENT_VERSIONS = "0.0";
	public static String ERROR_CLIENT_VERSIONS = DEFAULT_ERROR_CLIENT_VERSIONS;

	/**
	 * 1是session,2是cookie
	 */
	public static int DEFAULT_USER_LOGIN_TYPE = 1;
	public static int USER_LOGIN_TYPE = DEFAULT_USER_LOGIN_TYPE;

	/**
	 * cookie登录方式所需要的域
	 */
	public static String DEFAULT_COOKIE_DOMAIN = EMPTY;
	public static String COOKIE_DOMAIN = DEFAULT_COOKIE_DOMAIN;


	/**
	 * 逗号
	 */
	public static final String COMMA = ",";

	/**
	 * 换行
	 */
	public static final String NEXT_LINE = "\n";

	/**
	 * 空格
	 */
	public static final String SPACE = " ";


	/**
	 * 冒号
	 */
	public static final String COLON = ":";

	/**
	 * 星号
	 */
	public static final String STAR = "*";

	/**
	 * 登录跳转参数
	 */
	public final static String RREDIRECT_URL_PARAM = "redirectUrl";

	public volatile static Boolean IS_STARTED;


}
