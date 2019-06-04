package com.mr.archer.config;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mr.archer.entity.SysUser;
import com.mr.archer.entity.SysUserToken;
import com.mr.archer.service.SysUserService;
import com.mr.archer.service.SysUserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.UUID;

import static com.mr.archer.constant.SystemConstant.IS_STARTED;

@Slf4j
@Component
public class AfterServiceStarted implements ApplicationRunner {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysUserTokenService userTokenService;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        IS_STARTED = Boolean.TRUE;
        createAdminToken();
        log.info("服务器重启完成调用【AfterServiceStarted.run】方法");
    }

    /**
     * 首次启动初始化使用
     */
    private void createAdminToken(){
        SysUser admin = userService.getAdmin();
        SysUserToken token = userTokenService.selectOne(new EntityWrapper<SysUserToken>().eq("username", admin.getUsername()));
        if(Objects.isNull(token)){
            userTokenService.insert(new SysUserToken()
                    .setUsername(admin.getUsername())
                    .setToken(UUID.randomUUID().toString())
                    .setCreateBy(admin.getUsername())
                    .setUpdateBy(admin.getUsername())
                    .setCreateTime(new java.util.Date()));
        }
    }
}