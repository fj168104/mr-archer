package com.mr.archer.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.mr.archer.entity.CustomerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerInfoMapper extends BaseMapper<CustomerInfo> {

  List<CustomerInfo> selectCustomerListByUser(Pagination page, @Param("userid") String userid, @Param("name") String name, @Param("certid") String certid);

  List<CustomerInfo> selectBusiCustomerListByUser(Pagination page, @Param("userid") String userid, @Param("name") String name, @Param("certid") String certid);

  List<CustomerInfo> selectCustomerListByOrg(Pagination page, @Param("userid") String userid, @Param("name") String name, @Param("certid") String certid);

}
