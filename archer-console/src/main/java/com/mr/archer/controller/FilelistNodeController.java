package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.constant.SystemConstant;
import com.mr.archer.entity.FilelistNode;
import com.mr.archer.entity.SysUser;
import com.mr.archer.service.FilelistNodeService;
import com.mr.archer.utils.DateUtils;
import com.mr.archer.utils.KeyUtils;
import com.mr.archer.utils.PageUtils;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.mr.archer.controller.BaseController;

/**
 * <p>
 * 资料清单配置节点表 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-05
 */
@Slf4j
@RestController
@RequestMapping(SystemConstant.APP_CONTEXT + "/filelistnode")
public class FilelistNodeController extends BaseController {

  @Autowired
  private FilelistNodeService filelistNodeService;

  @PermInfo("查询资料清单配置节点列表信息")
  @PostMapping("/list")
  public Json query(@RequestBody String body) {
    String oper = "query FilelistNode";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    Wrapper<FilelistNode> queryParams = new EntityWrapper<>();

    JSONObject filterJson = json.getJSONObject("filters");
    String sName = filterJson.getString("name");
    if (StringUtils.isNotBlank(sName)) {
      queryParams.like("name", sName);
    }
    Page<FilelistNode> page = filelistNodeService.selectPage(PageUtils.getPageParam(json), queryParams);

    return Json.succ(oper).data("data", page);
  }

  @PermInfo("新增资料清单配置节点信息")
  @PostMapping("/create")
  public Json createData(@RequestBody String body) {
    String oper = "create FilelistNode";
    log.info("{}, body: {}", oper, body);
    FilelistNode newData = JSON.parseObject(body, FilelistNode.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    newData.setId(KeyUtils.getKey("FN"));
    newData.setCreateuser(sCurUserId);
    newData.setCreatetime(sCurTime);
    newData.setCreateorg(sCurUserOrg);
    newData.setUpdateuser(sCurUserId);
    newData.setUpdatetime(sCurTime);
    newData.setUpdateorg(sCurUserOrg);
    filelistNodeService.insert(newData);

    return Json.succ(oper, newData);
  }

  @PermInfo("查询单个资料清单配置节点信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query FilelistNode";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    FilelistNode data = filelistNodeService.selectById(sId);

    return Json.succ(oper, data);
  }

  @PermInfo("更新资料清单配置节点信息")
  @PostMapping("/update")
  public Json updateData(@RequestBody String body) {
    String oper = "update FilelistNode";
    log.info("{}, body: {}", oper, body);
    FilelistNode data = JSON.parseObject(body, FilelistNode.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    data.setUpdateuser(sCurUserId);
    data.setUpdatetime(sCurTime);
    data.setUpdateorg(sCurUserOrg);
    filelistNodeService.updateById(data);

    return Json.succ(oper, data);
  }

  @PermInfo("删除资料清单配置节点信息")
  @DeleteMapping("/delete/{uid}")
  public Json deleteData(@PathVariable String uid) {
    String oper = "delete FilelistNode";
    filelistNodeService.deleteById(uid);

    return Json.succ(oper);
  }
}

