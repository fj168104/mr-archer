package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.constant.SystemConstant;
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

import org.springframework.stereotype.Controller;
import com.mr.archer.controller.BaseController;

import java.util.List;

/**
 * <p>
 * 财报数据基本信息表 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-25
 */
@Slf4j
@RestController
@RequestMapping(SystemConstant.APP_CONTEXT + "/finbaseinfo")
public class FinBaseInfoController extends BaseController {

  @Autowired
  private SysUserService userService;
  @Autowired
  private FinBaseInfoService finBaseInfoService;
  @Autowired
  private FinModelConfigRefService finModelConfigRefService;
  @Autowired
  private FinBalanceSheetService finBalanceSheetService;
  @Autowired
  private FinProfitService finProfitService;
  @Autowired
  private FinCashFlowService finCashFlowService;

  @PermInfo("查询单个客户的财务报表列表信息")
  @PostMapping("/list")
  public Json query(@RequestBody String body) {
    String oper = "query cust FinBaseInfo";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    Wrapper<FinBaseInfo> queryParams = new EntityWrapper<>();

    JSONObject filterJson = json.getJSONObject("filters");
    String sCustomerId = filterJson.getString("customerid");
    queryParams.eq("customerid", sCustomerId);
    String sFinDate = filterJson.getString("findate");
    if (StringUtils.isNotBlank(sFinDate)) {
      queryParams.eq("findate",sFinDate);
    }
    Page<FinBaseInfo> page = finBaseInfoService.selectPage(PageUtils.getPageParam(json), queryParams);

    return Json.succ(oper).data("data", page);
  }

  @PermInfo("新增客户的财务报表信息")
  @Transactional
  @PostMapping("/create")
  public Json createData(@RequestBody String body) {
    String oper = "create cust FinBaseInfo";
    log.info("{}, body: {}", oper, body);
    FinBaseInfo newData = JSON.parseObject(body, FinBaseInfo.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    String sFinDataId = KeyUtils.getKey("FBI");
    newData.setId(sFinDataId);
    newData.setCreateuser(sCurUserId);
    newData.setCreatetime(sCurTime);
    newData.setCreateorg(sCurUserOrg);
    newData.setUpdateuser(sCurUserId);
    newData.setUpdatetime(sCurTime);
    newData.setUpdateorg(sCurUserOrg);
    finBaseInfoService.insert(newData);

    // 初始化其他关联数据表
    String sModelId = newData.getModelid();
    List<FinModelConfigRef> list = finModelConfigRefService.selectListByModelId(sModelId);
    for (FinModelConfigRef info : list) {
      String sFinDataType = info.getFindatatype();
      String sConfigId = info.getConfigid();
      if ("01".equals(sFinDataType)) {
        // 资产负债表
        FinBalanceSheet sheet = new FinBalanceSheet();
        sheet.setBaseinfoid(sFinDataId);
        sheet.setConfigid(sConfigId);
        sheet.setId(KeyUtils.getKey("FBS"));
        // 期初数据
        sheet.setType("01");
        finBalanceSheetService.insert(sheet);
        sheet.setId(KeyUtils.getKey("FBS"));
        // 期末数据
        sheet.setType("02");
        finBalanceSheetService.insert(sheet);
      } else if ("02".equals(sFinDataType)) {
        // 利润表
        FinProfit profit = new FinProfit();
        profit.setBaseinfoid(sFinDataId);
        profit.setConfigid(sConfigId);
        profit.setId(KeyUtils.getKey("FP"));
        // 期初数据
        profit.setType("01");
        finProfitService.insert(profit);
        profit.setId(KeyUtils.getKey("FP"));
        // 期末数据
        profit.setType("02");
        finProfitService.insert(profit);
      } else if ("03".equals(sFinDataType)) {
        // 现金流量表
        FinCashFlow cashflow = new FinCashFlow();
        cashflow.setBaseinfoid(sFinDataId);
        cashflow.setConfigid(sConfigId);
        cashflow.setId(KeyUtils.getKey("FCF"));
        // 期初数据
        cashflow.setType("01");
        finCashFlowService.insert(cashflow);
        cashflow.setId(KeyUtils.getKey("FCF"));
        // 期末数据
        cashflow.setType("02");
        finCashFlowService.insert(cashflow);
      }
    }

    return Json.succ(oper);
  }

  @PermInfo("查询单个财务报表信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query cust FinBaseInfo";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    FinBaseInfo data = finBaseInfoService.selectById(sId);

    return Json.succ(oper, data);
  }

  @PermInfo("更新客户的财务报表信息")
  @PostMapping("/update")
  public Json updateData(@RequestBody String body) {
    String oper = "update cust FinBaseInfo";
    log.info("{}, body: {}", oper, body);
    FinBaseInfo data = JSON.parseObject(body, FinBaseInfo.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    data.setUpdateuser(sCurUserId);
    data.setUpdatetime(sCurTime);
    data.setUpdateorg(sCurUserOrg);
    finBaseInfoService.updateById(data);

    return Json.succ(oper, data);
  }

  @PermInfo("删除客户的财务报表信息")
  @DeleteMapping("/delete/{uid}")
  public Json deleteData(@PathVariable String uid) {
    String oper = "delete cust FinBaseInfo";
    finBaseInfoService.deleteById(uid);

    return Json.succ(oper);
  }
}

