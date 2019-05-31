package com.mr.archer.service.impl;

import com.mr.archer.entity.AppAuditLog;
import com.mr.archer.dao.AppAuditLogMapper;
import com.mr.archer.service.AppAuditLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * app审核日志表 服务实现类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-05-19
 */
@Service
public class AppAuditLogServiceImpl extends ServiceImpl<AppAuditLogMapper, AppAuditLog> implements AppAuditLogService {

}
