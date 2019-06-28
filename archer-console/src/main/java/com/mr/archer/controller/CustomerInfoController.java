package com.mr.archer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.entity.*;
import com.mr.archer.service.*;
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
@RequestMapping("/cust")
public class CustomerInfoController extends BaseController{

    @Autowired
    private SysUserService userService;
    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private EntInfoService entInfoService;
    @Autowired
    private EntManagerService entManagerService;
    @Autowired
    private EntMemberService entMemberService;
    @Autowired
    private EntStockService entStockService;
    @Autowired
    private EntInvestService entInvestService;
    @Autowired
    private EntRelateService entRelateService;
    @Autowired
    private CustomerLegalService customerLegalService;
    @Autowired
    private CustomerBelongService customerBelongService;

    @PermInfo("查询所有小微客户信息")
    @PostMapping("/xw/list")
    public Json query(@RequestBody String body) {
        String oper = "query cust xw";
        log.info("{}, body: {}", oper, body);
        JSONObject json = JSON.parseObject(body);

        SysUser curUser = getCurUser();
        String sCurUserId = String.valueOf(curUser.getId());
        JSONObject filterJson = json.getJSONObject("filters");
        String sName = filterJson.getString("name");
        String sCertId = filterJson.getString("certid");

        Page<CustomerInfo> page = customerInfoService.selectCustomerListByUser(PageUtils.getPageParam(json), sCurUserId, sName, sCertId);
        Json result = Json.succ(oper).data("data", page);

        return result;
    }

    @PermInfo("新增小微客户信息")
    @Transactional
    @PostMapping("/xw/create")
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
        customerInfoService.insert(cust);

        // 新增客户基本信息
        EntInfo entInfo = new EntInfo();
        entInfo.setId(sCustomerId);
        // 企业规模：03-小微客户
        entInfo.setScale("03");
        entInfo.setCreateuser(sCurUserId);
        entInfo.setCreatetime(sCurTime);
        entInfo.setCreateorg(sCurUserOrg);
        entInfo.setUpdateuser(sCurUserId);
        entInfo.setUpdatetime(sCurTime);
        entInfo.setUpdateorg(sCurUserOrg);
        entInfoService.insert(entInfo);

        // 新增客户所属信息
        CustomerBelong custBelong = new CustomerBelong();
        custBelong.setId(KeyUtils.getKey("CB"));
        custBelong.setCustomerid(sCustomerId);
        custBelong.setUserid(sCurUserId);
        custBelong.setMainflag("1");
        custBelong.setModifyflag("1");
        custBelong.setViewflag("1");
        custBelong.setApplyflag("1");
        custBelong.setCreateuser(sCurUserId);
        custBelong.setCreatetime(sCurTime);
        custBelong.setCreateorg(sCurUserOrg);
        custBelong.setUpdateuser(sCurUserId);
        custBelong.setUpdatetime(sCurTime);
        custBelong.setUpdateorg(sCurUserOrg);
        customerBelongService.insert(custBelong);

        return Json.succ(oper, cust);
    }

    @PermInfo("删除小微客户信息")
    @Transactional
    @DeleteMapping("/xw/delete/{uid}")
    public Json deleteData(@PathVariable String uid) {
        String oper = "delete cust xw";
        customerInfoService.deleteById(uid);
        // 删除关联的客户基本信息
        entInfoService.deleteById(uid);
        // 删除关联的客户高管信息
        Wrapper<EntManager> emParams = new EntityWrapper<>();
        emParams.eq("customerid", uid);
        entManagerService.delete(emParams);
        // 删除关联的法人家族成员信息
        Wrapper<EntMember> embParams = new EntityWrapper<>();
        embParams.eq("customerid", uid);
        entMemberService.delete(embParams);
        // 删除关联的客户股东信息
        Wrapper<EntStock> esParams = new EntityWrapper<>();
        esParams.eq("customerid", uid);
        entStockService.delete(esParams);
        // 删除关联的对外股权投资信息
        Wrapper<EntInvest> eiParams = new EntityWrapper<>();
        eiParams.eq("customerid", uid);
        entInvestService.delete(eiParams);
        // 删除关联的上下游关联方信息
        Wrapper<EntRelate> erParams = new EntityWrapper<>();
        erParams.eq("customerid", uid);
        entRelateService.delete(erParams);
        // 删除关联的客户涉诉信息
        Wrapper<CustomerLegal> clParams = new EntityWrapper<>();
        clParams.eq("customerid", uid);
        customerLegalService.delete(clParams);
        // 删除关联的客户权限信息
        Wrapper<CustomerBelong> cbParams = new EntityWrapper<>();
        esParams.eq("customerid", uid);
        customerBelongService.delete(cbParams);

        return Json.succ(oper);
    }
}
