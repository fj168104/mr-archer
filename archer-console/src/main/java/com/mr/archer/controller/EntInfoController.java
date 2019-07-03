package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.entity.*;
import com.mr.archer.service.*;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 企业客户基本信息 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-14
 */
@Slf4j
@RestController
@RequestMapping("/cust/entinfo")
public class EntInfoController extends BaseController {

  @Autowired
  private SysUserService userService;
  @Autowired
  private EntInfoService entInfoService;
  @Autowired
  private EntManagerService entManagerService;
  @Autowired
  private EntMemberService entMemberService;
  @Autowired
  private EntStockService entStockService;
  @Autowired
  private EntInvestService entInvestService;
  @Autowired
  private SysIndustryService sysIndustryService;
  @Autowired
  private SysCodeService sysCodeService;

  @PermInfo("查询单个客户的基本信息")
  @PostMapping("/query")
  public Json query(@RequestBody String body) {
    String oper = "query cust entinfo";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);
    String sCustomerId = json.getString("id");
    EntInfo entInfo = entInfoService.selectEntInfoById(sCustomerId);

    Json resutl = Json.succ(oper, entInfo);
    return resutl;
  }

  @PermInfo("查询单个客户的调查报告基本信息")
  @PostMapping("/queryreport")
  public Json queryReport(@RequestBody String body) {
    String oper = "query cust entinfo report";
    log.info("{}, body: {}", oper, body);
    JSONObject resultJson = new JSONObject();
    JSONObject json = JSON.parseObject(body);

    JSONArray codelist = json.getJSONArray("codelist");
    if (codelist != null) {
      JSONObject oCodeList = sysCodeService.queryCodeList(codelist, null);
      resultJson.put("codemap", oCodeList);
    }

    String sCustomerId = json.getString("id");
    EntInfo entInfo = entInfoService.selectEntInfoById(sCustomerId);

    Wrapper<SysIndustry> siParams = new EntityWrapper<>();
    siParams.eq("l4", entInfo.getIndustry());
    SysIndustry sysIndustry = sysIndustryService.selectOne(siParams);
    entInfo.setIndustryname(sysIndustry.getName());

    JSONObject oEntInfo = (JSONObject)JSON.toJSON(entInfo);
    resultJson.put("entinfo", oEntInfo);

    // 法人代表
//    Wrapper<EntManager> emParams = new EntityWrapper<>();
//    emParams.eq("customerid", sCustomerId);
//    emParams.eq("duty", "01");
//    EntManager entManager = entManagerService.selectOne(emParams);
//    JSONObject oEntManager = (JSONObject)JSON.toJSON(entManager);
//    resultJson.put("entmanager", oEntManager);
    // 高管信息
    Wrapper<EntManager> emParams = new EntityWrapper<>();
    emParams.eq("customerid", sCustomerId);
    List<EntManager> entManagerList = entManagerService.selectList(emParams);
    JSONArray aEntManagerList = (JSONArray)JSONArray.toJSON(entManagerList);
    resultJson.put("entmanagerlist", aEntManagerList);

    // 股权结构
    Wrapper<EntStock> esParams = new EntityWrapper<>();
    esParams.eq("customerid", sCustomerId);
    List<EntStock> entStockList = entStockService.selectList(esParams);
    JSONArray aEntStockList = (JSONArray)JSONArray.toJSON(entStockList);
    resultJson.put("entstocklist", aEntStockList);

    // 对外投资
    Wrapper<EntInvest> eiParams = new EntityWrapper<>();
    eiParams.eq("customerid", sCustomerId);
    List<EntInvest> entInvestList = entInvestService.selectList(eiParams);
    JSONArray aEntInvestList = (JSONArray)JSONArray.toJSON(entInvestList);
    resultJson.put("entinvestlist", aEntInvestList);

    return Json.succ(oper, resultJson);
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

