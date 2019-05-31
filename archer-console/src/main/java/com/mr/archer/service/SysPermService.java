package com.mr.archer.service;

import com.baomidou.mybatisplus.service.IService;
import com.mr.archer.entity.SysPerm;
import com.mr.archer.vo.AuthVo;

import java.util.Set;

public interface SysPermService extends IService<SysPerm> {

    Set<AuthVo> getPermsByUserId(Integer userId);

    Set<SysPerm> getPermsByRoleId(Integer roleId);

}
