package com.mr.archer.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.mr.archer.entity.FilelistTreeNodeRela;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 资料清单节点关联表 Mapper 接口
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-05
 */
public interface FilelistTreeNodeRelaMapper extends BaseMapper<FilelistTreeNodeRela> {

  List<FilelistTreeNodeRela> selectTreeNodeRelaList(Page<FilelistTreeNodeRela> page, String treeid, String name);
}
