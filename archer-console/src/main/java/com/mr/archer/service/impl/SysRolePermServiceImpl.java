package com.mr.archer.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mr.archer.dao.SysRolePermMapper;
import com.mr.archer.entity.SysRolePerm;
import com.mr.archer.service.SysRolePermService;
import org.springframework.stereotype.Service;

@Service
public class SysRolePermServiceImpl extends ServiceImpl<SysRolePermMapper, SysRolePerm> implements SysRolePermService {
}
