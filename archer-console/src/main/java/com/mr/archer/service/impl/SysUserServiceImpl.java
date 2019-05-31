package com.mr.archer.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mr.archer.dao.SysUserMapper;
import com.mr.archer.entity.SysRole;
import com.mr.archer.entity.SysUser;
import com.mr.archer.exception.ArcherBusinessException;
import com.mr.archer.service.SysUserService;
import com.mr.archer.utils.SystemUtil;
import com.mr.archer.vo.AuthVo;
import com.mr.archer.constant.SystemConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Value("${superAdmin.username}")
    private String adminUsername;

    @Value("${superAdmin.password}")
    private String adminPassword;

    @Value("${superAdmin.nick}")
    private String adminNick;

    @Value("${superAdmin.mail}")
    private String adminMail;

    @Value("${superAdmin.phone}")
    private String adminPhone;

    @Value("${superAdmin.avator}")
    private String adminAvator;

    @Value("${superAdmin.roleName}")
    private String adminRoleName;

    @Value("${superAdmin.roleDesc}")
    private String adminRoleDesc;

    @Value("${superAdmin.roleValue}")
    private String adminRoleValue;

    @Value("${superAdmin.permValue}")
    private String adminPermValue;

    @Value("${superAdmin.permName}")
    private String adminPermName;

    private SysUser superAdmin;

    @Override
    public Page<SysUser> queryUserIncludeRoles(Page<SysUser> page, String nick) {
        return page.setRecords(baseMapper.selectUserIncludeRoles(page,nick));
    }


    public Boolean isAdmin(String username){
        return username.equals(adminUsername);
    }

    public SysUser getAdmin() {
        if(!SystemUtil.isStared()) throw new ArcherBusinessException(String.format("%s has not been started", SystemConstant.SYSTEM_NAME));
        if(Objects.nonNull(superAdmin)) return superAdmin;
        SysUser user = new SysUser();
        user.setUsername(adminUsername);
        user.setNick(adminNick);
        user.setMail(adminMail);
        user.setPhone("18800000000");
        SysRole role = new SysRole();
        role.setRoleName(adminRoleName);
        role.setRoleValue(adminRoleValue);
        role.setRoleDesc(adminRoleDesc);
        user.setRoleList(Lists.newArrayList(role));
        AuthVo roleVo = new AuthVo(adminRoleName, adminRoleValue);
        user.setPerms(Sets.newHashSet(roleVo));
        AuthVo permVo = new AuthVo(adminPermName, adminPermValue);
        user.setPerms(Sets.newHashSet(permVo));
        return superAdmin = user;
    }

    public Boolean checkLoginForAdmin(String username, String password){
        return adminUsername.equals(username) && adminPassword.equals(password);
    }

    @Override
    public SysUser selectUserByToken(String token) {
        return baseMapper.selectUserByToken(token);
    }

    @Override
    public String selectUsernameByToken(String token) {
        return baseMapper.selectUserByToken(token).getUsername();
    }

}
