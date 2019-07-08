package com.mr.archer.service.impl;

import com.mr.archer.entity.CustomerConfig;
import com.mr.archer.dao.CustomerConfigMapper;
import com.mr.archer.service.CustomerConfigService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户配置信息 服务实现类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-08
 */
@Service
public class CustomerConfigServiceImpl extends ServiceImpl<CustomerConfigMapper, CustomerConfig> implements CustomerConfigService {

}
