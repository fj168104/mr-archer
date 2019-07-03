package com.mr.archer.dao;

import com.mr.archer.entity.ReportConfigRela;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 调查报告配置节点关联表 Mapper 接口
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-01
 */
public interface ReportConfigRelaMapper extends BaseMapper<ReportConfigRela> {

  List<ReportConfigRela> selectListByReportId(@Param("reportid")String reportid);
}
