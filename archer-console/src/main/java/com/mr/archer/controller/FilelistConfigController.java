package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.constant.SystemConstant;
import com.mr.archer.entity.BusinessType;
import com.mr.archer.entity.FilelistConfig;
import com.mr.archer.entity.SysUser;
import com.mr.archer.service.FilelistConfigService;
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
 * 资料清单配置表 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-05
 */
@Slf4j
@RestController
@RequestMapping(SystemConstant.APP_CONTEXT + "/filelistconfig")
public class FilelistConfigController extends BaseController {

  @Autowired
  private FilelistConfigService filelistConfigService;

  @PermInfo("查询资料清单配置列表信息")
  @PostMapping("/list")
  public Json query(@RequestBody String body) {
    String oper = "query FilelistConfig";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    Wrapper<FilelistConfig> queryParams = new EntityWrapper<>();

    JSONObject filterJson = json.getJSONObject("filters");
    String sName = filterJson.getString("name");
    if (StringUtils.isNotBlank(sName)) {
      queryParams.like("name", sName);
    }
    Page<FilelistConfig> page = filelistConfigService.selectPage(PageUtils.getPageParam(json), queryParams);

    return Json.succ(oper).data("data", page);
  }

  @PermInfo("查询资料清单配置代码列表信息")
  @PostMapping("/codelist")
  public Json queryCodeList(@RequestBody String body) {
    String oper = "query codelist FilelistConfig";
    log.info("{}, body: {}", oper, body);
    JSONObject bodyJson = JSON.parseObject(body);

    String sType = bodyJson.getString("type");
    Wrapper<FilelistConfig> queryParams = new EntityWrapper<>();
    queryParams.eq("type", sType);
    List<FilelistConfig> list = filelistConfigService.selectList(queryParams);
    JSONArray codeList = new JSONArray();
    if (list != null && list.size() > 0) {
      for (int i = 0; i < list.size(); i++) {
        FilelistConfig fc = list.get(i);
        JSONObject code = new JSONObject();
        code.put("label", fc.getName());
        code.put("value", fc.getId());
        codeList.add(code);
      }
    }

    return Json.succ(oper, codeList);
  }

  @PermInfo("新增资料清单配置信息")
  @PostMapping("/create")
  public Json createData(@RequestBody String body) {
    String oper = "create FilelistConfig";
    log.info("{}, body: {}", oper, body);
    FilelistConfig newData = JSON.parseObject(body, FilelistConfig.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    newData.setId(KeyUtils.getKey("FC"));
    newData.setCreateuser(sCurUserId);
    newData.setCreatetime(sCurTime);
    newData.setCreateorg(sCurUserOrg);
    newData.setUpdateuser(sCurUserId);
    newData.setUpdatetime(sCurTime);
    newData.setUpdateorg(sCurUserOrg);
    filelistConfigService.insert(newData);

    return Json.succ(oper, newData);
  }

  @PermInfo("查询单个资料清单配置信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query FilelistConfig";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    FilelistConfig data = filelistConfigService.selectById(sId);

    return Json.succ(oper, data);
  }

  @PermInfo("更新资料清单配置信息")
  @PostMapping("/update")
  public Json updateData(@RequestBody String body) {
    String oper = "update FilelistConfig";
    log.info("{}, body: {}", oper, body);
    FilelistConfig data = JSON.parseObject(body, FilelistConfig.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    data.setUpdateuser(sCurUserId);
    data.setUpdatetime(sCurTime);
    data.setUpdateorg(sCurUserOrg);
    filelistConfigService.updateById(data);

    return Json.succ(oper, data);
  }

  @PermInfo("删除资料清单配置信息")
  @DeleteMapping("/delete/{uid}")
  public Json deleteData(@PathVariable String uid) {
    String oper = "delete FilelistConfig";
    filelistConfigService.deleteById(uid);

    return Json.succ(oper);
  }
}

