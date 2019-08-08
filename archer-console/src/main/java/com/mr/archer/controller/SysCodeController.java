package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.constant.SystemConstant;
import com.mr.archer.entity.SysCode;
import com.mr.archer.service.SysCodeService;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 代码表 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-16
 */
@Slf4j
@RestController
@RequestMapping(SystemConstant.APP_CONTEXT + "/syscode")
public class SysCodeController extends BaseController {

  @Autowired
  private SysCodeService sysCodeService;

  @PermInfo("查询代码列表")
  @PostMapping("/querylist")
  public Json queryList(@RequestBody String body) {
    String oper = "query syscode list";
    log.info("{}, body: {}", oper, body);
    JSONObject reqJson = JSON.parseObject(body);

    JSONArray aCodeList = reqJson.getJSONArray("codelist");
    JSONArray aTreeList = reqJson.getJSONArray("treelist");
    JSONObject resultJson = sysCodeService.queryCodeList(aCodeList, aTreeList);

    return Json.succ(oper, resultJson);
  }


}

