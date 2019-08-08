package com.mr.archer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Sets;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.constant.SystemConstant;
import com.mr.archer.entity.SysPerm;
import com.mr.archer.entity.SysRole;
import com.mr.archer.entity.SysRolePerm;
import com.mr.archer.entity.SysUserRole;
import com.mr.archer.exception.ArcherBusinessException;
import com.mr.archer.service.*;
import com.mr.archer.utils.PageUtils;
import com.mr.archer.vo.AuthVo;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


/**
 *
 */
@Slf4j
@PermInfo(value = "系统角色模块")
@RestController
@RequestMapping(SystemConstant.APP_CONTEXT)
public class SysRoleController extends BaseController {

	@Autowired
	private SysRoleService roleService;
	@Autowired
	private SysPermService permService;
	@Autowired
	private SysUserRoleService userRoleService;
	@Autowired
	private SysRolePermService rolePermService;

	@PostMapping("/role/list")
	public Json query(@RequestBody String body) {

		String oper = "query all roles";
		log.info("{}, rid: {}", oper, body);

		JSONObject json = JSON.parseObject(body);

		Wrapper<SysRole> queryParams = new EntityWrapper<>();
		String roleName = json.getString("roleName");
		if (StringUtils.isNotBlank(roleName)) {
			queryParams.like("role_name", roleName);
		}

		String roleValue = json.getString("roleValue");
		if (StringUtils.isNotBlank(roleValue)) {
			if(queryParams.isNotEmptyOfWhere()) queryParams.and();
			queryParams.like("role_value", roleValue);
		}

		Page<SysRole> page = roleService.selectPage(PageUtils.getPageParam(json), queryParams);
		page.getRecords().forEach(role -> {
			role.setPerms(permService.getPermsByRoleId(role.getId())
					.stream().map(r -> new AuthVo(r.getPermName(), r.getPermValue())).collect(Collectors.toSet())
			);
		});
//        for(SysRole role : page.getRecords()){
//            Set<SysPerm> perms = permService.getPermsByRoleId(role.getId());
//            Set<AuthVo> rolePerms = perms.stream().map(r->new AuthVo(r.getPermName(),r.getPermValue())).collect(Collectors.toSet());
//            role.setPerms(rolePerms);
//        }
		return Json.succ(oper, page);
	}

	@PostMapping("/role/create")
	public Json add(@RequestBody String body) {

		String oper = "add create";
		JSONObject roleObject = JSON.parseObject(body);

		SysRole role = new SysRole();
		role.setRoleName(roleObject.getString("roleName"));
		role.setRoleValue(roleObject.getString("roleValue"));
		role.setRoleDesc(roleObject.getString("roleDesc"));
		role.setCreateTime(new Date());
		role.setCreateBy(getUsername());
		role.setUpdateBy(getUsername());
		role.setUpdateTime(new Date());

		if (StringUtils.isBlank(role.getRoleValue())) {
			return Json.fail(oper, "角色值不能为空");
		}

		SysRole roleDB = roleService.selectOne(new EntityWrapper<SysRole>().eq("role_value", role.getRoleValue()));
		if (roleDB != null) {
			return Json.fail(oper, String.format("角色值[{}]已存在：", role.getRoleValue()));
		}

		//保存新用户数据
		roleService.insert(role);

		role = roleService.selectOne(new EntityWrapper<SysRole>().eq("role_value", role.getRoleValue()));
		//update role perm
		String result = updateRolePerm(roleObject.getJSONArray("routes"), role, Sets.newHashSet());

		if (StringUtils.isBlank(result)) {
			return Json.succ(oper, role);
		} else {
			return Json.fail(oper, result);
		}

	}

	@DeleteMapping("/role/delete/{rid}")
	public Json delete(@PathVariable Integer rid) {

		String oper = "delete role";

		roleService.deleteById(rid);
		rolePermService.delete(new EntityWrapper<SysRolePerm>().eq("role_id", rid));
		userRoleService.delete(new EntityWrapper<SysUserRole>().eq("role_id", rid));

		return Json.succ(oper);
	}

	@GetMapping("/roles")
	public Json query() {

		String oper = "query all roles";
		log.info("{}", oper);

		List<SysRole> roles = roleService.selectList(new EntityWrapper<>());
		roles.forEach(role -> {
			role.setPerms(permService.getPermsByRoleId(role.getId())
					.stream().map(r -> new AuthVo(r.getPermName(), r.getPermValue())).collect(Collectors.toSet())
			);
		});

		return Json.succ(oper, roles);
	}

	@PutMapping("/role/update")
	public Json update(@RequestBody String body) {

		String oper = "update role";
		log.info("{}, body: {}", oper, body);

		JSONObject roleObject = JSON.parseObject(body);
		if(Objects.isNull(roleObject.get("id"))) throw new ArcherBusinessException("Id is not exists");
		SysRole role = roleService.selectById(roleObject.getString("id"));
		if(Objects.isNull(role)) throw new ArcherBusinessException(String.format("The role[%s] is not exists", roleObject.get("id")));
		role.setRoleName(roleObject.getString("roleName"));
		role.setRoleValue(roleObject.getString("roleValue"));
		role.setRoleDesc(roleObject.getString("roleDesc"));
		role.setUpdateBy(getUsername());
		role.setUpdateTime(new Date());
		roleService.updateById(role);

		Set<Integer> rolePermValues = Sets.newHashSet();
		//update role perm
		String result = updateRolePerm(roleObject.getJSONArray("routes"), role, rolePermValues);
		//delete role perm in Db but not exist in body
		deleteNoExistRolePerm(rolePermValues, role.getId());

		if (StringUtils.isBlank(result)) {
			return Json.succ(oper);
		} else {
			return Json.fail(oper, result);
		}
	}


	private String updateRolePerm(JSONArray routeArray, SysRole role, Set<Integer> rolePermValues) {
		for (int i = 0; i < routeArray.size(); i++) {
			JSONObject metaOjb = routeArray.getJSONObject(i).getJSONObject("meta");
			if(Objects.isNull(metaOjb) || Objects.isNull(metaOjb.get("perm"))) return null;
			SysPerm sysPerm = permService.selectOne(new EntityWrapper<SysPerm>().eq("perm_value", metaOjb.getString("perm")));
			if (Objects.isNull(sysPerm)) {
				return String.format("perm:[%s} is not exists", metaOjb.get("perm"));
			}

			rolePermValues.add(sysPerm.getId());
			SysRolePerm rp = rolePermService.selectOne(new EntityWrapper<SysRolePerm>()
					.eq("role_id", role.getId())
					.eq("perm_id", sysPerm.getId())
			);

			if (Objects.isNull(rp)) {
				//新插入一条角色的权限
				SysRolePerm rolePerm = new SysRolePerm();
				rolePerm.setRoleId(role.getId());
				rolePerm.setPermId(sysPerm.getId());
				rolePerm.setCreateTime(new Date());
				rolePerm.setCreateBy(getUsername());
				boolean success = rolePermService.insert(rolePerm);
				if (!success) return "DB operate fail";
			}
			//update children
			if (routeArray.getJSONObject(i).containsKey("children")) {
				String result = updateRolePerm(routeArray.getJSONObject(i).getJSONArray("children"), role, rolePermValues);
				if (StringUtils.isNotBlank(result)) return result;
			}
		}
		return null;
	}

	private void deleteNoExistRolePerm(Set<Integer> rolePermSet, Integer roleId) {
		rolePermService.selectList(new EntityWrapper<SysRolePerm>().eq("role_id", roleId)).forEach(rolePerm -> {
			if (!rolePermSet.contains(rolePerm.getPermId())) {
				rolePermService.deleteById(rolePerm.getId());
			}
		});
	}
}
