package com.mr.archer.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 */
@Data
@Accessors(chain = true)
@TableName("sys_perm")
public class SysPerm extends Model<SysPerm> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("perm_value")
    private String permValue;    // 权限值 m:{permName}
    private Integer parent;  // 父节点权限值ID
    @TableField("perm_name")
    private String permName;   // 权限名称
    //private Integer ptype;  // 权限类型：1.菜单；2.按钮
    private Boolean leaf;   // 是否叶子节点

    @TableField("create_by")
    private String createBy;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_by")
    private String updateBy;
    @TableField("update_time")
    private Date updateTime;

    @TableField(exist = false)
    private List<SysPerm> children = new ArrayList<>();

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysPerm sysPerm = (SysPerm) o;
        return Objects.equals(permValue, sysPerm.permValue);
    }

}
