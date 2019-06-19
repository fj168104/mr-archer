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
 * 国标行业分类表
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-18
 */
@Data
@Accessors(chain = true)
@TableName("sys_industry")
public class SysIndustry extends Model<SysIndustry> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    /**
     * 一级分类
     */
    private String l1;
    /**
     * 二级分类
     */
    private String l2;
    /**
     * 三级分类
     */
    private String l3;
    /**
     * 四级分类
     */
    private String l4;
    /**
     * 名称
     */
    private String name;
    /**
     * 排序号
     */
    private String sortno;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
