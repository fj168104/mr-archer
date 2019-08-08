package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.constant.SystemConstant;
import com.mr.archer.entity.FinConfigCol;
import com.mr.archer.entity.SysUser;
import com.mr.archer.service.FinConfigColService;
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
 * 财报字段配置表 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-20
 */
@Slf4j
@RestController
@RequestMapping(SystemConstant.APP_CONTEXT + "/finconfigcol")
public class FinConfigColController extends BaseController {

  @Autowired
  private FinConfigColService finConfigColService;

  @PermInfo("查询财报配置字段信息列表")
  @PostMapping("/list")
  public Json query(@RequestBody String body) {
    String oper = "query FinConfigCol";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);
    JSONArray aResultJson = new JSONArray();

    String sConfigId = json.getString("configid");
    int iCols = json.getIntValue("cols");
    for (int i = 0; i < iCols; i++) {
      Wrapper<FinConfigCol> queryParams = new EntityWrapper<>();
      queryParams.eq("configid", sConfigId);
      queryParams.eq("col", i);
      queryParams.orderBy("sortno");
      List<FinConfigCol> list = finConfigColService.selectList(queryParams);
      JSONArray aList;
      if (list != null) {
        aList = (JSONArray) JSON.toJSON(list);
      } else {
        aList = new JSONArray();
      }
      aResultJson.add(aList);
    }

    return Json.succ(oper, aResultJson);
  }

  @PermInfo("批量处理财报配置字段信息")
  @Transactional
  @PostMapping("/updatelist")
  public Json updateList(@RequestBody String body) {
    String oper = "updatelist FinConfigCol";
    log.info("{}, body: {}", oper, body);
    // JSONObject json = JSON.parseObject(body);
    JSONArray aColsJson = JSON.parseArray(body);

    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    for (int i = 0; i < aColsJson.size(); i++) {
      JSONArray aColJson = (JSONArray)aColsJson.get(i);
      for (int j = 0; j < aColJson.size(); j++) {
        JSONObject oCol = aColJson.getJSONObject(j);
        String sId = oCol.getString("id");
        if (sId == null || "".equals(sId)) {
          FinConfigCol newData = JSON.toJavaObject(oCol, FinConfigCol.class);
          String sNewId = KeyUtils.getKey("FCC");
          newData.setId(sNewId);
          newData.setCreateuser(sCurUserId);
          newData.setCreatetime(sCurTime);
          newData.setCreateorg(sCurUserOrg);
          newData.setUpdateuser(sCurUserId);
          newData.setUpdatetime(sCurTime);
          newData.setUpdateorg(sCurUserOrg);
          oCol.put("id", sNewId);
          oCol.put("createuser", sCurUserId);
          oCol.put("createtime", sCurTime);
          oCol.put("createorg", sCurUserOrg);
          finConfigColService.insert(newData);
        } else {
          FinConfigCol data = JSON.toJavaObject(oCol, FinConfigCol.class);
          data.setUpdateuser(sCurUserId);
          data.setUpdatetime(sCurTime);
          data.setUpdateorg(sCurUserOrg);
          finConfigColService.updateById(data);
        }
        oCol.put("updateuser", sCurUserId);
        oCol.put("updatetime", sCurTime);
        oCol.put("updateorg", sCurUserOrg);
      }
    }

    return Json.succ(oper, aColsJson);
  }

  @PermInfo("新增财报配置字段信息")
  @PostMapping("/create")
  public Json createData(@RequestBody String body) {
    String oper = "create FinConfigCol";
    log.info("{}, body: {}", oper, body);
    FinConfigCol newData = JSON.parseObject(body, FinConfigCol.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    newData.setId(KeyUtils.getKey("FCC"));
    newData.setCreateuser(sCurUserId);
    newData.setCreatetime(sCurTime);
    newData.setCreateorg(sCurUserOrg);
    newData.setUpdateuser(sCurUserId);
    newData.setUpdatetime(sCurTime);
    newData.setUpdateorg(sCurUserOrg);
    finConfigColService.insert(newData);

    return Json.succ(oper, newData);
  }

  @PermInfo("查询单个财报配置字段信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query FinConfigCol";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    FinConfigCol data = finConfigColService.selectById(sId);

    return Json.succ(oper, data);
  }

  @PermInfo("更新财报配置字段信息")
  @PostMapping("/update")
  public Json updateData(@RequestBody String body) {
    String oper = "update FinConfigCol";
    log.info("{}, body: {}", oper, body);
    FinConfigCol data = JSON.parseObject(body, FinConfigCol.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    data.setUpdateuser(sCurUserId);
    data.setUpdatetime(sCurTime);
    data.setUpdateorg(sCurUserOrg);
    finConfigColService.updateById(data);

    return Json.succ(oper, data);
  }

  @PermInfo("删除财报配置字段信息")
  @DeleteMapping("/delete/{uid}")
  public Json deleteData(@PathVariable String uid) {
    String oper = "delete FinConfigCol";
    finConfigColService.deleteById(uid);

    return Json.succ(oper);
  }
}

