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
 * 财报字段配置表
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-20
 */
@Data
@Accessors(chain = true)
@TableName("fin_config_col")
public class FinConfigCol extends Model<FinConfigCol> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    /**
     * 财报配置编号
     */
    private String configid;
    /**
     * 所属分栏
     */
    private Integer col;
    /**
     * 字段名称
     */
    private String name;
    /**
     * 映射表名
     */
    private String tablename;
    /**
     * 计算公式
     */
    private String formulatxt;
    /**
     * 计算公式排序号
     */
    private String formulasortno;
    /**
     * 映射字段名
     */
    private String colname;
    /**
     * 是否可修改
     */
    private String isedit;
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
