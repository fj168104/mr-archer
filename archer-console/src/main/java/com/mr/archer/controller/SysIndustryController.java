package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.constant.SystemConstant;
import com.mr.archer.entity.SysIndustry;
import com.mr.archer.service.SysIndustryService;
import com.mr.archer.utils.ListUtils;
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
 * 国标行业分类表 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-18
 */
@Slf4j
@RestController
@RequestMapping(SystemConstant.APP_CONTEXT + "/sysindustry")
public class SysIndustryController extends BaseController {

  @Autowired
  private SysIndustryService sysIndustryService;

  @PermInfo("查询行业列表信息")
  @PostMapping("/list")
  public Json queryList() {
    String oper = "query sysindustry list";
    Wrapper<SysIndustry> queryParams1 = new EntityWrapper<>();
    queryParams1.isNotNull("l1");
    queryParams1.ne("l1", "");
    queryParams1.orderBy("sortno");
    List<SysIndustry> l1List = sysIndustryService.selectList(queryParams1);
    Wrapper<SysIndustry> queryParams2 = new EntityWrapper<>();
    queryParams2.isNotNull("l2");
    queryParams2.ne("l2", "");
    queryParams2.orderBy("sortno");
    List<SysIndustry> l2List = sysIndustryService.selectList(queryParams2);
    Wrapper<SysIndustry> queryParams3 = new EntityWrapper<>();
    queryParams3.isNotNull("l3");
    queryParams3.ne("l3", "");
    queryParams3.orderBy("sortno");
    List<SysIndustry> l3List = sysIndustryService.selectList(queryParams3);
    Wrapper<SysIndustry> queryParams4 = new EntityWrapper<>();
    queryParams4.isNotNull("l4");
    queryParams4.ne("l4", "");
    queryParams4.orderBy("sortno");
    List<SysIndustry> l4List = sysIndustryService.selectList(queryParams4);

    JSONArray aIndustry = new JSONArray();
    for (SysIndustry l1 : l1List) {
      String sL1 = l1.getL1();
      String sL1Name = l1.getName();
      JSONObject l1Json = new JSONObject();
      l1Json.put("value", sL1);
      l1Json.put("label", sL1Name);
      aIndustry.add(l1Json);

      JSONArray l1Childs = new JSONArray();
      l1Json.put("children", l1Childs);
      for (int x = 0; x < l2List.size(); x++) {
        SysIndustry l2 = l2List.get(x);
        String sL2 = l2.getL2();
        if (sL2.startsWith(sL1)) {
          JSONObject l2Json = new JSONObject();
          String sL2Name = l2.getName();
          l2Json.put("value", sL2);
          l2Json.put("label", sL2Name);
          l1Childs.add(l2Json);

          JSONArray l2Childs = new JSONArray();
          l2Json.put("children", l2Childs);
          for (int y = 0; y < l3List.size(); y++) {
            SysIndustry l3 = l3List.get(y);
            String sL3 = l3.getL3();
            if (sL3.startsWith(sL2)) {
              JSONObject l3Json = new JSONObject();
              String sL3Name = l3.getName();
              l3Json.put("value", sL3);
              l3Json.put("label", sL3Name);
              l2Childs.add(l3Json);

              String sL4Temp = l3.getL4();
              JSONArray l3Childs = new JSONArray();
              l3Json.put("children", l3Childs);
              if (sL4Temp != null && !"".equals(sL4Temp)) {
                JSONObject l4Json = new JSONObject();
                l4Json.put("value", sL4Temp);
                l4Json.put("label", sL3Name);
                l3Childs.add(l4Json);
                l4List.remove(0);
                continue;
              }
              for (int z = 0; z < l4List.size(); z++) {
                SysIndustry l4 = l4List.get(z);
                String sL4 = l4.getL4();
                if (sL4.startsWith(sL3)) {
                  JSONObject l4Json = new JSONObject();
                  String sL4Name = l4.getName();
                  l4Json.put("value", sL4);
                  l4Json.put("label", sL4Name);
                  l3Childs.add(l4Json);
                } else {
                  ListUtils.removeBefore(l4List, z);
                  break;
                }
              }
            } else {
              ListUtils.removeBefore(l3List, y);
              break;
            }
          }
        } else {
          ListUtils.removeBefore(l2List, x);
          break;
        }
      }
    }

    return Json.succ(oper, aIndustry);
  }

  @PermInfo("查询行业列表信息")
  @PostMapping("/levellist")
  public Json levelList(@RequestBody String body) {
    String oper = "query sysindustry list";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    Wrapper<SysIndustry> queryParams = new EntityWrapper<>();
    String sLevel = json.getString("level");
    switch (sLevel) {
      case "0":
        queryParams.isNotNull("l1");
        break;
      case "1":
        String sLevel1 = json.getString("p");
        queryParams.like("l2", sLevel1, SqlLike.RIGHT);
        break;
      case "2":
        String sLevel2 = json.getString("p");
        queryParams.like("l3", sLevel2, SqlLike.RIGHT);
        break;
      case "3":
        String sLevel3 = json.getString("p");
        queryParams.like("l4", sLevel3, SqlLike.RIGHT);
        break;
      default:
        break;
    }
    queryParams.orderBy("sortno");
    List<SysIndustry> industryList = sysIndustryService.selectList(queryParams);
    JSONArray aIndustry = new JSONArray();
    for (SysIndustry si : industryList) {
      JSONObject oIndustry = new JSONObject();
      oIndustry.put("label", si.getName());
      switch (sLevel) {
        case "0":
          oIndustry.put("value", si.getL1());
          break;
        case "1":
          oIndustry.put("value", si.getL2());
          break;
        case "2":
          oIndustry.put("value", si.getL3());
          break;
        case "3":
          oIndustry.put("value", si.getL4());
          break;
        default:
          break;
      }
      aIndustry.add(oIndustry);
    }

    return Json.succ(oper, aIndustry);
  }
}

