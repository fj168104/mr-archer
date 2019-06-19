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
 * 法人代表家族成员信息
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-19
 */
@Data
@Accessors(chain = true)
@TableName("ent_member")
public class EntMember extends Model<EntMember> {

    private static final long serialVersionUID = 1L;

    /**
     * 成员编号
     */
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    /**
     * 客户编号
     */
    private String customerid;
    /**
     * 成员名称
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
     * 家族关系
     */
    private String relation;
    /**
     * 所在企业名称
     */
    private String company;
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
