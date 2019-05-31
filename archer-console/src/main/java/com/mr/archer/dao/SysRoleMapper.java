package com.mr.archer.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mr.archer.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> getRolesByUserId(@Param("userId") Integer userId);

    List<Integer> getRoleIdsByUserId(@Param("userId") Integer userId);

    Boolean checkRidsContainRval(@Param("rids") List<Integer> rids, @Param("roleValue") String roleValue);

    Boolean checkUidContainRval(@Param("uid") Integer uid, @Param("roleValue") String roleValue);

}
