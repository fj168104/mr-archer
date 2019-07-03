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
 * 调查报告配置节点表
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-01
 */
@Data
@Accessors(chain = true)
@TableName("report_node")
public class ReportNode extends Model<ReportNode> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    /**
     * 节点标题
     */
    private String title;
    /**
     * 节点描述
     */
    private String nodedesc;
    /**
     * vue路径
     */
    private String vuepath;
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
    /**
     * 是否可用
     */
    private String isinuse;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
