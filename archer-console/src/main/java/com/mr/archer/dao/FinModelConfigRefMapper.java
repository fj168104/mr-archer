package com.mr.archer.dao;

import com.mr.archer.entity.FinModelConfigRef;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 财报模板配置关联表 Mapper 接口
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-21
 */
public interface FinModelConfigRefMapper extends BaseMapper<FinModelConfigRef> {

  List<FinModelConfigRef> selectListByModelId(@Param("modelid") String modelid);

  void updateSortNoById(@Param("id")String id, @Param("sortno")String sortno);
}
