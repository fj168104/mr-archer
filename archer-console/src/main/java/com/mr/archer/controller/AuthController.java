package com.mr.archer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mr.archer.annotation.PermInfo;
import com.mr.archer.constant.Codes;
import com.mr.archer.entity.SysUser;
import com.mr.archer.exception.*;
import com.mr.archer.service.SysPermService;
import com.mr.archer.service.SysRoleService;
import com.mr.archer.service.SysUserService;
import com.mr.archer.service.SysUserTokenService;
import com.mr.archer.utils.LoginUtil;
import com.mr.archer.vo.AuthVo;
import com.mr.archer.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.mr.archer.constant.SystemConstant.*;

/**
 *
 */
@Slf4j
@PermInfo(value = "登录模块")
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController{

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysPermService permService;

    @Autowired
    private SysUserTokenService tokenService;

    /**
     * loginUrl映射到这里，这里直接抛出异常交给GlobalExceptionHandler来统一返回json信息，
     * 也可以在这里直接返回json，不过这样子就跟GlobalExceptionHandler中返回的json重复了。
     * @return
     */
    @RequestMapping("/401")
    public Json page401() {
        throw new UnauthenticatedException();
    }

    /**
     * 做unauthorizedUrl映射到这里。由于约定了url方式只做鉴权控制，不权限访问控制，
     *
     * @return
     */
    @RequestMapping("/403")
    public Json page403() {
        throw new UnauthorizedException();
    }

    /**
     * 登录成功跳转到这里，直接返回json。但是实际情况是在login方法中登录成功后返回json了。
     * @return
     */
    @RequestMapping("/index")
    public Json pageIndex() {
        return new Json("index",true,1,"index page",null);
    }

    /**
     * 登录接口，由于UserService中是模拟返回用户信息的，
     * 所以用户名随意,这里默认使用LDAP来做作为登录验证，在DEBUG模式下无需验证，直接成功
     *
     *
     * @param body
     * @return
     */
    @PostMapping("/login")
    public Json login(@RequestBody String body){

        String oper = "user login";
        log.info("{}, body: {}",oper,body);

        JSONObject json = JSON.parseObject(body);
        String username = json.getString("username");
        String password = json.getString("password");

        if (StringUtils.isEmpty(username)){
            return Json.fail(oper,"用户名不能为空");
        }
        if (StringUtils.isEmpty(password)){
            return Json.fail(oper,"密码不能为空");
        }

        try {

            if(!userService.isAdmin(username)){
                //登录
                SysUser user = userService.selectOne(new EntityWrapper<SysUser>().eq("username", username));
                if(Objects.isNull(user)) throw new UnknownAccountException();
                if(user.locked()) throw new LockedAccountException();
            }

            if(!LoginUtil.passportCheck(username, password)) throw new IncorrectCredentialsException();

            //保存token
            Map<String, String> tokenMap = generateToken(username);
            //返回登录用户的token给前台

            return Json.succ(oper, tokenMap);

        } catch ( UnknownAccountException uae ) {
            log.warn("用户帐号不正确");
            return Json.fail(oper,"用户帐号或密码不正确");

        } catch ( IncorrectCredentialsException ice ) {
            log.warn("用户密码不正确");
            return Json.fail(oper,"用户帐号或密码不正确");

        } catch ( LockedAccountException lae ) {
            log.warn("用户帐号被锁定");
            return Json.fail(oper,"用户帐号被锁定不可用");

        } catch ( Exception ae ) {
            log.warn("登录出错");
            return Json.fail(oper,"登录失败："+ae.getMessage());
        }
    }


    private Map<String, String> generateToken(String username){
        Map<String, String> map = Maps.newHashMap();
        map.put("token", UUID.randomUUID().toString());
        String oToken = tokenService.refreshToken(username, map.get("token"));
        if(StringUtils.isNotBlank(oToken)){
            //生成新的token
            log.warn("{} 已经登录, token=[{}], 更新为 [{}]", username, oToken, map.get("token"));
        }
        return map;
    }

    @PostMapping("/logout")
    public Json logout(){
        String oper = "user logout";
        log.info("{}",oper);
        tokenService.clearToken(getToken());
        removeToken();
        return new Json(oper);
    }

    @GetMapping("/info")
    public Json info(String token){
        String oper = "get user info";

        //登录
        SysUser user = userService.selectUserByToken(token);
        if (user==null){
            //告知前台，登录失效

            return Json.fail(oper,Codes.UNAUTHEN);
        }else{
            if(userService.isAdmin(user.getUsername())){
                user.setRoles(Sets.newHashSet(new AuthVo(SUPER_ADMIN_ROLE_NAME, SUPER_ADMIN_ROLE_VALUE)));
                user.setPerms(Sets.newHashSet(new AuthVo(SUPER_ADMIN_PERM_NAME, SUPER_ADMIN_PERM_VALUE)));
            }else {
                user.setRoles(roleService.getRolesByUserId(user.getId()));
                user.setPerms(permService.getPermsByUserId(user.getId()));
            }
            //返回登录用户的信息给前台，含用户的所有角色和权限

            return Json.succ(oper, user);
        }
    }

}
