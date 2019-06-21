package com.mr.archer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.entity.*;
import com.mr.archer.exception.ArcherBusinessException;
import com.mr.archer.service.*;
import com.mr.archer.utils.PageUtils;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@PermInfo(value = "系统用户模块")
@RestController
@RequestMapping("/user")
public class SysUserController extends BaseController{

    @Autowired
    private SysUserService userService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysUserRoleService userRoleService;
    @Autowired
    private SysUserTokenService userTokenService;
    @Autowired
    private SysPermService permService;

    @PermInfo("更新系统用户的角色")
    @PostMapping("/role")
    public Json updateUserRole(@RequestBody String body) {

        String oper = "update user's roles";
        log.info("{}, body: {}",oper,body);

        JSONObject json = JSON.parseObject(body);
        final Integer uid = json.getIntValue("uid");

        List<Integer> rids = json.getJSONArray("rids").toJavaList(Integer.class);

        //更新：绑定新的角色
        rids.stream().filter(roleId -> userRoleService.selectCount(new EntityWrapper<SysUserRole>()
                .eq("role_id", roleId)
                .and()
                .eq("user_id", uid)) == 0)
                .forEach(roleId -> userRoleService.insert(new SysUserRole(uid, roleId)
						.setCreateBy(getUsername())
						.setCreateTime(new Date())
						.setUpdateBy(getUsername())
				));

		//删除：原来绑定的角色
        if(CollectionUtils.isEmpty(rids)){
            userRoleService.delete(new EntityWrapper<SysUserRole>()
                    .eq("user_id", uid));
        }else {
            userRoleService.delete(new EntityWrapper<SysUserRole>()
                    .eq("user_id", uid)
                    .and()
                    .notIn("role_id", rids));
        }
        return Json.succ(oper);
    }

    @PermInfo("查询所有系统用户")
    @PostMapping("/list")
    public Json query(@RequestBody String body) {
        String oper = "query user";
        log.info("{}, body: {}", oper, body);
        JSONObject json = JSON.parseObject(body);
        Wrapper<SysUser> queryParams = new EntityWrapper<>();

        String nick = json.getString("nick");
        if (StringUtils.isNotBlank(nick)) {
            queryParams.eq("nick", nick);
        }

        String username = json.getString("username");
        if (StringUtils.isNotBlank(username)) {
            if(queryParams.isNotEmptyOfWhere()) queryParams.and();
            queryParams.like("username", username);
        }

        String sort = json.getString("sort");
		if (StringUtils.isNotBlank(sort)) {
			if(sort.contains("+")){
				queryParams.orderBy(sort.substring(1));
			}else {
				queryParams.orderBy(sort.substring(1), false);
			}
		}

        Page<SysUser> page = userService.selectPage(PageUtils.getPageParam(json), queryParams);

        page.getRecords().forEach(user -> {
            if(!CollectionUtils.isEmpty(roleService.getRoleIdsByUserId(user.getId()))){
                user.setRoleList(roleService.selectBatchIds(roleService.getRoleIdsByUserId(user.getId())));
            }
            user.setRoles(roleService.getRolesByUserId(user.getId()));
            user.setPerms(permService.getPermsByUserId(user.getId()));
        });

        return Json.succ(oper).data("data", page);
    }

    @PermInfo("更新系统用户的信息")
    @PostMapping("/update")
    public Json updateUser(@RequestBody String body) {

        String oper = "update user";
        log.info("{}, body: {}", oper, body);

        SysUser user = JSON.parseObject(body, SysUser.class);

        //username can not be modified
		user.setUsername(null);
        user.setUpdateBy(getCurrentUsername());
        user.setUpdateTime(new Date());

        userService.updateById(user);
        return Json.succ(oper);
    }

    @PermInfo("删除系统用户的信息")
    @DeleteMapping("/delete/{uid}")
    public Json delete(@PathVariable Integer uid) {

        String oper = "delete role";

		SysUser user = userService.selectById(uid);
		if(Objects.isNull(user)) {
			log.warn("user[id={}] not exist", uid);
			return Json.succ(oper);
		}
        userService.deleteById(uid);
        userRoleService.delete(new EntityWrapper<SysUserRole>().eq("user_id", uid));
        userTokenService.delete(new EntityWrapper<SysUserToken>().eq("username", user.getUsername()));

        return Json.succ(oper);
    }

    @PermInfo("新增系统用户的信息")
    @PostMapping("/create")
    public Json createUser(@RequestBody String body) {

        String oper = "create user";
        log.info("{}, body: {}", oper, body);

        SysUser user = JSON.parseObject(body, SysUser.class);

        checkUsernameForCreate(user.getUsername());

        user.setCreateBy(getCurrentUsername());
        user.setCreateTime(new Date());
        user.setUpdateBy(getCurrentUsername());
        userService.insert(user);
        //insert token
        userTokenService.insert(new SysUserToken()
                .setUsername(user.getUsername())
                .setCreateBy(getCurrentUsername())
                .setUpdateBy(getCurrentUsername())
                .setCreateTime(new Date()));
        user = userService.selectOne(new EntityWrapper<SysUser>().eq("username", user.getUsername()));
        return Json.succ(oper, user);
    }

    private void checkUsernameForCreate(String username){
        if(Objects.isNull(username)) throw new ArcherBusinessException("username can not be null ");
        SysUser user = userService.selectOne(new EntityWrapper<SysUser>().eq("username", username));
        if(Objects.nonNull(user))
            throw new ArcherBusinessException(String.format("username:[{}] is exist, can not be username )",
                    username));
        if(userService.isAdmin(username))
            throw new ArcherBusinessException(String.format("username:[{}] is superadmin, can not be username ",
                    username));

    }
    private String getCurrentUsername(){
        return getUsername();
    }
}
