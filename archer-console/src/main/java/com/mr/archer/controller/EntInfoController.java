package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.entity.EntInfo;
import com.mr.archer.service.EntInfoService;
import com.mr.archer.service.SysUserService;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 企业客户基本信息 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-14
 */
@Slf4j
@Controller
@RequestMapping("/cust/entinfo")
public class EntInfoController extends BaseController {

  @Autowired
  private SysUserService userService;
  @Autowired
  private EntInfoService entInfoService;

  @PermInfo("查询单个客户的基本信息")
  @PostMapping("/query")
  public Json query(@RequestBody String body) {
    String oper = "query cust entinfo";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);
    String sCustomerId = json.getString("id");
    EntInfo entInfo = entInfoService.selectById(sCustomerId);
    // JSONObject resultJson = (JSONObject)JSON.toJSON(entInfo);

    // return Json.succ(oper).data("data", resultJson);
    Json resutl = Json.succ(oper, entInfo);
    // Json resutl = Json.succ(oper).data("data", resultJson);
    return resutl;
  }

  @PermInfo("更新单个客户的基本信息")
  @PostMapping("/update")
  public Json update(@RequestBody String body) {
    String oper = "update cust entinfo";
    log.info("{}, body: {}", oper, body);
    EntInfo entInfo = JSONObject.parseObject(body, EntInfo.class);
    entInfoService.updateById(entInfo);

    return Json.succ(oper);
  }
}

