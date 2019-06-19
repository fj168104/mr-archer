package com.mr.archer.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.mr.archer.entity.EntInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 企业客户基本信息 Mapper 接口
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-14
 */
public interface EntInfoMapper extends BaseMapper<EntInfo> {

  EntInfo selectEntInfoById(@Param("id") String id);
}
