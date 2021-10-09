package com.bc.common.service;

import com.bc.api.UserService;
import com.bc.common.config.ThreadPoolConfig;
import com.bc.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * @author zhankun
 * @date 2021-09-28 16:06:44
 */
@Slf4j
@DubboService
public class UserServiceImpl implements UserService {

    @Resource
    Environment environment;

    @Override
    public UserDto getById(Long userId) {
        // 使用的dubbo线程
        log.info("我的端口号是:{}, 我被调用了；线程名称是:{}", environment.getProperty("server.port"), Thread.currentThread().getName());
        return UserDto.builder().userId(userId).name("我的名字是:" + userId).build();
    }

    @Override
    public CompletableFuture<UserDto> getByIdAsync(Long userId) {
        // 这里是dubbo线程
        log.info("进入调用方法，当前线程名称是：{}", Thread.currentThread().getName());
        // 这里采用异步方式，使用的服务线程
        return CompletableFuture.supplyAsync(() -> {
            log.info("异步执行，我的端口号是:{}；线程名称是:{}", environment.getProperty("server.port"), Thread.currentThread().getName());
            return UserDto.builder().userId(userId).name("我是异步执行，我的名字是:" + userId).build();
        }, ThreadPoolConfig.ASYN_HANDLE);
    }

    @Override
    public void testTimeOutRetry() {
        log.info("我被调用了");
        // 制造超时异常
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserDto testErrorRetry() {
        log.info("我被调用了");
        // 模拟异常
        System.out.println(2 / 0);
        return UserDto.builder().userId(10000L).build();
    }
}
