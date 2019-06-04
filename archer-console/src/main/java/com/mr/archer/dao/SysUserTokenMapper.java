package com.mr.archer.dao;

import com.mr.archer.entity.SysUserToken;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户登录token表 Mapper 接口
 * </p>
 *
 * @author jiang.feng
 * @since 2019-05-31
 */

@Mapper
public interface SysUserTokenMapper extends BaseMapper<SysUserToken> {

	void clearToken(@Param("token") String token);


}
