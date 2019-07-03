package com.mr.archer.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mr.archer.dao.CustomerInfoMapper;
import com.mr.archer.entity.CustomerInfo;
import com.mr.archer.service.CustomerInfoService;
import org.springframework.stereotype.Service;

@Service
public class CustomerInfoServiceImpl extends ServiceImpl<CustomerInfoMapper, CustomerInfo> implements CustomerInfoService {

  public Page<CustomerInfo> selectCustomerListByUser(Page<CustomerInfo> page, String userid, String name, String certid) {
    return page.setRecords(baseMapper.selectCustomerListByUser(page, userid, name, certid));
  }

  public Page<CustomerInfo> selectBusiCustomerListByUser(Page<CustomerInfo> page, String userid, String name, String certid) {
    return page.setRecords(baseMapper.selectBusiCustomerListByUser(page, userid, name, certid));
  }

  public Page<CustomerInfo> selectCustomerListByOrg(Page<CustomerInfo> page, String orgid, String name, String certid) {
    return page.setRecords(baseMapper.selectCustomerListByOrg(page, orgid, name, certid));
  }

}
