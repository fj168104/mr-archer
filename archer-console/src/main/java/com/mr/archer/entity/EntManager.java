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
 * 客户高管信息
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-19
 */
@Data
@Accessors(chain = true)
@TableName("ent_manager")
public class EntManager extends Model<EntManager> {

    private static final long serialVersionUID = 1L;

    /**
     * 高管编号
     */
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    /**
     * 客户编号
     */
    private String customerid;
    /**
     * 高管名称
     */
    private String name;
    /**
     * 证件类型
     */
    private String certtype;
    /**
     * 证件编号
     */
    private String certid;
    /**
     * 性别
     */
    private String sex;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 学历
     */
    private String education;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 担任职务
     */
    private String duty;
    /**
     * 担任该职务时间
     */
    private String dutytime;
    /**
     * 相关行业从业年限
     */
    private Integer workyears;
    /**
     * 持股比例(%)
     */
    private BigDecimal stock;
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
