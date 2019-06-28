package com.mr.archer.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.entity.BusinessApply;
import com.mr.archer.dao.BusinessApplyMapper;
import com.mr.archer.service.BusinessApplyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务申请 服务实现类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-27
 */
@Service
public class BusinessApplyServiceImpl extends ServiceImpl<BusinessApplyMapper, BusinessApply> implements BusinessApplyService {

  public Page<BusinessApply> selectBusinessApplyListByUser(Page<BusinessApply> page, String userid, String customername) {
    return page.setRecords(baseMapper.selectBusinessApplyListByUser(page, userid, customername));
  }
}
