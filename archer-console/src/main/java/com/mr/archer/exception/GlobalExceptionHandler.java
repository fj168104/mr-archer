package com.mr.archer.exception;

import com.mr.archer.constant.Codes;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 统一捕捉异常，返回给前台一个json信息，前台根据这个信息显示对应的提示，或者做页面的跳转。
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //不满足@RequiresGuest注解时抛出的异常信息
    private static final String GUEST_ONLY = "Attempting to perform a guest-only operation";


    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public Json handleAuthorizationException(AuthorizationException e) {
        String eName = e.getClass().getSimpleName();
        log.error("Authorization check执行出错：{}",eName);
        return new Json(eName, false, Codes.UNAUTHZ, "鉴权或授权过程出错", null);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseBody
    public Json page401(UnauthenticatedException e) {
        String eMsg = e.getMessage();
        if (StringUtils.startsWithIgnoreCase(eMsg,GUEST_ONLY)){
            return new Json("Unauthenticated", false, Codes.UNAUTHEN, "只允许游客访问，若您已登录，请先退出登录", e.getMessage());
        }else{
            return new Json("Unauthenticated", false, Codes.UNAUTHEN, "用户未登录", e.getMessage());
        }
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public Json page403(UnauthenticatedException e) {
        return new Json("Unauthorized", false, Codes.UNAUTHZ, "用户没有访问权限", e.getMessage());
    }

    @ExceptionHandler(ArcherBusinessException.class)
    @ResponseBody
    public Json bizError(ArcherBusinessException e) {
        log.error(e.getMessage(), e);
        return new Json("bizError", false, Codes.BIZ_ERR, "业务处理异常", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Json serverError(Exception e) {
        log.error(e.getMessage(), e);
        return new Json("serverError", false, Codes.SERVER_ERR, "服务器异常", e.getMessage());
    }

}