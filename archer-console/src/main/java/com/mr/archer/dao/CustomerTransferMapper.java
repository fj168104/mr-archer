package com.mr.archer.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.mr.archer.entity.CustomerTransfer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 客户权限维护 Mapper 接口
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-04
 */
public interface CustomerTransferMapper extends BaseMapper<CustomerTransfer> {

  List<CustomerTransfer> selectListByCreateUser(Pagination page, @Param("userid") String userid, @Param("customername") String customername);

  List<CustomerTransfer> selectListByUser(Pagination page, @Param("userid") String userid, @Param("status") String status, @Param("customername") String customername);

  void updateStatusById(@Param("id") String id, @Param("status") String status, @Param("updateuser") String updateuser, @Param("updatetime") String updatetime, @Param("updateorg") String updateorg);
}
