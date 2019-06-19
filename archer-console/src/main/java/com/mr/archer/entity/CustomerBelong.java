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
 * 客户权限表
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-18
 */
@Data
@Accessors(chain = true)
@TableName("customer_belong")
public class CustomerBelong extends Model<CustomerBelong> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    /**
     * 用户编号
     */
    private String userid;
    /**
     * 客户编号
     */
    private String customerid;
    /**
     * 客户主办权
     */
    private String mainflag;
    /**
     * 信息维护权
     */
    private String modifyflag;
    /**
     * 信息查看权
     */
    private String viewflag;
    /**
     * 业务申办权
     */
    private String applyflag;
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
