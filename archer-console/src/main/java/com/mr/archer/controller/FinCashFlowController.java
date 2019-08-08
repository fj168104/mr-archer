package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.constant.SystemConstant;
import com.mr.archer.entity.FinCashFlow;
import com.mr.archer.entity.FinProfit;
import com.mr.archer.service.FinCashFlowService;
import com.mr.archer.service.FinProfitService;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.mr.archer.controller.BaseController;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 现金流量表 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-25
 */
@Slf4j
@RestController
@RequestMapping(SystemConstant.APP_CONTEXT + "/fincashflow")
public class FinCashFlowController extends BaseController {

  @Autowired
  private FinCashFlowService finCashFlowService;

  @PermInfo("查询单个财报数据的现金流量表信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query FinCashFlow";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);
    JSONObject resultJson = new JSONObject();

    String sFinDataId = json.getString("baseinfoid");
    Wrapper<FinCashFlow> queryParams = new EntityWrapper<>();
    queryParams.eq("baseinfoid", sFinDataId);
    List<FinCashFlow> datalist = finCashFlowService.selectList(queryParams);

    for (FinCashFlow info : datalist) {
      String sType = info.getType();
      if ("01".equals(sType)) {
        resultJson.put("lastinfo", JSONObject.toJSON(info));
      } else if ("02".equals(sType)) {
        resultJson.put("curinfo", JSONObject.toJSON(info));
      }
    }

    return Json.succ(oper, resultJson);
  }

  @PermInfo("更新单个财报数据的现金流量表信息")
  @Transactional
  @PostMapping("/update")
  public Json updateData(@RequestBody String body) {
    String oper = "update FinCashFlow";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);
    JSONObject curinfo = json.getJSONObject("curinfo");
    FinCashFlow curData = JSONObject.toJavaObject(curinfo, FinCashFlow.class);
    finCashFlowService.updateById(curData);

    JSONObject lastinfo = json.getJSONObject("lastinfo");
    FinCashFlow lastData = JSONObject.toJavaObject(lastinfo, FinCashFlow.class);
    finCashFlowService.updateById(lastData);

    return Json.succ(oper);
  }
}

