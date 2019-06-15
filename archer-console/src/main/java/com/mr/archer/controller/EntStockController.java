package com.mr.archer.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.entity.EntStock;
import com.mr.archer.entity.SysUser;
import com.mr.archer.service.EntStockService;
import com.mr.archer.service.SysUserService;
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
 * 股东情况 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-14
 */
@Slf4j
@RestController
@RequestMapping("/cust/entstock")
public class EntStockController extends BaseController {

  @Autowired
  private SysUserService userService;
  @Autowired
  private EntStockService entStockService;

  @PermInfo("查询所有客户的股东信息")
  @PostMapping("/list")
  public Json query(@RequestBody String body) {
    String oper = "query cust entstock";
    log.info("{}, body: {}", oper, body);
    JSONObject json = JSON.parseObject(body);

    Wrapper<EntStock> queryParams = new EntityWrapper<>();

    String sCustomerId = json.getString("customerid");
    queryParams.eq("customerid", sCustomerId);
    Page<EntStock> page = entStockService.selectPage(PageUtils.getPageParam(json), queryParams);

    return Json.succ(oper).data("data", page);
  }

  @PermInfo("新增客户的股东信息")
  @PostMapping("/create")
  public Json createData(@RequestBody String body) {
    String oper = "create cust entstock";
    log.info("{}, body: {}", oper, body);
    EntStock stock = JSON.parseObject(body, EntStock.class);
    SysUser curUser = getCurUser();
    String sCurUserId = String.valueOf(curUser.getId());
    String sCurUserOrg = curUser.getOrgid();
    String sCurTime = DateUtils.getNowTime();
    stock.setId(KeyUtils.getKey());
    stock.setCreateuser(sCurUserId);
    stock.setCreatetime(sCurTime);
    stock.setCreateorg(sCurUserOrg);
    stock.setUpdateuser(sCurUserId);
    stock.setUpdatetime(sCurTime);
    stock.setUpdateorg(sCurUserOrg);
    entStockService.insert(stock);

    return Json.succ(oper, stock);
  }

  @PermInfo("删除客户的股东信息")
  @DeleteMapping("/delete/{uid}")
  public Json deleteData(@PathVariable String uid) {
    String oper = "delete cust entstock";
    entStockService.deleteById(uid);

    return Json.succ(oper);
  }
}

