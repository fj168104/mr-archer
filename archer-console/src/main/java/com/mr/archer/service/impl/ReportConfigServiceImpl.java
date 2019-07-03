package com.mr.archer.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mr.archer.dao.ReportConfigMapper;
import com.mr.archer.entity.ReportConfig;
import com.mr.archer.service.ReportConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 调查报告配置表 服务实现类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-01
 */
@Service
public class ReportConfigServiceImpl extends ServiceImpl<ReportConfigMapper, ReportConfig> implements ReportConfigService {

  public JSONArray getCodeList() {
    JSONArray resultJson = new JSONArray();
    Wrapper<ReportConfig> rcParams = new EntityWrapper<>();
    rcParams.eq("isinuse", "1");
    List<ReportConfig> rcList = this.selectList(rcParams);
    for (int i = 0; i < rcList.size(); i++) {
      ReportConfig rc = rcList.get(i);
      JSONObject oReportConfig = new JSONObject();
      oReportConfig.put("label", rc.getName());
      oReportConfig.put("value", rc.getId());
      resultJson.add(oReportConfig);
    }

    return resultJson;
  }
}
