package com.mr.archer.config;

import com.mr.archer.constant.SystemConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AfterServiceStarted implements ApplicationRunner {
 
    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        SystemConstant.IS_STARTED = Boolean.TRUE;
        log.info("服务器重启完成调用【AfterServiceStarted.run】方法");
    }
}