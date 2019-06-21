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
 * 财报模板配置关联表
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-21
 */
@Data
@Accessors(chain = true)
@TableName("fin_model_config_ref")
public class FinModelConfigRef extends Model<FinModelConfigRef> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    /**
     * 模板编号
     */
    private String modelid;
    /**
     * 配置编号
     */
    private String configid;
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

    @TableField(exist=false)
    private String name;
    @TableField(exist=false)
    private String cols;
    @TableField(exist=false)
    private String findatatype;
    @TableField(exist=false)
    private String configdesc;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
