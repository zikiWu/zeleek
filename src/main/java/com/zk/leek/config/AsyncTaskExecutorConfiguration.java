package com.gz.mxc.service.asynctaskpool.config;

import com.gz.mxc.service.asynctaskpool.executor.ExceptionHandlingAsyncTaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * The class Async config.
 * spring 线程池异步执行
 * @author bob
 */
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncTaskExecutorConfiguration implements AsyncConfigurer {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	@Bean(name = "taskExecutor")
	public Executor getAsyncExecutor() {
		log.debug("Creating Async Task Executor");
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		//线程池维护线程的最小数量.
		executor.setCorePoolSize(50);
		//线程池维护线程的最大数量.
		executor.setMaxPoolSize(100);
		//队列最大长度
		executor.setQueueCapacity(10000);
		//空闲线程的存活时间
		executor.setKeepAliveSeconds(3000);
		//线程池名称浅醉前缀
		executor.setThreadNamePrefix("ybt-task-executor-");
		return new ExceptionHandlingAsyncTaskExecutor(executor);
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}
}
