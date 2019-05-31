package com.mr.archer.constant;

/**
 * 响应的code
 */
public interface Codes {

    /** 未登录 */
    int UNAUTHEN = 50008;

    /** 未授权，拒绝访问 */
    int UNAUTHZ = 50012;

    /** session超时退出登录 */
    int SESSION_TIMEOUT = 50014;

    /** 服务端异常 */
    int SERVER_ERR = 55000;

    /** 服务端异常 */
    int BIZ_ERR = 80000;

}
