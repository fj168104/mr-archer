package com.mr.archer.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mr.archer.entity.SysPerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface SysPermMapper extends BaseMapper<SysPerm> {

    Set<SysPerm> getPermsByUserId(@Param("userId") Integer userId);

    Set<SysPerm> getPermsByRoleId(@Param("roleId") Integer roleId);

}
