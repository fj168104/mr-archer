package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.entity.ReportConfig;
import com.mr.archer.entity.ReportConfigRela;
import com.mr.archer.entity.SysUser;
import com.mr.archer.service.ReportConfigRelaService;
import com.mr.archer.service.ReportConfigService;
import com.mr.archer.utils.DateUtils;
import com.mr.archer.utils.KeyUtils;
import com.mr.archer.utils.PageUtils;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 调查报告配置表 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-01
 */
@Slf4j
@RestController
@RequestMapping("/reportconfig")
public class ReportConfigController extends BaseController {

  @Autowired
  private ReportConfigService reportConfigService;
  @Autowired
  private ReportConfigRelaService reportConfigRelaService;

  @PermInfo("查询调查报告配置信息列表信息")
  @PostMapping("/list")
  public Json query(@RequestBody String body) {
    String oper = "query ReportConfig";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    Wrapper<ReportConfig> queryParams = new EntityWrapper<>();
    JSONObject filterJson = json.getJSONObject("filters");
    String sName = filterJson.getString("name");
    if (StringUtils.isNotBlank(sName)) {
      queryParams.like("name", sName);
    }

    Page<ReportConfig> page = reportConfigService.selectPage(PageUtils.getPageParam(json), queryParams);

    return Json.succ(oper).data("data", page);
  }

  @PermInfo("新增调查报告配置信息")
  @PostMapping("/create")
  public Json createData(@RequestBody String body) {
    String oper = "create ReportConfig";
    log.info("{}, body: {}", oper, body);
    ReportConfig newData = JSON.parseObject(body, ReportConfig.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    newData.setId(KeyUtils.getKey());
    newData.setCreateuser(sCurUserId);
    newData.setCreatetime(sCurTime);
    newData.setCreateorg(sCurUserOrg);
    newData.setUpdateuser(sCurUserId);
    newData.setUpdatetime(sCurTime);
    newData.setUpdateorg(sCurUserOrg);
    reportConfigService.insert(newData);

    return Json.succ(oper, newData);
  }

  @PermInfo("查询单个调查报告配置信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query ReportConfig";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    ReportConfig data = reportConfigService.selectById(sId);

    return Json.succ(oper, data);
  }

  @PermInfo("更新调查报告配置信息")
  @PostMapping("/update")
  public Json updateData(@RequestBody String body) {
    String oper = "update ReportConfig";
    log.info("{}, body: {}", oper, body);
    ReportConfig data = JSON.parseObject(body, ReportConfig.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    data.setUpdateuser(sCurUserId);
    data.setUpdatetime(sCurTime);
    data.setUpdateorg(sCurUserOrg);
    reportConfigService.updateById(data);

    return Json.succ(oper, data);
  }

  @PermInfo("删除调查报告配置信息")
  @Transactional
  @DeleteMapping("/delete/{uid}")
  public Json deleteData(@PathVariable String uid) {
    String oper = "delete ReportConfig";
    reportConfigService.deleteById(uid);

    // 删除调查报告关联表信息
    Wrapper<ReportConfigRela> queryParams = new EntityWrapper<>();
    queryParams.eq("reportid", uid);
    reportConfigRelaService.delete(queryParams);

    return Json.succ(oper);
  }
}

