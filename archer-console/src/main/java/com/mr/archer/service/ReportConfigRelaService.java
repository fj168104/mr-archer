package com.mr.archer.service;

import com.mr.archer.entity.ReportConfigRela;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 调查报告配置节点关联表 服务类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-01
 */
public interface ReportConfigRelaService extends IService<ReportConfigRela> {

  List<ReportConfigRela> selectListByReportId(String reportid);
}
