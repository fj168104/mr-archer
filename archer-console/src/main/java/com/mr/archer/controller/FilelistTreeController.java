package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.entity.FilelistTree;
import com.mr.archer.entity.SysUser;
import com.mr.archer.service.FilelistTreeService;
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
 * 资料清单树图 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-05
 */
@Slf4j
@RestController
@RequestMapping("/filelisttree")
public class FilelistTreeController extends BaseController {

  @Autowired
  private FilelistTreeService filelistTreeService;

  @PermInfo("查询资料清单树图列表信息")
  @PostMapping("/list")
  public Json query(@RequestBody String body) {
    String oper = "query FilelistTree";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    Wrapper<FilelistTree> queryParams = new EntityWrapper<>();

    JSONObject filterJson = json.getJSONObject("filters");
    String sConfigId = filterJson.getString("configid");
    queryParams.eq("configid", sConfigId);
    String sName = filterJson.getString("name");
    if (StringUtils.isNotBlank(sName)) {
      queryParams.like("name", sName);
    }
    queryParams.orderBy("sortno");
    Page<FilelistTree> page = filelistTreeService.selectPage(PageUtils.getPageParam(json), queryParams);

    return Json.succ(oper).data("data", page);
  }

  @PermInfo("查询资料清单树图列表信息")
  @PostMapping("/treelist")
  public Json treeList(@RequestBody String body) {
    String oper = "query treelist FilelistTree";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    Wrapper<FilelistTree> queryParams = new EntityWrapper<>();

    String sConfigId = json.getString("configid");
    queryParams.eq("configid", sConfigId);
    queryParams.orderBy("sortno");
    List<FilelistTree> olist = filelistTreeService.selectList( queryParams);

    JSONArray aTreeList = filelistTreeService.getTreeList(olist);

    return Json.succ(oper, aTreeList);
  }

  @PermInfo("新增资料清单树图信息")
  @PostMapping("/create")
  public Json createData(@RequestBody String body) {
    String oper = "create FilelistTree";
    log.info("{}, body: {}", oper, body);
    FilelistTree newData = JSON.parseObject(body, FilelistTree.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    newData.setId(KeyUtils.getKey("FT"));
    newData.setCreateuser(sCurUserId);
    newData.setCreatetime(sCurTime);
    newData.setCreateorg(sCurUserOrg);
    newData.setUpdateuser(sCurUserId);
    newData.setUpdatetime(sCurTime);
    newData.setUpdateorg(sCurUserOrg);
    filelistTreeService.insert(newData);

    return Json.succ(oper, newData);
  }

  @PermInfo("查询单个资料清单树图信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query FilelistTree";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    FilelistTree data = filelistTreeService.selectById(sId);

    return Json.succ(oper, data);
  }

  @PermInfo("更新资料清单树图信息")
  @PostMapping("/update")
  public Json updateData(@RequestBody String body) {
    String oper = "update FilelistTree";
    log.info("{}, body: {}", oper, body);
    FilelistTree data = JSON.parseObject(body, FilelistTree.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    data.setUpdateuser(sCurUserId);
    data.setUpdatetime(sCurTime);
    data.setUpdateorg(sCurUserOrg);
    filelistTreeService.updateById(data);

    return Json.succ(oper, data);
  }

  @PermInfo("删除资料清单树图信息")
  @DeleteMapping("/delete/{uid}")
  public Json deleteData(@PathVariable String uid) {
    String oper = "delete FilelistTree";
    filelistTreeService.deleteById(uid);

    return Json.succ(oper);
  }
}

