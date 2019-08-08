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
 * 资料清单数据记录
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-09
 */
@Data
@Accessors(chain = true)
@TableName("filelist_record")
public class FilelistRecord extends Model<FilelistRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    /**
     * 所属类型
     */
    private String belongtype;
    /**
     * 所属编号
     */
    private String belongid;
    /**
     * 配置编号
     */
    private String configid;
    /**
     * 树图编号
     */
    private String treeid;
    /**
     * 节点编号
     */
    private String nodeid;
    /**
     * 文件路径
     */
    private String fileurl;
    /**
     * 原文件名
     */
    private String filename;
    /**
     * 原文件扩展名
     */
    private String fileextname;
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
