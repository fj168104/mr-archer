package com.mr.archer.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.mr.archer.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUser> selectUserIncludeRoles(Pagination page, @Param("nick") String nick);

    String selectUsernameByToken(@Param("token") String token);

    List<SysUser> selectAllUserList(Page<SysUser> page, @Param("userid") String userid, @Param("nick") String nick);

}