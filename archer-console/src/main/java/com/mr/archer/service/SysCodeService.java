package com.mr.archer.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mr.archer.entity.SysCode;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 代码表 服务类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-16
 */
public interface SysCodeService extends IService<SysCode> {

  JSONObject queryCodeList(JSONArray aCodeList, JSONArray aTreeList);
}
