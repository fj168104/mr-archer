package com.mr.archer.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.entity.SysRole;
import com.mr.archer.service.SysRoleService;
import com.mr.archer.vo.Json;
import com.mr.archer.vo.Option;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
@PermInfo(value = "选项模块", pval = "a:option")
@RestController
@RequestMapping("/option")
public class OptionController extends BaseController{

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/role")
    public Json listRoleOptions() {
        String oper = "list role options";
        log.info(oper);

        EntityWrapper<SysRole> params = new EntityWrapper<>();
        params.setSqlSelect("id,role_name,role_value");

        List<SysRole> list = sysRoleService.selectList(params);
        List<Option> options = list.stream().map(obj -> new Option(obj.getId(), obj.getRoleName(),obj.getRoleValue())).collect(Collectors.toList());
        return Json.succ(oper, "options", options);
    }


}
