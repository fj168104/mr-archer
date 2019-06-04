package com.mr.archer.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 应用审核表
 * </p>
 *
 * @author jiang.feng
 * @since 2019-05-19
 */
@Data
@Accessors(chain = true)
@TableName("app_audit")
public class AppAudit extends Model<AppAudit> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 应用id
     */
    @TableField("app_id")
    private Long appId;
    /**
     * 申请人的id
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;
    /**
     * 申请类型:0:用户注册,1:CHANNEL申请,2:QUEUE申请
     */
    private Integer type;
    /**
     * 预留参数1
     */
    private String param1;
    /**
     * 预留参数2
     */
    private String param2;
    /**
     * 预留参数3
     */
    private String param3;
    /**
     * 申请描述
     */
    private String info;
    /**
     * 0:等待审批; 1:审批通过; -1:驳回
     */
    private Integer status;
    /**
     * 驳回理由
     */
    @TableField("refuse_reason")
    private String refuseReason;
    @TableField("create_by")
    private String createBy;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_by")
    private String updateBy;
    @TableField("update_time")
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
