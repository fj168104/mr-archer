package com.mr.archer.service;

import com.mr.archer.entity.SysUserToken;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户登录token表 服务类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-05-31
 */
public interface SysUserTokenService extends IService<SysUserToken> {

	String refreshToken(String username, String token);

	String clearToken(String token);
}
