package com.mr.archer.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mr.archer.entity.SysUserToken;
import com.mr.archer.dao.SysUserTokenMapper;
import com.mr.archer.service.SysUserTokenService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 用户登录token表 服务实现类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-05-31
 */
@Service
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenMapper, SysUserToken> implements SysUserTokenService {

	@Override
	public String refreshToken(String username, String token) {
		SysUserToken userToken = selectOne(new EntityWrapper<SysUserToken>().eq("username", username));
		String oToken = userToken.getToken();
		userToken.setToken(token).setUpdateTime(new Date());
		updateById(userToken);
		return oToken;
	}

	@Override
	public String clearToken(String token) {
		SysUserToken userToken = selectOne(new EntityWrapper<SysUserToken>().eq("token", token));
		baseMapper.clearToken(token);
		return userToken.getUsername();
	}
}
