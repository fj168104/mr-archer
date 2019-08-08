package com.mr.archer.controller;


import com.mr.archer.constant.SystemConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.mr.archer.controller.BaseController;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 客户权限表 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-18
 */
@Slf4j
@RestController
@RequestMapping(SystemConstant.APP_CONTEXT + "/custbelong")
public class CustomerBelongController extends BaseController {

}

