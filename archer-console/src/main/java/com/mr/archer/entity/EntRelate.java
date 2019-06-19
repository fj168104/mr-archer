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
 * 上下游关联方情况
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-19
 */
@Data
@Accessors(chain = true)
@TableName("ent_relate")
public class EntRelate extends Model<EntRelate> {

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
     * 关联方客户类型
     */
    private String custtype;
    /**
     * 关联方名称
     */
    private String name;
    /**
     * 关联方证件类型
     */
    private String certtype;
    /**
     * 关联方证件编号
     */
    private String certid;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 关联关系
     */
    private String type;
    /**
     * 供应(销售)产品
     */
    private String product;
    /**
     * 供应(销售)额币种
     */
    private String currency;
    /**
     * 供应(销售)额
     */
    private BigDecimal money;
    /**
     * 供应(销售)比例
     */
    private BigDecimal percent;
    /**
     * 关系建立时间
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
