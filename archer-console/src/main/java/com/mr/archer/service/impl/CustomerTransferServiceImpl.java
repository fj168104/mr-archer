package com.mr.archer.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mr.archer.dao.CustomerTransferMapper;
import com.mr.archer.entity.CustomerTransfer;
import com.mr.archer.service.CustomerTransferService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户权限维护 服务实现类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-04
 */
@Service
public class CustomerTransferServiceImpl extends ServiceImpl<CustomerTransferMapper, CustomerTransfer> implements CustomerTransferService {

  public Page<CustomerTransfer> selectListByCreateUser(Page<CustomerTransfer> page, String userid, String customername) {
    return page.setRecords(baseMapper.selectListByCreateUser(page, userid, customername));
  }

  public Page<CustomerTransfer> selectListByUser(Page<CustomerTransfer> page, String userid, String status, String customername) {
    return page.setRecords(baseMapper.selectListByUser(page, userid, status, customername));
  }

  public void updateStatusById(String id, String status, String updateuser, String updatetime, String updateorg) {
    baseMapper.updateStatusById(id, status, updateuser, updatetime, updateorg);
  }

}
