package com.mr.archer.entity;

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
 * 财报数据基本信息表
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-25
 */
@Data
@Accessors(chain = true)
@TableName("fin_base_info")
public class FinBaseInfo extends Model<FinBaseInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    /**
     * 财报模板编号
     */
    private String modelid;
    /**
     * 客户编号
     */
    private String customerid;
    /**
     * 统计日期
     */
    private String findate;
    /**
     * 报表口径
     */
    private String scope;
    /**
     * 报表周期
     */
    private String period;
    /**
     * 报表币种
     */
    private String currency;
    /**
     * 报表单位
     */
    private String unit;
    /**
     * 是否审计
     */
    private String isaudit;
    /**
     * 审计意见
     */
    private String auditopinion;
    /**
     * 报表状态
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
