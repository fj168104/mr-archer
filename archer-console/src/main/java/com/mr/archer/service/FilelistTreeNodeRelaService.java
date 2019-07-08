package com.mr.archer.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mr.archer.entity.FilelistTreeNodeRela;

/**
 * <p>
 * 资料清单节点关联表 服务类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-05
 */
public interface FilelistTreeNodeRelaService extends IService<FilelistTreeNodeRela> {

  Page<FilelistTreeNodeRela> selectTreeNodeRelaList(Page<FilelistTreeNodeRela> page, String treeid, String name);

}
