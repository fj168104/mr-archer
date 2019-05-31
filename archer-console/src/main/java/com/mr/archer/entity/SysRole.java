package com.mr.archer.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.mr.archer.vo.AuthVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 */
@Data
@Accessors(chain = true)
@TableName("sys_role")
public class SysRole extends Model<SysRole> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("role_name")
    private String roleName;   // 角色名，用于显示
    @TableField("role_desc")
    private String roleDesc;   // 角色描述
    @TableField("role_value")
    private String roleValue;    // 角色值，用于权限判断
    @TableField("create_by")
    private String createBy;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_by")
    private String updateBy;
    @TableField("update_time")
    private Date updateTime;

    @TableField(exist = false)
    private Set<AuthVo> perms = new HashSet<>();    //用户所有权限值，用于做资源权限的判断

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysRole sysRole = (SysRole) o;
        return Objects.equals(roleDesc, sysRole.roleDesc);
    }

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
