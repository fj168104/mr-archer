package com.mr.archer.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mr.archer.dao.FilelistTreeNodeRelaMapper;
import com.mr.archer.entity.FilelistTreeNodeRela;
import com.mr.archer.service.FilelistTreeNodeRelaService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资料清单节点关联表 服务实现类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-05
 */
@Service
public class FilelistTreeNodeRelaServiceImpl extends ServiceImpl<FilelistTreeNodeRelaMapper, FilelistTreeNodeRela> implements FilelistTreeNodeRelaService {

  public Page<FilelistTreeNodeRela> selectTreeNodeRelaList(Page<FilelistTreeNodeRela> page, @Param("treeid") String treeid, @Param("name") String name) {
    return page.setRecords(baseMapper.selectTreeNodeRelaList(page, treeid, name));
  }

}
