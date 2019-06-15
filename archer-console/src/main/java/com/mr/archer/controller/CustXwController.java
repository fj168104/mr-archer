package com.mr.archer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.entity.CustomerInfo;
import com.mr.archer.entity.EntInfo;
import com.mr.archer.entity.EntStock;
import com.mr.archer.entity.SysUser;
import com.mr.archer.service.CustXwService;
import com.mr.archer.service.EntInfoService;
import com.mr.archer.service.EntStockService;
import com.mr.archer.service.SysUserService;
import com.mr.archer.utils.DateUtils;
import com.mr.archer.utils.KeyUtils;
import com.mr.archer.utils.PageUtils;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/cust/xw")
public class CustXwController extends BaseController{

    @Autowired
    private SysUserService userService;
    @Autowired
    private CustXwService custXwService;
    @Autowired
    private EntInfoService entInfoService;
    @Autowired
    private EntStockService entStockService;

    @PermInfo("查询所有小微客户信息")
    @PostMapping("/list")
    public Json query(@RequestBody String body) {
        String oper = "query cust xw";
        log.info("{}, body: {}", oper, body);
        JSONObject json = JSON.parseObject(body);
        Wrapper<CustomerInfo> queryParams = new EntityWrapper<>();

        JSONObject filterJson = json.getJSONObject("filters");
        String sName = filterJson.getString("name");
        if (StringUtils.isNotBlank(sName)) {
            queryParams.like("name", sName);
        }

        String sCertId = filterJson.getString("certid");
        if (StringUtils.isNotBlank(sCertId)) {
            if(queryParams.isNotEmptyOfWhere()) queryParams.and();
            queryParams.eq("certid", sCertId);
        }

        Page<CustomerInfo> page = custXwService.selectPage(PageUtils.getPageParam(json), queryParams);
        Json result = Json.succ(oper).data("data", page);

        return result;
    }

    @PermInfo("新增小微客户信息")
    @Transactional
    @PostMapping("/create")
    public Json createData(@RequestBody String body) {
        String oper = "create cust xw";
        log.info("{}, body: {}", oper, body);
        CustomerInfo cust = JSON.parseObject(body, CustomerInfo.class);
        SysUser curUser = getCurUser();
        String sCurUserId = String.valueOf(curUser.getId());
        String sCurUserOrg = curUser.getOrgid();
        String sCurTime = DateUtils.getNowTime();
        String sCustomerId = KeyUtils.getKey("XW");
        cust.setId(sCustomerId);
        cust.setCreateuser(sCurUserId);
        cust.setCreatetime(sCurTime);
        cust.setCreateorg(sCurUserOrg);
        cust.setUpdateuser(sCurUserId);
        cust.setUpdatetime(sCurTime);
        cust.setUpdateorg(sCurUserOrg);
        custXwService.insert(cust);

        // 新增客户基本信息数据
        EntInfo entInfo = new EntInfo();
        entInfo.setId(sCustomerId);
        entInfo.setCreateuser(sCurUserId);
        entInfo.setCreatetime(sCurTime);
        entInfo.setCreateorg(sCurUserOrg);
        entInfo.setUpdateuser(sCurUserId);
        entInfo.setUpdatetime(sCurTime);
        entInfo.setUpdateorg(sCurUserOrg);
        entInfoService.insert(entInfo);

        return Json.succ(oper, cust);
    }

    @PermInfo("删除小微客户信息")
    @Transactional
    @DeleteMapping("/delete/{uid}")
    public Json deleteData(@PathVariable String uid) {
        String oper = "delete cust xw";
        custXwService.deleteById(uid);
        // 删除关联的客户基本信息
        entInfoService.deleteById(uid);
        // 删除关联的客户股东信息
        Wrapper<EntStock> esParams = new EntityWrapper<>();
        esParams.eq("customerid", uid);
        entStockService.delete(esParams);

        return Json.succ(oper);
    }
}
