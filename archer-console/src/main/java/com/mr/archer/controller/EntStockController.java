package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.entity.EntStock;
import com.mr.archer.entity.SysUser;
import com.mr.archer.service.EntStockService;
import com.mr.archer.service.SysUserService;
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
 * 股东情况 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-14
 */
@Slf4j
@RestController
@RequestMapping("/cust/entstock")
public class EntStockController extends BaseController {

  @Autowired
  private SysUserService userService;
  @Autowired
  private EntStockService entStockService;

  @PermInfo("查询单个客户的股东列表信息")
  @PostMapping("/list")
  public Json query(@RequestBody String body) {
    String oper = "query cust entstock";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    Wrapper<EntStock> queryParams = new EntityWrapper<>();

    JSONObject filterJson = json.getJSONObject("filters");
    String sCustomerId = filterJson.getString("customerid");
    queryParams.eq("customerid", sCustomerId);
    String sName = filterJson.getString("name");
    if (StringUtils.isNotBlank(sName)) {
      queryParams.like("name", sName);
    }
    Page<EntStock> page = entStockService.selectPage(PageUtils.getPageParam(json), queryParams);

    return Json.succ(oper).data("data", page);
  }

  @PermInfo("新增客户的股东信息")
  @PostMapping("/create")
  public Json createData(@RequestBody String body) {
    String oper = "create cust entstock";
    log.info("{}, body: {}", oper, body);
    EntStock newData = JSON.parseObject(body, EntStock.class);
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
    entStockService.insert(newData);

    return Json.succ(oper, newData);
  }

  @PermInfo("查询单个股东信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query cust entstock";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    EntStock data = entStockService.selectById(sId);

    return Json.succ(oper, data);
  }

  @PermInfo("更新客户的股东信息")
  @PostMapping("/update")
  public Json updateData(@RequestBody String body) {
    String oper = "update cust entstock";
    log.info("{}, body: {}", oper, body);
    EntStock data = JSON.parseObject(body, EntStock.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    data.setUpdateuser(sCurUserId);
    data.setUpdatetime(sCurTime);
    data.setUpdateorg(sCurUserOrg);
    entStockService.updateById(data);

    return Json.succ(oper, data);
  }

  @PermInfo("删除客户的股东信息")
  @DeleteMapping("/delete/{uid}")
  public Json deleteData(@PathVariable String uid) {
    String oper = "delete cust entstock";
    entStockService.deleteById(uid);

    return Json.succ(oper);
  }
}

