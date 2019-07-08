package com.mr.archer.service;

import com.alibaba.fastjson.JSONArray;
import com.mr.archer.entity.FilelistConfig;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 资料清单配置表 服务类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-05
 */
public interface FilelistConfigService extends IService<FilelistConfig> {

  JSONArray getCodeList(String type);
}
