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
 * 调查报告配置节点关联表
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-01
 */
@Data
@Accessors(chain = true)
@TableName("report_config_rela")
public class ReportConfigRela extends Model<ReportConfigRela> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    /**
     * 报告编号
     */
    private String reportid;
    /**
     * 节点编号
     */
    private String nodeid;
    /**
     * 排序号
     */
    private String sortno;

    /**
     * 节点标题
     */
    @TableField(exist=false)
    private String title;
    /**
     * 节点描述
     */
    @TableField(exist=false)
    private String nodedesc;
    /**
     * vue路径
     */
    @TableField(exist=false)
    private String vuepath;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
