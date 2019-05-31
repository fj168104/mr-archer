package com.mr.archer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.entity.SysUser;
import com.mr.archer.entity.SysUserRole;
import com.mr.archer.service.SysRoleService;
import com.mr.archer.service.SysUserRoleService;
import com.mr.archer.service.SysUserService;
import com.mr.archer.utils.PageUtils;
import com.mr.archer.vo.Json;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * created by CaiBaoHong at 2018/4/17 16:41<br>
 */
@PermInfo(value = "系统用户模块")
@RestController
@RequestMapping("/sys_user")
public class SysUserController extends BaseController{

    private static final Logger log = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @PermInfo("更新系统用户的角色")
    @PatchMapping("/role")
    public Json updateUserRole(@RequestBody String body) {

        String oper = "update user's roles";
        log.info("{}, body: {}",oper,body);

        JSONObject json = JSON.parseObject(body);
        final Integer uid = json.getIntValue("uid");
        final String username = json.getString("username");

        List<Integer> rids = json.getJSONArray("rids").toJavaList(Integer.class);

        //检查：不能含有管理员角色
        if (sysUserService.isAdmin(username)){
            return Json.fail(oper,"不能给非管理员用户赋予管理员角色");
        }

        //删除：原来绑定的角色
        boolean deleteSucc = sysUserRoleService.delete(new EntityWrapper<SysUserRole>().eq("user_id", uid));
        if (!deleteSucc) return Json.fail(oper, "无法解除原来的用户-角色关系");

        //更新：绑定新的角色
        List<SysUserRole> list = rids.stream().map(roleId -> new SysUserRole(uid, roleId)).collect(Collectors.toList());

        if (!rids.isEmpty()){
            boolean addSucc = sysUserRoleService.insertBatch(list);
            return Json.result(oper, addSucc);
        }
        return Json.succ(oper);
    }

    @PermInfo("查询所有系统用户")
    @PostMapping("/query")
    public Json query(@RequestBody String body) {
        String oper = "query user";
        log.info("{}, body: {}", oper, body);
        JSONObject json = JSON.parseObject(body);
        String nick = json.getString("nick");
        Page<SysUser> page = sysUserService.queryUserIncludeRoles(PageUtils.getPageParam(json), nick);
        return Json.succ(oper).data("data", page);
    }

    @PermInfo("查询系统用户信息")
    @GetMapping("/info")
    public Json userInfo() {
        String oper = "query user info";
        log.info("{}", oper);
        String username = getCurrentUsername();
        SysUser user = sysUserService.selectOne(new EntityWrapper<SysUser>().eq("username", username));
        return Json.succ(oper, "userInfo", user);
    }

    @PermInfo("更新系统用户的信息")
    @PatchMapping("/info")
    public Json update(@RequestBody String body) {

        String oper = "update user";
        log.info("{}, body: {}", oper, body);

        SysUser user = JSON.parseObject(body, SysUser.class);

        user.setUsername(null);
        user.setUpdateBy(getCurrentUsername());
        user.setUpdateTime(new Date());
        boolean success = sysUserService.updateById(user);

        return Json.result(oper, success).data("updated",user.getUpdateTime());
    }

    @PermInfo("查找系统用户的角色")
    @GetMapping("/{uid}/roles")
    public Json findUserRoles(@PathVariable String uid){
        String oper = "find user roles";
        log.info("{}, uid: {}", oper, uid);
        if (StringUtils.isBlank(uid)){
            return Json.fail(oper, "无法查询当前用户的角色值：参数为空（用户id）");
        }
        if(sysUserService.isAdmin(getCurrentUsername())) {
            return Json.succ(oper,"rids",sysUserService.getAdmin().getRoleList());
        }

        List<Integer> rids = sysRoleService.getRoleIdsByUserId(Integer.parseInt(uid));
        return Json.succ(oper,"rids",rids);
    }


    private String getCurrentUsername(){
        return sysUserService.selectUsernameByToken(getToken());
    }
}
