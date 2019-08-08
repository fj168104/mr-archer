package com.mr.archer.controller;


import com.mr.archer.constant.SystemConstant;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * app审核日志表 前端控制器
 * </p>
 *
 * @author jiang.feng
 * @since 2019-05-19
 */
@RestController
@RequestMapping(SystemConstant.APP_CONTEXT + "/appAuditLog")
public class AppAuditLogController extends BaseController {

}

