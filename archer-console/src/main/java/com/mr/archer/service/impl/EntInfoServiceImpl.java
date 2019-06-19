package com.mr.archer.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.entity.EntInfo;
import com.mr.archer.dao.EntInfoMapper;
import com.mr.archer.service.EntInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 企业客户基本信息 服务实现类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-14
 */
@Service
public class EntInfoServiceImpl extends ServiceImpl<EntInfoMapper, EntInfo> implements EntInfoService {

  public EntInfo selectEntInfoById(String id) {
    return baseMapper.selectEntInfoById(id);
  }
}
