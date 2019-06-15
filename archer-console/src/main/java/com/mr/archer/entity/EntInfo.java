package com.mr.archer.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 企业客户基本信息
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-14
 */
@Data
@Accessors(chain = true)
@TableName("ent_info")
public class EntInfo extends Model<EntInfo> {

    /**
     * 客户编号
     */
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    /**
     * 工商营业执照号码
     */
    private String businessno;
    /**
     * 营业执照登记日
     */
    private String regbegindate;
    /**
     * 营业执照到期日
     */
    private String regenddate;
    /**
     * 企业类型
     */
    private String entkind;
    /**
     * 国标行业分类
     */
    private String industry;
    /**
     * 经营范围
     */
    private String scope;
    /**
     * 职工人数
     */
    private Integer workers;
    /**
     * 企业规模
     */
    private String scale;
    /**
     * 上市公司类型
     */
    private String stock;
    /**
     * 企业成立日期
     */
    private String funddate;
    /**
     * 注册资本币种
     */
    private String regcurrency;
    /**
     * 注册资本
     */
    private BigDecimal regcapital;
    /**
     * 实收资本币种
     */
    private String realcurrency;
    /**
     * 实收资本
     */
    private BigDecimal realcapital;
    /**
     * 注册地址
     */
    private String regaddress;
    /**
     * 注册地址邮政编码
     */
    private String regpostcode;
    /**
     * 所在国家(地区)
     */
    private String country;
    /**
     * 省份、直辖市、自治区
     */
    private String province;
    /**
     * 办公地址
     */
    private String office;
    /**
     * 公司网址
     */
    private String website;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 传真电话
     */
    private String fax;
    /**
     * 财务部联系电话
     */
    private String financetel;
    /**
     * 税务登记证号(国税)
     */
    private String statetaxno;
    /**
     * 税务登记证号(地税)
     */
    private String localtaxno;
    /**
     * 财务报表类型
     */
    private String financereport;
    /**
     * 基本帐户行
     */
    private String basebank;
    /**
     * 基本帐户号
     */
    private String baseaccount;
    /**
     * 我行开户行
     */
    private String mybank;
    /**
     * 我行开户帐号
     */
    private String myaccount;
    /**
     * 他行开户行
     */
    private String otherbank;
    /**
     * 他行开户帐号
     */
    private String otheraccount;
    /**
     * 在我行首次开立账户时间
     */
    private String firstaccountdate;
    /**
     * 与我行建立信贷关系时间
     */
    private String firstloandate;
    /**
     * 有无进出口经营权
     */
    private String istrade;
    /**
     * 是否担保公司
     */
    private String isguaranty;
    /**
     * 银监会小企业标志
     */
    private String issmall;
    /**
     * 信用等级评估模板名称
     */
    private String creditmodel;
    /**
     * 本行即期信用等级
     */
    private String creditlevel;
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
