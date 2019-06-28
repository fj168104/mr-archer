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
 * 业务品种
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-27
 */
@Data
@Accessors(chain = true)
@TableName("business_type")
public class BusinessType extends Model<BusinessType> {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @TableId(value = "id", type = IdType.INPUT)
  private String id;
  /**
   * 所属分类
   */
  private String type;
  /**
   * 业务品种名称
   */
  private String name;
  /**
   * 还款方式
   */
  private String repaytype;
  /**
   * 基础年利率（%）
   */
  private BigDecimal rate;
  /**
   * 调查报告配置编号
   */
  private String reportid;
  /**
   * 资料清单配置编号
   */
  private String filelistid;
  /**
   * 审批流程编号
   */
  private String flowno;
  /**
   * 是否有效
   */
  private String isinuse;
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
