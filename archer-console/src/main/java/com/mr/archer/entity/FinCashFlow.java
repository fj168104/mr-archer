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
 * 现金流量表
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-25
 */
@Data
@Accessors(chain = true)
@TableName("fin_cash_flow")
public class FinCashFlow extends Model<FinCashFlow> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    /**
     * 财报数据编号
     */
    private String baseinfoid;
    /**
     * 类型
     */
    private String type;
    /**
     * 配置编号
     */
    private String configid;
    /**
     * 销售产成品、商品、提供劳务收到的现金
     */
    private BigDecimal a1;
    /**
     * 收到其他与经营活动有关的现金
     */
    private BigDecimal a2;
    /**
     * 购买原材料、商品、接受劳务支付的现金
     */
    private BigDecimal a3;
    /**
     * 支付的职工薪酬
     */
    private BigDecimal a4;
    /**
     * 支付的税费
     */
    private BigDecimal a5;
    /**
     * 支付其他与经营活动有关的现金
     */
    private BigDecimal a6;
    /**
     * 经营活动产生的现金流量净额
     */
    private BigDecimal a7;
    /**
     * 收回短期投资、长期债券投资和长期股权投资收到的现金
     */
    private BigDecimal a8;
    /**
     * 取得投资收益收到的现金
     */
    private BigDecimal a9;
    /**
     * 处置固定资产、无形资产和其他非流动资产收回的现金净额
     */
    private BigDecimal a10;
    /**
     * 短期投资、长期债券投资和长期股权投资支付的现金
     */
    private BigDecimal a11;
    /**
     * 购建固定资产、无形资产和其他非流动资产支付的现金
     */
    private BigDecimal a12;
    /**
     * 投资活动所产生的现金流量净额
     */
    private BigDecimal a13;
    /**
     * 取得借款收到的现金
     */
    private BigDecimal a14;
    /**
     * 吸收投资者投资收到的现金
     */
    private BigDecimal a15;
    /**
     * 偿还借款本金支付的现金
     */
    private BigDecimal a16;
    /**
     * 偿还借款利息支付的现金
     */
    private BigDecimal a17;
    /**
     * 分配利润支付的现金
     */
    private BigDecimal a18;
    /**
     * 筹资活动产生的现金流量净额
     */
    private BigDecimal a19;
    /**
     * 四、现金净增加额
     */
    private BigDecimal a20;
    /**
     * 加：期初现金余额
     */
    private BigDecimal a21;
    /**
     * 五、期末现金余额
     */
    private BigDecimal a22;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
