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
 * 资产负债表
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-25
 */
@Data
@Accessors(chain = true)
@TableName("fin_balance_sheet")
public class FinBalanceSheet extends Model<FinBalanceSheet> {

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
     * 货币资金
     */
    private BigDecimal a1;
    /**
     * 短期投资
     */
    private BigDecimal a2;
    /**
     * 应收票据
     */
    private BigDecimal a3;
    /**
     * 应收账款
     */
    private BigDecimal a4;
    /**
     * 预付账款
     */
    private BigDecimal a5;
    /**
     * 应收股利
     */
    private BigDecimal a6;
    /**
     * 应收利息
     */
    private BigDecimal a7;
    /**
     * 其他应收款
     */
    private BigDecimal a8;
    /**
     * 存货
     */
    private BigDecimal a9;
    /**
     * 其中：原材料
     */
    private BigDecimal a10;
    /**
     * 在建产品
     */
    private BigDecimal a11;
    /**
     * 库存商品
     */
    private BigDecimal a12;
    /**
     * 周转材料
     */
    private BigDecimal a13;
    /**
     * 其他流动资产
     */
    private BigDecimal a14;
    /**
     * 流动资产合计
     */
    private BigDecimal a15;
    /**
     * 长期债券投资
     */
    private BigDecimal a16;
    /**
     * 长期股权投资
     */
    private BigDecimal a17;
    /**
     * 固定资产原价
     */
    private BigDecimal a18;
    /**
     * 减：累计折旧
     */
    private BigDecimal a19;
    /**
     * 固定资产账面价值
     */
    private BigDecimal a20;
    /**
     * 在建工程
     */
    private BigDecimal a21;
    /**
     * 工程物资
     */
    private BigDecimal a22;
    /**
     * 固定资产清理
     */
    private BigDecimal a23;
    /**
     * 生产性生物资产
     */
    private BigDecimal a24;
    /**
     * 无形资产
     */
    private BigDecimal a25;
    /**
     * 开发支出
     */
    private BigDecimal a26;
    /**
     * 长期待摊费用
     */
    private BigDecimal a27;
    /**
     * 其他非流动资产
     */
    private BigDecimal a28;
    /**
     * 非流动资产合计
     */
    private BigDecimal a29;
    /**
     * 资产总计
     */
    private BigDecimal a30;
    /**
     * 短期借款
     */
    private BigDecimal a31;
    /**
     * 应付票据
     */
    private BigDecimal a32;
    /**
     * 应付帐款
     */
    private BigDecimal a33;
    /**
     * 预收帐款
     */
    private BigDecimal a34;
    /**
     * 应付职工薪酬
     */
    private BigDecimal a35;
    /**
     * 应交税费
     */
    private BigDecimal a36;
    /**
     * 应付利息
     */
    private BigDecimal a37;
    /**
     * 应付利润
     */
    private BigDecimal a38;
    /**
     * 其他应付款
     */
    private BigDecimal a39;
    /**
     * 其他流动负债
     */
    private BigDecimal a40;
    /**
     * 流动负债合计
     */
    private BigDecimal a41;
    /**
     * 长期借款
     */
    private BigDecimal a42;
    /**
     * 长期应付款
     */
    private BigDecimal a43;
    /**
     * 递延收益
     */
    private BigDecimal a44;
    /**
     * 其他非流动负债
     */
    private BigDecimal a45;
    /**
     * 非流动负债合计
     */
    private BigDecimal a46;
    /**
     * 负债合计
     */
    private BigDecimal a47;
    /**
     * 实收资本（或股本）
     */
    private BigDecimal a48;
    /**
     * 资本公积
     */
    private BigDecimal a49;
    /**
     * 盈余公积
     */
    private BigDecimal a50;
    /**
     * 未分配利润
     */
    private BigDecimal a51;
    /**
     * 所有者权益合计
     */
    private BigDecimal a52;
    /**
     * 负债和所有者权益总计
     */
    private BigDecimal a53;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
