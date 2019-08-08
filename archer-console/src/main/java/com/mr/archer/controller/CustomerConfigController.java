package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.constant.SystemConstant;
import com.mr.archer.entity.CustomerConfig;
import com.mr.archer.service.CustomerConfigService;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.mr.archer.controller.BaseController;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 客户配置信息 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-08
 */
@Slf4j
@RestController
@RequestMapping(SystemConstant.APP_CONTEXT + "/customerconfig")
public class CustomerConfigController extends BaseController {

  @Autowired
  private CustomerConfigService customerConfigService;

  @PermInfo("查询单个客户配置信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query CustomerConfig";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    CustomerConfig data = customerConfigService.selectById(sId);

    return Json.succ(oper, data);
  }
}

