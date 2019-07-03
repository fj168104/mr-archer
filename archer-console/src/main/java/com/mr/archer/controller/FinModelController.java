package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.entity.FinModel;
import com.mr.archer.entity.FinModelConfigRef;
import com.mr.archer.entity.SysUser;
import com.mr.archer.service.FinModelConfigRefService;
import com.mr.archer.service.FinModelService;
import com.mr.archer.utils.DateUtils;
import com.mr.archer.utils.KeyUtils;
import com.mr.archer.utils.PageUtils;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 财报模板表 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-21
 */
@Slf4j
@RestController
@RequestMapping("/finmodel")
public class FinModelController extends BaseController {

  @Autowired
  private FinModelService finModelService;
  @Autowired
  private FinModelConfigRefService finModelConfigRefService;

  @PermInfo("查询财报模板列表信息")
  @PostMapping("/list")
  public Json query(@RequestBody String body) {
    String oper = "query FinModel";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    Wrapper<FinModel> queryParams = new EntityWrapper<>();
    JSONObject filterJson = json.getJSONObject("filters");
    String sName = filterJson.getString("name");
    if (StringUtils.isNotBlank(sName)) {
      queryParams.like("name", sName);
    }

    Page<FinModel> page = finModelService.selectPage(PageUtils.getPageParam(json), queryParams);

    return Json.succ(oper).data("data", page);
  }

  @PermInfo("查询所有财报模板列表信息")
  @PostMapping("/codelist")
  public Json queryCodeList(@RequestBody String body) {
    String oper = "query codelist FinModel";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);
    JSONArray resultJson = new JSONArray();

    Wrapper<FinModel> queryParams = new EntityWrapper<>();
    queryParams.isNotNull("");
    List<FinModel> list = finModelService.selectList(queryParams);
    for (FinModel model : list) {
      JSONObject curModelJson = new JSONObject();
      curModelJson.put("label", model.getName());
      curModelJson.put("value", model.getId());
      resultJson.add(curModelJson);
    }

    return Json.succ(oper, resultJson);
  }

  @PermInfo("新增财报模板信息")
  @PostMapping("/create")
  public Json createData(@RequestBody String body) {
    String oper = "create FinModel";
    log.info("{}, body: {}", oper, body);
    FinModel newData = JSON.parseObject(body, FinModel.class);
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
    finModelService.insert(newData);

    return Json.succ(oper, newData);
  }

  @PermInfo("查询单个财报模板信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query FinModel";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    FinModel data = finModelService.selectById(sId);

    return Json.succ(oper, data);
  }

  @PermInfo("更新财报模板信息")
  @PostMapping("/update")
  public Json updateData(@RequestBody String body) {
    String oper = "update FinModel";
    log.info("{}, body: {}", oper, body);
    FinModel data = JSON.parseObject(body, FinModel.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    data.setUpdateuser(sCurUserId);
    data.setUpdatetime(sCurTime);
    data.setUpdateorg(sCurUserOrg);
    finModelService.updateById(data);

    return Json.succ(oper, data);
  }

  @PermInfo("删除财报模板信息")
  @Transactional
  @DeleteMapping("/delete/{uid}")
  public Json deleteData(@PathVariable String uid) {
    String oper = "delete FinModel";
    finModelService.deleteById(uid);

    // 删除财报模板关联表信息
    Wrapper<FinModelConfigRef> queryParams = new EntityWrapper<>();
    queryParams.eq("modelid", uid);
    finModelConfigRefService.delete(queryParams);

    return Json.succ(oper);
  }
}

