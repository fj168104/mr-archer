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
 * 资料清单节点关联表
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-05
 */
@Data
@Accessors(chain = true)
@TableName("filelist_tree_node_rela")
public class FilelistTreeNodeRela extends Model<FilelistTreeNodeRela> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    /**
     * 树图编号
     */
    private String treeid;
    /**
     * 节点编号
     */
    private String nodeid;
    /**
     * 触发事件
     */
    private String event;
    /**
     * 排序号
     */
    private String sortno;
    /**
     * 是否必输
     */
    private String isrequired;
    /**
     * 是否可用
     */
    private String isinuse;
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
    private String name;
    @TableField(exist=false)
    private String filetype;
    @TableField(exist=false)
    private Integer count;
    @TableField(exist=false)
    private String nodeisinuse;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
