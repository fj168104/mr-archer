package com.mr.archer.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mr.archer.dao.SysRoleMapper;
import com.mr.archer.entity.SysRole;
import com.mr.archer.service.SysRoleService;
import com.mr.archer.vo.AuthVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public Set<AuthVo> getRolesByUserId(Integer userId) {
        List<SysRole> list = baseMapper.getRolesByUserId(userId);
        return list.stream().map(r->new AuthVo(r.getRoleName(),r.getRoleValue())).collect(Collectors.toSet());
    }

    @Override
    public List<Integer> getRoleIdsByUserId(Integer userId) {
        return baseMapper.getRoleIdsByUserId(userId);
    }

    @Override
    public Set<String> getRoleValuesByUserId(Integer userId) {
        List<SysRole> list = baseMapper.getRolesByUserId(userId);
        return list.stream().map(r->r.getRoleValue()).collect(Collectors.toSet());
    }

    @Override
    public boolean checkRidsContainRval(List<Integer> rids, String rval) {
        if (rids.isEmpty()) return false;
        Boolean re = baseMapper.checkRidsContainRval(rids, rval);
        return re==null?false:re.booleanValue();
    }

    @Override
    public boolean checkUidContainRval(Integer uid, String roleValue) {
        Boolean re = baseMapper.checkUidContainRval(uid, roleValue);
        return re==null?false:re.booleanValue();
    }

}
