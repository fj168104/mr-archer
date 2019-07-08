package com.mr.archer.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mr.archer.entity.FilelistConfig;
import com.mr.archer.dao.FilelistConfigMapper;
import com.mr.archer.service.FilelistConfigService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资料清单配置表 服务实现类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-05
 */
@Service
public class FilelistConfigServiceImpl extends ServiceImpl<FilelistConfigMapper, FilelistConfig> implements FilelistConfigService {


  public JSONArray getCodeList(String type) {
    Wrapper<FilelistConfig> queryParams = new EntityWrapper<>();
    queryParams.eq("type", type);
    List<FilelistConfig> list = this.selectList(queryParams);
    JSONArray codeList = new JSONArray();
    if (list != null && list.size() > 0) {
      for (int i = 0; i < list.size(); i++) {
        FilelistConfig fc = list.get(i);
        JSONObject code = new JSONObject();
        code.put("label", fc.getName());
        code.put("value", fc.getId());
        codeList.add(code);
      }
    }

    return codeList;
  }
}
