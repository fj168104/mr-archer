package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.entity.BusinessType;
import com.mr.archer.entity.SysUser;
import com.mr.archer.service.BusinessTypeService;
import com.mr.archer.utils.DateUtils;
import com.mr.archer.utils.KeyUtils;
import com.mr.archer.utils.PageUtils;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 业务品种 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-27
 */
@Slf4j
@RestController
@RequestMapping("/businesstype")
public class BusinessTypeController extends BaseController {

  @Autowired
  private BusinessTypeService businessTypeService;

  @PermInfo("查询业务品种列表信息")
  @PostMapping("/list")
  public Json query(@RequestBody String body) {
    String oper = "query BusinessType";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    Wrapper<BusinessType> queryParams = new EntityWrapper<>();
    JSONObject filterJson = json.getJSONObject("filters");
    String sType = filterJson.getString("type");
    if (StringUtils.isNotBlank(sType)) {
      queryParams.like("type", sType, SqlLike.RIGHT);
    }
    String sName = filterJson.getString("name");
    if (StringUtils.isNotBlank(sName)) {
      queryParams.like("name", sName);
    }

    Page<BusinessType> page = businessTypeService.selectPage(PageUtils.getPageParam(json), queryParams);

    return Json.succ(oper).data("data", page);
  }

  @PermInfo("查询业务品种代码列表信息")
  @PostMapping("/codelist")
  public Json queryCodeList(@RequestBody String body) {
    String oper = "query codelist BusinessType";
    log.info("{}, body: {}", oper, body);

    Wrapper<BusinessType> queryParams = new EntityWrapper<>();
    queryParams.orderBy("type");
    List<BusinessType> list = businessTypeService.selectList(queryParams);
    JSONArray codeList = new JSONArray();
    if (list != null && list.size() > 0) {
      for (int i = 0; i < list.size(); i++) {
        BusinessType bt = list.get(i);
        JSONObject code = new JSONObject();
        code.put("label", bt.getName());
        code.put("value", bt.getId());
        codeList.add(code);
      }
    }

    return Json.succ(oper, codeList);
  }

  @PermInfo("新增业务品种信息")
  @PostMapping("/create")
  public Json createData(@RequestBody String body) {
    String oper = "create BusinessType";
    log.info("{}, body: {}", oper, body);
    BusinessType newData = JSON.parseObject(body, BusinessType.class);
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
    businessTypeService.insert(newData);

    return Json.succ(oper, newData);
  }

  @PermInfo("查询单个业务品种信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query BusinessType";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    BusinessType data = businessTypeService.selectById(sId);

    return Json.succ(oper, data);
  }

  @PermInfo("更新业务品种信息")
  @PostMapping("/update")
  public Json updateData(@RequestBody String body) {
    String oper = "update BusinessType";
    log.info("{}, body: {}", oper, body);
    BusinessType data = JSON.parseObject(body, BusinessType.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    data.setUpdateuser(sCurUserId);
    data.setUpdatetime(sCurTime);
    data.setUpdateorg(sCurUserOrg);
    businessTypeService.updateById(data);

    return Json.succ(oper, data);
  }

  @PermInfo("删除业务品种信息")
  @DeleteMapping("/delete/{uid}")
  public Json deleteData(@PathVariable String uid) {
    String oper = "delete BusinessType";
    businessTypeService.deleteById(uid);

    return Json.succ(oper);
  }
}

