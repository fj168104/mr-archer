package com.mr.archer.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 客户权限维护
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-04
 */
@Data
@Accessors(chain = true)
@TableName("customer_transfer")
public class CustomerTransfer extends Model<CustomerTransfer> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    /**
     * 权限类型
     */
    private String righttype;
    /**
     * 客户编号
     */
    private String customerid;
    /**
     * 接收人 
     */
    private String userid;
    /**
     * 转让申请日期 
     */
    private String applydate;
    /**
     * 转让确认日期
     */
    private String confirmdate;
    /**
     * 转让拒绝日期
     */
    private String refusedate;
    /**
     * 转移状态
     */
    private String status;
    /**
     * 创建人
     */
    private String createuser;
    /**
     * 创建时间
     */
    private String createtime;
    /**
     * 创建机构
     */
    private String createorg;
    /**
     * 更新人
     */
    private String updateuser;
    /**
     * 更新时间
     */
    private String updatetime;
    /**
     * 更新机构
     */
    private String updateorg;


    /**
     * 客户名称
     */
    @TableField(exist=false)
    private String customername;
    /**
     * 客户证件类型
     */
    @TableField(exist=false)
    private String certtype;
    /**
     * 客户证件号码
     */
    @TableField(exist=false)
    private String certid;
    /**
     * 接收人 名称
     */
    @TableField(exist=false)
    private String username;
    /**
     * 创建人名称
     */
    @TableField(exist=false)
    private String createusername;
    /**
     * 创建机构名称
     */
    @TableField(exist=false)
    private String createorgname;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
