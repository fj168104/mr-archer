package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.constant.SystemConstant;
import com.mr.archer.entity.CustomerBelong;
import com.mr.archer.entity.CustomerTransfer;
import com.mr.archer.entity.SysUser;
import com.mr.archer.service.CustomerBelongService;
import com.mr.archer.service.CustomerTransferService;
import com.mr.archer.utils.DateUtils;
import com.mr.archer.utils.KeyUtils;
import com.mr.archer.utils.PageUtils;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 客户权限维护 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-04
 */
@Slf4j
@RestController
@RequestMapping(SystemConstant.APP_CONTEXT + "/customertransfer")
public class CustomerTransferController extends BaseController {

  @Autowired
  private CustomerTransferService customerTransferService;
  @Autowired
  private CustomerBelongService customerBelongService;

  @PermInfo("查询客户转移列表信息")
  @PostMapping("/list")
  public Json query(@RequestBody String body) {
    String oper = "query cust customertransfer";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    JSONObject filterJson = json.getJSONObject("filters");
    String sCustomerName = filterJson.getString("customername");

    Page<CustomerTransfer> page = customerTransferService.selectListByCreateUser(PageUtils.getPageParam(json), sCurUserId, sCustomerName);

    return Json.succ(oper).data("data", page);
  }

  @PermInfo("查询待处理/已处理的客户转移列表信息")
  @PostMapping("/approvelist")
  public Json approveList(@RequestBody String body) {
    String oper = "query deallist cust customertransfer";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    JSONObject filterJson = json.getJSONObject("filters");
    String sStatus = filterJson.getString("status");
    String sCustomerName = filterJson.getString("customername");

    Page<CustomerTransfer> page = customerTransferService.selectListByUser(PageUtils.getPageParam(json), sCurUserId, sStatus, sCustomerName);

    return Json.succ(oper).data("data", page);
  }

  @PermInfo("新增客户的转移信息")
  @PostMapping("/create")
  public Json createData(@RequestBody String body) {
    String oper = "create cust customertransfer";
    log.info("{}, body: {}", oper, body);
    CustomerTransfer newData = JSON.parseObject(body, CustomerTransfer.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    newData.setId(KeyUtils.getKey("CT"));
    newData.setCreateuser(sCurUserId);
    newData.setCreatetime(sCurTime);
    newData.setCreateorg(sCurUserOrg);
    newData.setUpdateuser(sCurUserId);
    newData.setUpdatetime(sCurTime);
    newData.setUpdateorg(sCurUserOrg);
    newData.setApplydate(sCurTime);
    newData.setStatus("01");
    customerTransferService.insert(newData);

    return Json.succ(oper, newData);
  }

  @PermInfo("查询单个转移信息")
  @PostMapping("/query")
  public Json queryData(@RequestBody String body) {
    String oper = "query cust customertransfer";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    CustomerTransfer data = customerTransferService.selectById(sId);

    return Json.succ(oper, data);
  }

  @PermInfo("更新客户转移的状态信息")
  @Transactional
  @PostMapping("/updatestatus")
  public Json updateStatus(@RequestBody String body) {
    String oper = "updatestatus cust customertransfer";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    String sId = json.getString("id");
    String sStatus = json.getString("status");
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();

    // 更新客户转移状态
    customerTransferService.updateStatusById(sId, sStatus, sCurUserId, sCurTime, sCurUserOrg);

    // 关联更新
    if ("02".equals(sStatus)) {
      // 确认
      CustomerTransfer ct = customerTransferService.selectById(sId);
      String sRightType = ct.getRighttype();
      String sCustomerId = ct.getCustomerid();

      Wrapper<CustomerBelong> cbOldParams = new EntityWrapper<>();
      cbOldParams.eq("userid", ct.getCreateuser());
      cbOldParams.eq("customerid", sCustomerId);
      CustomerBelong oldcb = customerBelongService.selectOne(cbOldParams);

      Wrapper<CustomerBelong> cbParams = new EntityWrapper<>();
      cbParams.eq("userid", sCurUserId);
      cbParams.eq("customerid", sCustomerId);
      CustomerBelong cb = customerBelongService.selectOne(cbParams);
      if (cb == null) {
        cb = new CustomerBelong();
        cb.setId(KeyUtils.getKey("CB"));
        cb.setCustomerid(sCustomerId);
        cb.setUserid(sCurUserId);
        cb.setCreateuser(sCurUserId);
        cb.setCreatetime(sCurTime);
        cb.setCreateorg(sCurUserOrg);
      }
      cb.setUpdateuser(sCurUserId);
      cb.setUpdatetime(sCurTime);
      cb.setUpdateorg(sCurUserOrg);
      cb.setModifyflag("1");
      cb.setViewflag("1");
      if ("01".equals(sRightType)) {
        // 管护权
        oldcb.setMainflag("0");
        oldcb.setApplyflag("0");
        oldcb.setModifyflag("0");
        oldcb.setUpdateuser(sCurUserId);
        oldcb.setUpdatetime(sCurTime);
        oldcb.setUpdateorg(sCurUserOrg);
        customerBelongService.updateById(oldcb);

        cb.setMainflag("1");
        cb.setApplyflag("1");
        customerBelongService.insertOrUpdateAllColumn(cb);
      } else if ("02".equals(sRightType)) {
        // 维护权
        cb.setMainflag("0");
        cb.setApplyflag("0");
        customerBelongService.insertOrUpdateAllColumn(cb);
      }
    }

    return Json.succ(oper);
  }

  @PermInfo("删除客户的转移信息")
  @DeleteMapping("/delete/{uid}")
  public Json deleteData(@PathVariable String uid) {
    String oper = "delete cust customertransfer";
    customerTransferService.deleteById(uid);

    return Json.succ(oper);
  }
}

