package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.entity.EntAddress;
import com.mr.archer.entity.SysUser;
import com.mr.archer.service.EntAddressService;
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
 * 客户地址信息 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-02
 */
@Slf4j
@RestController
@RequestMapping("/entaddress")
public class EntAddressController extends BaseController {

  @Autowired
  private EntAddressService entAddressService;

  @PermInfo("查询客户地址列表信息")
  @PostMapping("/list")
  public Json query(@RequestBody String body) {
    String oper = "query cust EntInvest";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    Wrapper<EntAddress> queryParams = new EntityWrapper<>();
    JSONObject filterJson = json.getJSONObject("filters");
    String sCustomerId = filterJson.getString("customerid");
    queryParams.eq("customerid", sCustomerId);
    String sAddress = filterJson.getString("address");
    if (StringUtils.isNotBlank(sAddress)) {
      queryParams.like("address", sAddress);
    }

    Page<EntAddress> page = entAddressService.selectPage(PageUtils.getPageParam(json), queryParams);

    return Json.succ(oper).data("data", page);
  }

  @PermInfo("新增客户的地址信息")
  @PostMapping("/create")
  public Json createData(@RequestBody String body) {
    String oper = "create cust EntAddress";
    log.info("{}, body: {}", oper, body);
    EntAddress newData = JSON.parseObject(body, EntAddress.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    newData.setId(KeyUtils.getKey("EA"));
    newData.setCreateuser(sCurUserId);
    newData.setCreatetime(sCurTime);
    newData.setCreateorg(sCurUserOrg);
    newData.setUpdateuser(sCurUserId);
    newData.setUpdatetime(sCurTime);
    newData.setUpdateorg(sCurUserOrg);
    entAddressService.insert(newData);

    return Json.succ(oper, newData);
  }

  @PermInfo("查询单个地址信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query cust EntAddress";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    EntAddress data = entAddressService.selectById(sId);

    return Json.succ(oper, data);
  }

  @PermInfo("更新客户的地址信息")
  @PostMapping("/update")
  public Json updateData(@RequestBody String body) {
    String oper = "update cust EntAddress";
    log.info("{}, body: {}", oper, body);
    EntAddress data = JSON.parseObject(body, EntAddress.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    data.setUpdateuser(sCurUserId);
    data.setUpdatetime(sCurTime);
    data.setUpdateorg(sCurUserOrg);
    entAddressService.updateById(data);

    return Json.succ(oper, data);
  }

  @PermInfo("删除客户的地址信息")
  @DeleteMapping("/delete/{uid}")
  public Json deleteData(@PathVariable String uid) {
    String oper = "delete cust EntAddress";
    entAddressService.deleteById(uid);

    return Json.succ(oper);
  }
}

