package com.mr.archer.service;

import com.alibaba.fastjson.JSONArray;
import com.mr.archer.entity.FilelistTree;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 资料清单树图 服务类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-05
 */
public interface FilelistTreeService extends IService<FilelistTree> {

  JSONArray getTreeList(List<FilelistTree> treeList);
}
