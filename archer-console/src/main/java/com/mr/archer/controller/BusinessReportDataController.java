package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.constant.SystemConstant;
import com.mr.archer.entity.BusinessReportData;
import com.mr.archer.entity.SysUser;
import com.mr.archer.service.BusinessReportDataService;
import com.mr.archer.utils.DateUtils;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 授信申请调查报告数据 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-01
 */
@Slf4j
@RestController
@RequestMapping(SystemConstant.APP_CONTEXT + "/businessreportdata")
public class BusinessReportDataController extends BaseController {

  @Autowired
  private BusinessReportDataService businessReportDataService;

  @PermInfo("查询授信申请的调查报告列表信息")
  @PostMapping("/list")
  public Json query(@RequestBody String body) {
    String oper = "query BusinessReportData";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sApplyId = json.getString("applyid");

    Wrapper<BusinessReportData> brdParams = new EntityWrapper<>();
    brdParams.eq("applyid", sApplyId);
    brdParams.orderBy("sortno");
    List<BusinessReportData> list = businessReportDataService.selectList(brdParams);

    return Json.succ(oper, list);
  }

  @PermInfo("查询单个调查报告信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query BusinessReportData";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    BusinessReportData data = businessReportDataService.selectById(sId);
    byte[] nodedata = data.getNodedata();
    try {
      data.setNodedatatext(new String(nodedata, "utf-8"));
    } catch(Exception e) {
      e.printStackTrace();
    }

    return Json.succ(oper, data);
  }

  @PermInfo("更新单个调查报告信息")
  @PostMapping("/update")
  public Json update(@RequestBody String body) {
    String oper = "update BusinessReportData";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    BusinessReportData data = JSON.toJavaObject(json, BusinessReportData.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    data.setUpdateuser(sCurUserId);
    data.setUpdatetime(sCurTime);
    data.setUpdateorg(sCurUserOrg);
    businessReportDataService.updateById(data);

    return Json.succ(oper);
  }

  @PermInfo("更新调查报告节点数据")
  @PostMapping("/updatenodedata")
  public Json updateNodeData(@RequestBody String body) {
    String oper = "updatenodedata BusinessReportData";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    JSONObject nodeData = json.getJSONObject("nodedata");

    BusinessReportData data = businessReportDataService.selectById(sId);
    data.setNodedata(nodeData.toString().getBytes());
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    data.setUpdateuser(sCurUserId);
    data.setUpdatetime(sCurTime);
    data.setUpdateorg(sCurUserOrg);
    businessReportDataService.updateById(data);

    return Json.succ(oper);
  }
}

