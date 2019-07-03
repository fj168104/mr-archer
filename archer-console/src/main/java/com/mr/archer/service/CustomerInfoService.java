package com.mr.archer.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mr.archer.entity.CustomerInfo;

public interface CustomerInfoService extends IService<CustomerInfo> {

  /**
   * 查询用户拥有信息查看权的客户列表
   * @param page
   * @param userid
   * @param name
   * @param certid
   * @return
   */
  Page<CustomerInfo> selectCustomerListByUser(Page<CustomerInfo> page, String userid, String name, String certid);

  /**
   * 查询用户拥有业务申办权的客户列表
   * @param page
   * @param userid
   * @param name
   * @param certid
   * @return
   */
  Page<CustomerInfo> selectBusiCustomerListByUser(Page<CustomerInfo> page, String userid, String name, String certid);

  /**
   * 查询用户所属机构及以下的客户列表
   * @param page
   * @param orgid
   * @param name
   * @param certid
   * @return
   */
  Page<CustomerInfo> selectCustomerListByOrg(Page<CustomerInfo> page, String orgid, String name, String certid);

}
