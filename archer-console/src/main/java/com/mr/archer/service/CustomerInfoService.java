package com.mr.archer.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mr.archer.entity.CustomerInfo;

import java.util.List;

public interface CustomerInfoService extends IService<CustomerInfo> {

  Page<CustomerInfo> selectCustomerListByUser(Page<CustomerInfo> page, String userid, String name, String certid);
}
