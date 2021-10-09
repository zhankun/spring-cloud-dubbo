package com.bc.api;

import com.bc.dto.UserDto;

import java.util.concurrent.CompletableFuture;

/**
 * @author zhankun
 * @date 2021-09-28 16:02:50
 */
public interface UserService {

    UserDto getById(Long userId);

    /**
     * 异步执行
     * @param userId
     * @return
     */
    CompletableFuture<UserDto> getByIdAsync(Long userId);

    /**
     * 超时重试测试
     */
    void testTimeOutRetry();

    /**
     * 错误重试测试
     */
    UserDto testErrorRetry();
}
