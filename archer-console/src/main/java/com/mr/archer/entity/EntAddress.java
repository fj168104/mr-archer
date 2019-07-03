package com.mr.archer.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
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
 * 客户地址信息
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-02
 */
@Data
@Accessors(chain = true)
@TableName("ent_address")
public class EntAddress extends Model<EntAddress> {

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
     *  地址类型 
     */
    private String addtype;
    /**
     *  地址所属国家/地区 
     */
    private String country;
    /**
     * 地址所属省/市
     */
    private String city;
    /**
     * 地址详情
     */
    private String address;
    /**
     * 邮政编码
     */
    private String zipcode;
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

    @TableField(exist=false)
    private String countryname;
    @TableField(exist=false)
    private String cityname;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
