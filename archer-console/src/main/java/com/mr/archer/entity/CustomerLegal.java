package com.mr.archer.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.activerecord.Model;
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
 * 客户涉诉信息
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-19
 */
@Data
@Accessors(chain = true)
@TableName("customer_legal")
public class CustomerLegal extends Model<CustomerLegal> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    /**
     * 客户编号
     */
    private String customerid;
    /**
     * 起诉人名称
     */
    private String name;
    /**
     * 执行结果
     */
    private String result;
    /**
     * 币种
     */
    private String currency;
    /**
     * 判决执行金额
     */
    private BigDecimal money;
    /**
     * 判决执行日期
     */
    private String occurdate;
    /**
     * 被起诉原因
     */
    private String reason;
    /**
     * 是否有效
     */
    private String status;
    /**
     * 备注
     */
    private String remark;
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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
