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
import java.util.*;

/**
 *
 */
@Data
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser extends Model<SysUser> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;   // 登录名，不可改
    private String nick;    // 用户昵称，可改
    //private String pwd;     // 已加密的登录密码
    //private String salt;    // 加密盐值
    private String mail;
    private String avatar = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
    private String phone;
    private Integer lock;   // 是否锁定

    @TableField("create_by")
    private String createBy;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_by")
    private String updateBy;
    @TableField("update_time")
    private Date updateTime;

    @TableField(exist = false)
    private List<SysRole> roleList = new ArrayList<>();    //用户所有角色值，在管理后台显示用户的角色
    @TableField(exist = false)
    private Set<AuthVo> roles = new HashSet<>();    //用户所有角色值，用于做角色权限的判断
    @TableField(exist = false)
    private Set<AuthVo> perms = new HashSet<>();    //用户所有权限值，用于做资源权限的判断

    @Override
    protected Serializable pkVal() {
        return id;
    }

    public Boolean locked(){
        return lock == 1;
    }
}
