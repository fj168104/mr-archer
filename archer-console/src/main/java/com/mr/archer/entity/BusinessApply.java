package com.mr.archer.entity;

import java.math.BigDecimal;
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
 * 业务申请
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-27
 */
@Data
@Accessors(chain = true)
@TableName("business_apply")
public class BusinessApply extends Model<BusinessApply> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    /**
     * 客户编号
     */
    private String customerid;
    /**
     * 客户名称
     */
    private String customername;
    /**
     * 业务品种编号
     */
    private String businesstype;
    /**
     * 币种
     */
    private String currency;
    /**
     * 金额
     */
    private BigDecimal money;
    /**
     * 期限单位
     */
    private String termunit;
    /**
     * 期限
     */
    private Integer term;
    /**
     * 是否循环
     */
    private String iscycle;
    /**
     * 主要担保和信用缓释方式
     */
    private String guaranttype;
    /**
     * 年利率（%）
     */
    private BigDecimal rate;
    /**
     * 授信用途
     */
    private String purpose;
    /**
     * 调查报告配置编号
     */
    private String reportid;
    /**
     * 资料清单配置编号
     */
    private String filelistid;
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

    @TableField(exist=false)
    private String createusername;
    @TableField(exist=false)
    private String createorgname;

    @TableField(exist=false)
    private String businesstypename;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
