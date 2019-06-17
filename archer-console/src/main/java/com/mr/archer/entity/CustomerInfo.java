package com.mr.archer.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 */
@Data
@Accessors(chain = true)
@TableName("customer_info")
public class CustomerInfo extends Model<CustomerInfo> {

    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    private String name;
    private String type;
    private String certtype;
    private String certid;
    private String createuser;
    private String createtime;
    private String createorg;
    private String updateuser;
    private String updatetime;
    private String updateorg;

    @TableField(exist=false)
    private String createusername;
    @TableField(exist=false)
    private String createorgname;

    @TableField(exist=false)
    private String mainflag;
    @TableField(exist=false)
    private String modifyflag;
    @TableField(exist=false)
    private String viewflag;
    @TableField(exist=false)
    private String applyflag;

    @Override
    protected Serializable pkVal() {
        return id;
    }


}
