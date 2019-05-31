package com.mr.archer.service;

import com.baomidou.mybatisplus.service.IService;
import com.mr.archer.entity.SysRole;
import com.mr.archer.vo.AuthVo;

import java.util.List;
import java.util.Set;

public interface SysRoleService extends IService<SysRole> {

    Set<AuthVo> getRolesByUserId(Integer userId);

    List<Integer> getRoleIdsByUserId(Integer userId);

    Set<String> getRoleValuesByUserId(Integer userId);

    boolean checkRidsContainRval(List<Integer> rids, String rval);

    boolean checkUidContainRval(Integer uid, String rval);

}
