package com.mr.archer.service.impl;

import com.mr.archer.entity.FinModelConfigRef;
import com.mr.archer.dao.FinModelConfigRefMapper;
import com.mr.archer.service.FinModelConfigRefService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 财报模板配置关联表 服务实现类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-21
 */
@Service
public class FinModelConfigRefServiceImpl extends ServiceImpl<FinModelConfigRefMapper, FinModelConfigRef> implements FinModelConfigRefService {

  public List<FinModelConfigRef> selectListByModelId(String modelid) {
    return baseMapper.selectListByModelId(modelid);
  }

  public void updateSortNoById(String id, String sortno) {
    baseMapper.updateSortNoById(id, sortno);
  }
}
