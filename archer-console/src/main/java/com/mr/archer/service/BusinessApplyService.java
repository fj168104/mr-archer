package com.mr.archer.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.entity.BusinessApply;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 业务申请 服务类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-27
 */
public interface BusinessApplyService extends IService<BusinessApply> {

  Page<BusinessApply> selectBusinessApplyListByUser(Page<BusinessApply> page, String userid, String customername);

  BusinessApply selectBusinessApplyById(String id);
}
