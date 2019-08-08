package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.constant.SystemConstant;
import com.mr.archer.entity.ReportConfigRela;
import com.mr.archer.service.ReportConfigRelaService;
import com.mr.archer.utils.KeyUtils;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 调查报告配置节点关联表 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-01
 */
@Slf4j
@RestController
@RequestMapping(SystemConstant.APP_CONTEXT + "/reportconfigrela")
public class ReportConfigRelaController extends BaseController {

  @Autowired
  private ReportConfigRelaService reportConfigRelaService;

  @PermInfo("查询查报告配置节点关联信息列表信息")
  @PostMapping("/list")
  public Json query(@RequestBody String body) {
    String oper = "query ReportConfigRela";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sReportId = json.getString("reportid");
    List<ReportConfigRela> list = reportConfigRelaService.selectListByReportId(sReportId);

    return Json.succ(oper, list);
  }

  @PermInfo("新增查报告配置节点关联信息")
  @PostMapping("/create")
  public Json createData(@RequestBody String body) {
    String oper = "create ReportConfigRela";
    log.info("{}, body: {}", oper, body);
    ReportConfigRela newData = JSON.parseObject(body, ReportConfigRela.class);
    newData.setId(KeyUtils.getKey("RCR"));
    reportConfigRelaService.insert(newData);

    return Json.succ(oper, newData);
  }

  @PermInfo("查询单个查报告配置节点关联信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query ReportConfigRela";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    ReportConfigRela data = reportConfigRelaService.selectById(sId);

    return Json.succ(oper, data);
  }

  @PermInfo("更新查报告配置节点关联信息")
  @PostMapping("/update")
  public Json updateData(@RequestBody String body) {
    String oper = "update ReportConfigRela";
    log.info("{}, body: {}", oper, body);
    ReportConfigRela data = JSON.parseObject(body, ReportConfigRela.class);
    reportConfigRelaService.updateById(data);

    return Json.succ(oper, data);
  }

  @PermInfo("批量更新查报告配置节点关联信息")
  @Transactional
  @PostMapping("/updatelist")
  public Json updateList(@RequestBody String body) {
    String oper = "update batch ReportConfigRela";
    log.info("{}, body: {}", oper, body);
    JSONArray list = (JSONArray) JSONArray.parse(body);
    for (int i = 0; i < list.size(); i++) {
      JSONObject o = list.getJSONObject(i);
      ReportConfigRela data = o.toJavaObject(ReportConfigRela.class);
      reportConfigRelaService.updateById(data);
    }

    return Json.succ(oper);
  }

  @PermInfo("删除查报告配置节点关联信息")
  @DeleteMapping("/delete/{uid}")
  public Json deleteData(@PathVariable String uid) {
    String oper = "delete ReportConfigRela";
    reportConfigRelaService.deleteById(uid);

    return Json.succ(oper);
  }
}

