package com.mr.archer.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.entity.CustomerTransfer;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 客户权限维护 服务类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-04
 */
public interface CustomerTransferService extends IService<CustomerTransfer> {

  Page<CustomerTransfer> selectListByCreateUser(Page<CustomerTransfer> page, String userid, String customername);

  Page<CustomerTransfer> selectListByUser(Page<CustomerTransfer> page, String userid, String status, String customername);

  void updateStatusById(String id, String status, String updateuser, String updatetime, String updateorg);
}
