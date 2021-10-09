package com.bc.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 线程池相关配置
 * 
 * @author shiyu
 *
 */
@Slf4j
@Component(value = "threadPoolConfig")
public class ThreadPoolConfig {

	/**
	 * 异步处理线程池
	 */
	public static final ThreadPoolTaskExecutor ASYN_HANDLE = new ThreadPoolTaskExecutor();

	@PostConstruct
	private void init() {
		int cpuNum = Runtime.getRuntime().availableProcessors();
		
		ASYN_HANDLE.setCorePoolSize(cpuNum * 4);
		ASYN_HANDLE.setMaxPoolSize(cpuNum * 4);
		ASYN_HANDLE.setKeepAliveSeconds(1000);
		ASYN_HANDLE.setQueueCapacity(10000);
		ASYN_HANDLE.setThreadNamePrefix("testServerThread-");
		ASYN_HANDLE.setRejectedExecutionHandler((r, executor) -> {
			log.error("ThreadPoolConfig加入任务被拒绝: {}", r);
		});
		ASYN_HANDLE.initialize();
	}

	@PreDestroy
	public void destroy() {
		ASYN_HANDLE.shutdown();
	}

}
