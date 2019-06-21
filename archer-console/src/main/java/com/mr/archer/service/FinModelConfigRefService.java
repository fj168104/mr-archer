package com.mr.archer.service;

import com.baomidou.mybatisplus.service.IService;
import com.mr.archer.entity.FinModelConfigRef;

import java.util.List;

/**
 * <p>
 * 财报模板配置关联表 服务类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-21
 */
public interface FinModelConfigRefService extends IService<FinModelConfigRef> {

  List<FinModelConfigRef> selectListByModelId(String modelid);

  void updateSortNoById(String id, String sortno);
}
