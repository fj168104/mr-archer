package com.mr.archer.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.mr.archer.entity.BusinessApply;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 业务申请 Mapper 接口
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-27
 */
public interface BusinessApplyMapper extends BaseMapper<BusinessApply> {

  List<BusinessApply> selectBusinessApplyListByUser(Pagination page, @Param("userid") String userid, @Param("customername") String customername);
}
