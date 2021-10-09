package com.bc.order.service;

import com.bc.api.UserService;
import com.bc.dto.UserDto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @author zhankun
 * @date 2021-09-29 14:03:23
 */
@Slf4j
@Service
public class TestUserService {

    @DubboReference(retries = 3)
    private UserService userService;

    @HystrixCommand(fallbackMethod = "retry")
    public UserDto testErrorRetry() {
        return userService.testErrorRetry();
    }

    public UserDto retry() {
        log.info("服务调用方出错了，我来生成默认数据");
        return UserDto.builder().userId(-1L).name("默认数据").build();
    }
}
