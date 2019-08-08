package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.constant.SystemConstant;
import com.mr.archer.entity.ReportNode;
import com.mr.archer.entity.SysUser;
import com.mr.archer.service.ReportNodeService;
import com.mr.archer.utils.DateUtils;
import com.mr.archer.utils.KeyUtils;
import com.mr.archer.utils.PageUtils;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 调查报告节点节点表 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-01
 */
@Slf4j
@RestController
@RequestMapping(SystemConstant.APP_CONTEXT + "/reportnode")
public class ReportNodeController extends BaseController {

  @Autowired
  private ReportNodeService reportNodeService;

  @PermInfo("查询调查报告节点信息列表信息")
  @PostMapping("/list")
  public Json query(@RequestBody String body) {
    String oper = "query ReportNode";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    Wrapper<ReportNode> queryParams = new EntityWrapper<>();
    JSONObject filterJson = json.getJSONObject("filters");
    String sName = filterJson.getString("name");
    if (StringUtils.isNotBlank(sName)) {
      queryParams.like("name", sName);
    }
    String sIsInUse = filterJson.getString("isinuse");
    if (StringUtils.isNotBlank(sIsInUse)) {
      queryParams.like("isinuse", sIsInUse);
    }

    Page<ReportNode> page = reportNodeService.selectPage(PageUtils.getPageParam(json), queryParams);

    return Json.succ(oper).data("data", page);
  }

  @PermInfo("新增调查报告节点信息")
  @PostMapping("/create")
  public Json createData(@RequestBody String body) {
    String oper = "create ReportNode";
    log.info("{}, body: {}", oper, body);
    ReportNode newData = JSON.parseObject(body, ReportNode.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    newData.setId(KeyUtils.getKey("RN"));
    newData.setCreateuser(sCurUserId);
    newData.setCreatetime(sCurTime);
    newData.setCreateorg(sCurUserOrg);
    newData.setUpdateuser(sCurUserId);
    newData.setUpdatetime(sCurTime);
    newData.setUpdateorg(sCurUserOrg);
    reportNodeService.insert(newData);

    return Json.succ(oper, newData);
  }

  @PermInfo("查询单个调查报告节点信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query ReportNode";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    ReportNode data = reportNodeService.selectById(sId);

    return Json.succ(oper, data);
  }

  @PermInfo("更新调查报告节点信息")
  @PostMapping("/update")
  public Json updateData(@RequestBody String body) {
    String oper = "update ReportNode";
    log.info("{}, body: {}", oper, body);
    ReportNode data = JSON.parseObject(body, ReportNode.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    data.setUpdateuser(sCurUserId);
    data.setUpdatetime(sCurTime);
    data.setUpdateorg(sCurUserOrg);
    reportNodeService.updateById(data);

    return Json.succ(oper, data);
  }

  @PermInfo("删除调查报告节点信息")
  @DeleteMapping("/delete/{uid}")
  public Json deleteData(@PathVariable String uid) {
    String oper = "delete ReportNode";
    reportNodeService.deleteById(uid);

    return Json.succ(oper);
  }

}

