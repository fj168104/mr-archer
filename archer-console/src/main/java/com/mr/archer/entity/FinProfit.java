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
 * 利润表
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-25
 */
@Data
@Accessors(chain = true)
@TableName("fin_profit")
public class FinProfit extends Model<FinProfit> {

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
     * 一、营业收入
     */
    private BigDecimal a1;
    /**
     * 减：营业成本
     */
    private BigDecimal a2;
    /**
     * 税金及附加
     */
    private BigDecimal a3;
    /**
     * 其中：消费税
     */
    private BigDecimal a4;
    /**
     * 营业税
     */
    private BigDecimal a5;
    /**
     * 城市维护建设税
     */
    private BigDecimal a6;
    /**
     * 资源税
     */
    private BigDecimal a7;
    /**
     * 土地增值税
     */
    private BigDecimal a8;
    /**
     * 城镇土地使用税、房产税、车船税、印花税
     */
    private BigDecimal a9;
    /**
     * 教育费附加、矿产资源补偿费、排污费
     */
    private BigDecimal a10;
    /**
     * 销售费用
     */
    private BigDecimal a11;
    /**
     * 其中：商品维修费
     */
    private BigDecimal a12;
    /**
     * 广告费和业务宣传费
     */
    private BigDecimal a13;
    /**
     * 管理费用
     */
    private BigDecimal a14;
    /**
     * 其中：开办费
     */
    private BigDecimal a15;
    /**
     * 业务招待费
     */
    private BigDecimal a16;
    /**
     * 研究费用
     */
    private BigDecimal a17;
    /**
     * 财务费用
     */
    private BigDecimal a18;
    /**
     * 其中：利息费用（收入以“-”号填列）
     */
    private BigDecimal a19;
    /**
     * 加：投资收益（损失以“-”号填列）
     */
    private BigDecimal a20;
    /**
     * 二、营业利润（亏损以“-”号填列）
     */
    private BigDecimal a21;
    /**
     * 加：营业外收入
     */
    private BigDecimal a22;
    /**
     * 其中：政府补助
     */
    private BigDecimal a23;
    /**
     * 减：营业外支出
     */
    private BigDecimal a24;
    /**
     * 其中：坏账损失
     */
    private BigDecimal a25;
    /**
     * 无法收回的长期债券投资损失
     */
    private BigDecimal a26;
    /**
     * 无法收回的长期股权投资损失
     */
    private BigDecimal a27;
    /**
     * 自然灾害等不可抗力因素造成的损失
     */
    private BigDecimal a28;
    /**
     * 税收滞纳金
     */
    private BigDecimal a29;
    /**
     * 三、利润总额（亏损总额以“-”号填列）
     */
    private BigDecimal a30;
    /**
     * 减：所得税费用
     */
    private BigDecimal a31;
    /**
     * 四、净利润（净亏损以“-”号填列）
     */
    private BigDecimal a32;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
