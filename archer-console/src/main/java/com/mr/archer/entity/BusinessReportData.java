package com.mr.archer.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 授信申请调查报告数据
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-01
 */
@Data
@Accessors(chain = true)
@TableName("business_report_data")
public class BusinessReportData extends Model<BusinessReportData> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    /**
     * 申请编号
     */
    private String applyid;
    /**
     * 调查报告配置编号
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
     * 节点数据
     */
    private byte[] nodedata;
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
