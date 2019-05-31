package com.mr.archer.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;

/**
 *
 */
public class PageUtils {

    /**
     * 获取分页参数
     *
     * @param json
     * @return
     */
    public static <T> Page<T> getPageParam(JSONObject json) {
        int current = json.getIntValue("page");
        int size = json.getIntValue("limit");
        if (current == 0) current = 1;
        if (size == 0) size = 10;
        return new Page<T>(current, size);
    }

}
