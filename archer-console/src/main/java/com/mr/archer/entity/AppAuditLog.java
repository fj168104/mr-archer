package com.mr.archer.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * app审核日志表
 * </p>
 *
 * @author jiang.feng
 * @since 2019-05-19
 */
@Data
@Accessors(chain = true)
@TableName("app_audit_log")
public class AppAuditLog extends Model<AppAuditLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 应用id
     */
    @TableField("app_id")
    private Long appId;
    /**
     * 审批操作人id
     */
    @TableField("user_id")
    private Long userId;
    /**
     * app审批的详细信息
     */
    private String info;
    /**
     * 审批id
     */
    @TableField("app_audit_id")
    private Long appAuditId;
    @TableField("create_time")
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
