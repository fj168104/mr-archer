package com.mr.archer.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mr.archer.entity.SysUser;

public interface SysUserService extends IService<SysUser> {

    Page<SysUser> queryUserIncludeRoles(Page<SysUser> page, String nick);

	SysUser selectOne(Wrapper<SysUser> uname);

	Boolean isAdmin(String username);

	SysUser getAdmin();

	Boolean checkLoginForAdmin(String username, String password);

	SysUser selectUserByToken(String token);

	String selectUsernameByToken(String token);
}
