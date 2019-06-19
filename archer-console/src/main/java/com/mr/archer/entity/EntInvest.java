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
 * 对外股权投资情况
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-19
 */
@Data
@Accessors(chain = true)
@TableName("ent_invest")
public class EntInvest extends Model<EntInvest> {

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
     * 投向企业客户类型
     */
    private String enttype;
    /**
     * 投向企业名称
     */
    private String name;
    /**
     * 投向企业证件类型
     */
    private String certtype;
    /**
     * 投向企业证件编号
     */
    private String certid;
    /**
     * 投资方式
     */
    private String type;
    /**
     * 出资币种
     */
    private String currency;
    /**
     * 应出资金额
     */
    private BigDecimal money;
    /**
     * 实际投资金额
     */
    private BigDecimal realmoney;
    /**
     * 出资比例(%)
     */
    private BigDecimal percent;
    /**
     * 投资日期
     */
    private String occurdate;
    /**
     * 是否有效
     */
    private String isinuse;
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
