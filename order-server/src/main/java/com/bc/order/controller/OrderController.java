package com.bc.order.controller;

import com.alibaba.fastjson.JSON;
import com.bc.api.UserService;
import com.bc.dto.UserDto;
import com.bc.order.service.TestUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhankun
 * @date 2021-09-28 16:20:36
 */
@Slf4j
@RequestMapping(value = "order")
@RestController
public class OrderController {

    @DubboReference(retries = 3)
    private UserService userService;

    @Resource
    private TestUserService testUserService;

    @RequestMapping(value = "save/{userId}")
    public String saveOrder(@PathVariable(value = "userId") Long userId) {
        UserDto userDto = userService.getById(userId);
        log.info(JSON.toJSONString(userDto));
        return "success";
    }

    @RequestMapping(value = "async/{userId}")
    public UserDto async(@PathVariable Long userId) throws Exception {
        return userService.getByIdAsync(userId).get();
    }

    @RequestMapping(value = "retry")
    public String retry() {
        userService.testTimeOutRetry();
        return "success";
    }

    @RequestMapping(value = "error")
    public UserDto error() {
        return testUserService.testErrorRetry();
    }
}
