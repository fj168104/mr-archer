package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.entity.SysCode;
import com.mr.archer.service.SysCodeService;
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
 * 代码表 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-16
 */
@Slf4j
@RestController
@RequestMapping("/syscode")
public class SysCodeController extends BaseController {

  @Autowired
  private SysCodeService sysCodeService;

  @PermInfo("查询代码列表")
  @PostMapping("/querylist")
  public Json queryList(@RequestBody String body) {
    String oper = "query syscode list";
    log.info("{}, body: {}", oper, body);
    JSONObject resultJson = new JSONObject();
    JSONObject reqJson = JSON.parseObject(body);
    JSONArray aCodeList = reqJson.getJSONArray("codelist");
    for (int i = 0; i < aCodeList.size(); i++) {
      Object oCode = aCodeList.get(i);
      String sCurId = oCode.toString();
      Wrapper<SysCode> queryParams = new EntityWrapper<>();
      queryParams.eq("id", sCurId);
      queryParams.orderBy("sortno");
      queryParams.setSqlSelect("opt,name");
      List<SysCode> curCodeList = sysCodeService.selectList(queryParams);
      JSONArray aSelectCode = new JSONArray();
      for (SysCode code : curCodeList) {
        JSONObject oCodeJson = new JSONObject();
        oCodeJson.put("value", code.getOpt());
        oCodeJson.put("label", code.getName());
        aSelectCode.add(oCodeJson);
      }
      resultJson.put(sCurId, aSelectCode);
    }

    return Json.succ(oper, resultJson);
  }
}

