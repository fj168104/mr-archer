package com.mr.archer.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 代码表
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-16
 */
@Data
@Accessors(chain = true)
@TableName("sys_code")
public class SysCode extends Model<SysCode> {

    private static final long serialVersionUID = 1L;

    /**
     * 代码编号
     */
    private String id;
    /**
     * 代码项值
     */
    private String opt;
    /**
     * 代码项名称
     */
    private String name;
    /**
     * 排序号
     */
    private String sortno;
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
