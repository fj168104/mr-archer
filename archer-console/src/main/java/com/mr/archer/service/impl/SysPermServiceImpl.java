package com.mr.archer.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mr.archer.dao.SysPermMapper;
import com.mr.archer.entity.SysPerm;
import com.mr.archer.service.SysPermService;
import com.mr.archer.vo.AuthVo;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysPermServiceImpl extends ServiceImpl<SysPermMapper, SysPerm> implements SysPermService {

    @Override
    public Set<AuthVo> getPermsByUserId(Integer userId) {
        Set<SysPerm> set = baseMapper.getPermsByUserId(userId);
        return set.stream().map(p->new AuthVo(p.getPermName(),p.getPermValue())).collect(Collectors.toSet());
    }

    @Override
    public Set<SysPerm> getPermsByRoleId(Integer roleId) {
        return baseMapper.getPermsByRoleId(roleId);
    }

}
