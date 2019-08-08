package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.constant.SystemConstant;
import com.mr.archer.entity.FilelistTreeNodeRela;
import com.mr.archer.entity.SysUser;
import com.mr.archer.service.FilelistTreeNodeRelaService;
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
 * 资料清单树图节点关联表 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-05
 */
@Slf4j
@RestController
@RequestMapping(SystemConstant.APP_CONTEXT + "/filelisttreenoderela")
public class FilelistTreeNodeRelaController extends BaseController {

  @Autowired
  private FilelistTreeNodeRelaService filelistTreeNodeRelaService;

  @PermInfo("查询资料清单树图节点关联列表信息")
  @PostMapping("/list")
  public Json query(@RequestBody String body) {
    String oper = "query FilelistTreeNodeRela";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    JSONObject filterJson = json.getJSONObject("filters");
    String sTreeId = filterJson.getString("treeid");
    String sName = filterJson.getString("name");
    Page<FilelistTreeNodeRela> page = filelistTreeNodeRelaService.selectTreeNodeRelaList(PageUtils.getPageParam(json), sTreeId, sName);

    return Json.succ(oper).data("data", page);
  }

  @PermInfo("新增资料清单树图节点关联信息")
  @PostMapping("/create")
  public Json createData(@RequestBody String body) {
    String oper = "create FilelistTreeNodeRela";
    log.info("{}, body: {}", oper, body);
    FilelistTreeNodeRela newData = JSON.parseObject(body, FilelistTreeNodeRela.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    newData.setId(KeyUtils.getKey("FTNR"));
    newData.setCreateuser(sCurUserId);
    newData.setCreatetime(sCurTime);
    newData.setCreateorg(sCurUserOrg);
    newData.setUpdateuser(sCurUserId);
    newData.setUpdatetime(sCurTime);
    newData.setUpdateorg(sCurUserOrg);
    filelistTreeNodeRelaService.insert(newData);

    return Json.succ(oper, newData);
  }

  @PermInfo("查询单个资料清单树图节点关联信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query FilelistTreeNodeRela";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    FilelistTreeNodeRela data = filelistTreeNodeRelaService.selectById(sId);

    return Json.succ(oper, data);
  }

  @PermInfo("更新资料清单树图节点关联信息")
  @PostMapping("/update")
  public Json updateData(@RequestBody String body) {
    String oper = "update FilelistTreeNodeRela";
    log.info("{}, body: {}", oper, body);
    FilelistTreeNodeRela data = JSON.parseObject(body, FilelistTreeNodeRela.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    data.setUpdateuser(sCurUserId);
    data.setUpdatetime(sCurTime);
    data.setUpdateorg(sCurUserOrg);
    filelistTreeNodeRelaService.updateById(data);

    return Json.succ(oper, data);
  }

  @PermInfo("删除资料清单树图节点关联信息")
  @DeleteMapping("/delete/{uid}")
  public Json deleteData(@PathVariable String uid) {
    String oper = "delete FilelistTreeNodeRela";
    filelistTreeNodeRelaService.deleteById(uid);

    return Json.succ(oper);
  }
}

