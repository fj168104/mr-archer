package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.entity.FinBalanceSheet;
import com.mr.archer.service.FinBalanceSheetService;
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
 * 资产负债表 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-25
 */
@Slf4j
@RestController
@RequestMapping("/finbalancesheet")
public class FinBalanceSheetController extends BaseController {

  @Autowired
  private FinBalanceSheetService finBalanceSheetService;

  @PermInfo("查询单个财报数据的资产负债表信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query FinBalanceSheet";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);
    JSONObject resultJson = new JSONObject();

    String sFinDataId = json.getString("baseinfoid");
    Wrapper<FinBalanceSheet> queryParams = new EntityWrapper<>();
    queryParams.eq("baseinfoid", sFinDataId);
    List<FinBalanceSheet> datalist = finBalanceSheetService.selectList(queryParams);

    for (FinBalanceSheet info : datalist) {
      String sType = info.getType();
      if ("01".equals(sType)) {
        resultJson.put("lastinfo", JSONObject.toJSON(info));
      } else if ("02".equals(sType)) {
        resultJson.put("curinfo", JSONObject.toJSON(info));
      }
    }

    return Json.succ(oper, resultJson);
  }

  @PermInfo("更新单个财报数据的资产负债表信息")
  @Transactional
  @PostMapping("/update")
  public Json updateData(@RequestBody String body) {
    String oper = "update FinBalanceSheet";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);
    JSONObject curinfo = json.getJSONObject("curinfo");
    FinBalanceSheet curData = JSONObject.toJavaObject(curinfo, FinBalanceSheet.class);
    finBalanceSheetService.updateById(curData);

    JSONObject lastinfo = json.getJSONObject("lastinfo");
    FinBalanceSheet lastData = JSONObject.toJavaObject(lastinfo, FinBalanceSheet.class);
    finBalanceSheetService.updateById(lastData);

    return Json.succ(oper);
  }

}

