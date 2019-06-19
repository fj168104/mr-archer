package com.mr.archer.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mr.archer.entity.EntInfo;

/**
 * <p>
 * 企业客户基本信息 服务类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-14
 */
public interface EntInfoService extends IService<EntInfo> {

  EntInfo selectEntInfoById(String id);
}
