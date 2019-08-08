package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.entity.FilelistRecord;
import com.mr.archer.entity.SysUser;
import com.mr.archer.service.FilelistRecordService;
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
 * 资料清单数据记录 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-09
 */
@Slf4j
@RestController
@RequestMapping("/filelistrecord")
public class FilelistRecordController extends BaseController {
  @Autowired
  private FilelistRecordService filelistRecordService;

  @PermInfo("查询资料清单数据记录列表信息")
  @PostMapping("/list")
  public Json query(@RequestBody String body) {
    String oper = "query FilelistRecord";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    Wrapper<FilelistRecord> queryParams = new EntityWrapper<>();

    JSONObject filterJson = json.getJSONObject("filters");
    String sConfigId = filterJson.getString("configid");
    String sTreeId = filterJson.getString("treeid");
    String sNodeId = filterJson.getString("nodeid");
    queryParams.eq("configid", sConfigId);
    queryParams.eq("treeid", sTreeId);
    queryParams.eq("nodeid", sNodeId);

    String sFileType = filterJson.getString("filetype");
    if ("01".equalsIgnoreCase(sFileType)) {
      // 01-Img
      List<FilelistRecord> list = filelistRecordService.selectList(queryParams);
      return Json.succ(oper).data("data", list);
    } else {
      Page<FilelistRecord> page = filelistRecordService.selectPage(PageUtils.getPageParam(json), queryParams);
      return Json.succ(oper).data("data", page);
    }
  }

  @PermInfo("新增资料清单数据记录信息")
  @PostMapping("/create")
  public Json createData(@RequestBody String body) {
    String oper = "create FilelistRecord";
    log.info("{}, body: {}", oper, body);
    FilelistRecord newData = JSON.parseObject(body, FilelistRecord.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    newData.setId(KeyUtils.getKey("ES"));
    newData.setCreateuser(sCurUserId);
    newData.setCreatetime(sCurTime);
    newData.setCreateorg(sCurUserOrg);
    newData.setUpdateuser(sCurUserId);
    newData.setUpdatetime(sCurTime);
    newData.setUpdateorg(sCurUserOrg);
    filelistRecordService.insert(newData);

    return Json.succ(oper, newData);
  }

  @PermInfo("查询单个资料清单数据记录信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query FilelistRecord";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    FilelistRecord data = filelistRecordService.selectById(sId);

    return Json.succ(oper, data);
  }

  @PermInfo("更新资料清单数据记录信息")
  @PostMapping("/update")
  public Json updateData(@RequestBody String body) {
    String oper = "update FilelistRecord";
    log.info("{}, body: {}", oper, body);
    FilelistRecord data = JSON.parseObject(body, FilelistRecord.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    data.setUpdateuser(sCurUserId);
    data.setUpdatetime(sCurTime);
    data.setUpdateorg(sCurUserOrg);
    filelistRecordService.updateById(data);

    return Json.succ(oper, data);
  }

  @PermInfo("删除资料清单数据记录信息")
  @DeleteMapping("/delete/{uid}")
  public Json deleteData(@PathVariable String uid) {
    String oper = "delete FilelistRecord";
    filelistRecordService.deleteById(uid);

    return Json.succ(oper);
  }
}

