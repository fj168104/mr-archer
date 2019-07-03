package com.mr.archer.service.impl;

import com.mr.archer.entity.ReportConfigRela;
import com.mr.archer.dao.ReportConfigRelaMapper;
import com.mr.archer.service.ReportConfigRelaService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 调查报告配置节点关联表 服务实现类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-01
 */
@Service
public class ReportConfigRelaServiceImpl extends ServiceImpl<ReportConfigRelaMapper, ReportConfigRela> implements ReportConfigRelaService {

  public List<ReportConfigRela> selectListByReportId(String reportid) {
    return baseMapper.selectListByReportId(reportid);
  }
}
