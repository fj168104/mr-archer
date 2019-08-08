package com.mr.archer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.constant.SystemConstant;
import com.mr.archer.entity.FinConfig;
import com.mr.archer.entity.FinModelConfigRef;
import com.mr.archer.entity.SysUser;
import com.mr.archer.service.FinModelConfigRefService;
import com.mr.archer.utils.DateUtils;
import com.mr.archer.utils.KeyUtils;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 财报模板配置关联表 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-21
 */
@Slf4j
@RestController
@RequestMapping(SystemConstant.APP_CONTEXT + "/finmodelconfigref")
public class FinModelConfigRefController extends BaseController {

  @Autowired
  private FinModelConfigRefService finModelConfigRefService;

  @PermInfo("查询财报模板配置关联信息列表信息")
  @PostMapping("/list")
  public Json query(@RequestBody String body) {
    String oper = "query FinModelConfigRef";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sModelId = json.getString("modelid");
    List<FinModelConfigRef> list = finModelConfigRefService.selectListByModelId(sModelId);

    return Json.succ(oper, list);
  }

  @PermInfo("批量更新财报模板配置关联信息列表")
  @Transactional
  @PostMapping("/updatelist")
  public Json updateList(@RequestBody String body) {
    String oper = "updatelist FinModelConfigRef";
    log.info("{}, body: {}", oper, body);
    JSONArray json = JSON.parseArray(body);
    for (int i = 0; i < json.size(); i++) {
      JSONObject datainfo = json.getJSONObject(i);
      String sId = datainfo.getString("id");
      String sSortNo = datainfo.getString("sortno");
      finModelConfigRefService.updateSortNoById(sId, sSortNo);
    }

    return Json.succ(oper);
  }

  @PermInfo("新增财报模板配置关联信息")
  @PostMapping("/create")
  public Json createData(@RequestBody String body) {
    String oper = "create FinModelConfigRef";
    log.info("{}, body: {}", oper, body);
    FinModelConfigRef newData = JSON.parseObject(body, FinModelConfigRef.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    newData.setId(KeyUtils.getKey("FMCR"));
    newData.setCreateuser(sCurUserId);
    newData.setCreatetime(sCurTime);
    newData.setCreateorg(sCurUserOrg);
    finModelConfigRefService.insert(newData);

    return Json.succ(oper, newData);
  }

  @PermInfo("查询单个财报模板配置关联信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query FinModelConfigRef";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    FinModelConfigRef data = finModelConfigRefService.selectById(sId);

    return Json.succ(oper, data);
  }

  @PermInfo("更新财报模板配置关联信息")
  @PostMapping("/update")
  public Json updateData(@RequestBody String body) {
    String oper = "update FinModelConfigRef";
    log.info("{}, body: {}", oper, body);
    FinModelConfigRef data = JSON.parseObject(body, FinModelConfigRef.class);
    SysUser curUser = getCurUser();
    finModelConfigRefService.updateById(data);

    return Json.succ(oper, data);
  }

  @PermInfo("删除财报模板配置关联信息")
  @DeleteMapping("/delete/{uid}")
  public Json deleteData(@PathVariable String uid) {
    String oper = "delete FinModelConfigRef";
    finModelConfigRefService.deleteById(uid);

    return Json.succ(oper);
  }
}

