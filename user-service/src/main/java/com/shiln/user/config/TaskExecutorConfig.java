package com.shiln.user.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


/** 
  * <p> 类描述： 
  * <p> 创建人: baojunhu
  * <p> 创建时间: 2018/8/30 16:41
  * <p> 版权申明：Huobi All Rights Reserved
 */
@Configuration
public class TaskExecutorConfig {

    /**
     * Set the ThreadPoolExecutor's core pool size.
     */
    private static final int CORE_POOL_SIZE = 10;

    /**
     * Set the ThreadPoolExecutor's maximum pool size.
     */
    private static final int MAX_POOL_SIZE = 50;

    /**
     * Set the capacity for the ThreadPoolExecutor's BlockingQueue.
     */
    private static final int QUEUE_CAPACITY = 1000;

    /**
     * Set the capacity for the ThreadPoolExecutor's KEEP_ALIVE.
     */
    private static final int KEEP_ALIVE = 2000;

    /**
     *   当一个任务通过execute(Runnable)方法欲添加到线程池时：
     1、 如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。
     2、 如果此时线程池中的数量等于 corePoolSize，但是缓冲队列 workQueue未满，那么任务被放入缓冲队列。
     3、如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maximumPoolSize，
     建新的线程来处理被添加的任务。
     4、 如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maximumPoolSize，
     那么通过 handler所指定的策略来处理此任务。也就是：处理任务的优先级为：核心线程corePoolSize、
     任务队列workQueue、最大线程 maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务。
     5、 当线程池中的线程数量大于 corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。
     这样，线程池可以动态的调整池中的线程数。
     * @return
     */
    @Bean(name ="taskExecutor" )
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);// 线程池维护线程的最少数量
        taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);// 线程池维护线程的最大数量
        taskExecutor.setQueueCapacity(QUEUE_CAPACITY);// 线程池所使用的缓冲队列
        taskExecutor.setThreadNamePrefix("SupplyChainExecutor-");
        taskExecutor.setKeepAliveSeconds(KEEP_ALIVE);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
        return taskExecutor;
    }
}
