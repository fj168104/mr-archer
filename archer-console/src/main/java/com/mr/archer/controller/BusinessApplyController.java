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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 业务申请 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-27
 */
@Slf4j
@RestController
@RequestMapping("/businessapply")
public class BusinessApplyController extends BaseController {

  @Autowired
  private BusinessApplyService businessApplyService;
  @Autowired
  private BusinessReportDataService businessReportDataService;
  @Autowired
  private BusinessTypeService businessTypeService;
  @Autowired
  private ReportConfigRelaService reportConfigRelaService;

  @PermInfo("查询用户下的贷款申请列表")
  @PostMapping("/user/list")
  public Json query(@RequestBody String body) {
    String oper = "query user list BusinessApply";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());

    JSONObject filterJson = json.getJSONObject("filters");
    String sCustomerName = filterJson.getString("customername");
    Page<BusinessApply> page = businessApplyService.selectBusinessApplyListByUser(PageUtils.getPageParam(json), sCurUserId, sCustomerName);

    return Json.succ(oper).data("data", page);
  }

  @PermInfo("新增贷款申请信息")
  @Transactional
  @PostMapping("/create")
  public Json createData(@RequestBody String body) {
    String oper = "create BusinessApply";
    log.info("{}, body: {}", oper, body);
    BusinessApply newData = JSON.parseObject(body, BusinessApply.class);
    String sApplyId = KeyUtils.getKey("BA");
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();

    // 通过业务品种初始化贷款申请信息
    String sBusinessType = newData.getBusinesstype();
    BusinessType bt = businessTypeService.selectById(sBusinessType);
    // 基础年利率
    BigDecimal dRate = bt.getRate();
    newData.setBaserate(dRate);
    newData.setRate(dRate);
    newData.setMonthrate(dRate.divide(new BigDecimal(12),6, BigDecimal.ROUND_HALF_UP));

    //调查报告
    String sReportConfigId = bt.getReportid();
    List<ReportConfigRela> nodelist = reportConfigRelaService.selectListByReportId(sReportConfigId);
    for (ReportConfigRela reportConfigRela : nodelist) {
      BusinessReportData businessReportData = new BusinessReportData();
      businessReportData.setId(KeyUtils.getKey("BRD"));
      businessReportData.setApplyid(sApplyId);
      businessReportData.setReportid(reportConfigRela.getReportid());
      businessReportData.setNodeid(reportConfigRela.getNodeid());
      businessReportData.setTitle(reportConfigRela.getTitle());
      businessReportData.setVuepath(reportConfigRela.getVuepath());
      businessReportData.setSortno(reportConfigRela.getSortno());
      businessReportData.setNodedesc(reportConfigRela.getNodedesc());
      businessReportDataService.insert(businessReportData);
    }

    newData.setId(sApplyId);
    // 发生类型：01-新发生
    newData.setOccurtype("01");
    // 币种：CNY-人民币
    newData.setCurrency("CNY");
    newData.setCreateuser(sCurUserId);
    newData.setCreatetime(sCurTime);
    newData.setCreateorg(sCurUserOrg);
    newData.setUpdateuser(sCurUserId);
    newData.setUpdatetime(sCurTime);
    newData.setUpdateorg(sCurUserOrg);
    businessApplyService.insert(newData);

    return Json.succ(oper);
  }

  @PermInfo("查询单个贷款申请信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query BusinessApply";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    BusinessApply data = businessApplyService.selectBusinessApplyById(sId);

    return Json.succ(oper, data);
  }

  @PermInfo("更新贷款申请信息")
  @PostMapping("/update")
  public Json updateData(@RequestBody String body) {
    String oper = "update BusinessApply";
    log.info("{}, body: {}", oper, body);
    BusinessApply data = JSON.parseObject(body, BusinessApply.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    data.setUpdateuser(sCurUserId);
    data.setUpdatetime(sCurTime);
    data.setUpdateorg(sCurUserOrg);
    businessApplyService.updateById(data);

    return Json.succ(oper, data);
  }

  @PermInfo("删除贷款申请信息")
  @Transactional
  @DeleteMapping("/delete/{uid}")
  public Json deleteData(@PathVariable String uid) {
    String oper = "delete BusinessApply";
    businessApplyService.deleteById(uid);

    // 删除调查报告信息
    Wrapper<BusinessReportData> brdParams = new EntityWrapper<>();
    brdParams.eq("applyid", uid);
    businessReportDataService.delete(brdParams);

    return Json.succ(oper);
  }
}

