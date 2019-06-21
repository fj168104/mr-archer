package com.mr.archer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Sets;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.entity.SysPerm;
import com.mr.archer.entity.SysRolePerm;
import com.mr.archer.service.*;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 *
 */
@Slf4j
@PermInfo(value = "系统权限模块")
@RestController
@RequestMapping("/perm")
public class SysPermController extends BaseController {

	@Autowired
	private SysRoleService roleService;
	@Autowired
	private SysPermService permService;
	@Autowired
	private SysUserRoleService userRoleService;
	@Autowired
	private SysRolePermService rolePermService;


	@PostMapping("/sync")
	public Json syncMenuPermission(@RequestBody String body) {
		String oper = "sync menu permission";
		log.info("{}, body: {}", oper, body);

		JSONArray permArray = JSON.parseArray(body);
		Set<String> pagePermValues = Sets.newHashSet();

		insertOrUpdatePerm(permArray, 0, pagePermValues);
		//delete the pageMenu in Db but not exist in page
		deleteUnusedPerm(pagePermValues);

		return Json.succ(oper);
	}

	private void insertOrUpdatePerm(JSONArray permArray, Integer parentId, Set<String> pagePermValues) {
		for (int i = 0; i < permArray.size(); i++) {
			String path = permArray.getJSONObject(i).getString("path");
			String perm = permArray.getJSONObject(i).getString("perm");
			pagePermValues.add(perm);
			String title = permArray.getJSONObject(i).getString("title");
			SysPerm sysPerm = permService.selectOne(new EntityWrapper<SysPerm>().eq("perm_value", perm));
			//权限不存在则插入，存在则更新
			if (Objects.isNull(sysPerm)) {
				SysPerm p = new SysPerm();
				p.setPermValue(perm);
				p.setPermName(title);
				p.setParent(parentId);
				if (permArray.getJSONObject(i).containsKey("children")) p.setLeaf(false);
				p.setCreateBy(getUsername());
				p.setCreateTime(new Date());
				permService.insert(p);

			} else {
				sysPerm.setParent(parentId);
				sysPerm.setPermName(title);
				sysPerm.setUpdateBy(getUsername());
				sysPerm.setUpdateTime(new Date());
				permService.updateById(sysPerm);
			}

			if (permArray.getJSONObject(i).containsKey("children")) {
				SysPerm sp = permService.selectOne(new EntityWrapper<SysPerm>().eq("perm_value", perm));
				insertOrUpdatePerm(permArray.getJSONObject(i).getJSONArray("children"), sp.getId(), pagePermValues);
			}

		}
	}

	private void deleteUnusedPerm(Set<String> permSet) {
		permService.selectList(new EntityWrapper<>()).forEach(sysPerm -> {
			if (!permSet.contains(sysPerm.getPermValue())) {
				permService.deleteById(sysPerm.getId());
				rolePermService.delete(new EntityWrapper<SysRolePerm>().eq("perm_id", sysPerm.getId()));
			}
		});
	}
}
