package com.shiln.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by baojunhu on 2018/7/20.
 */

@SpringBootApplication
@ComponentScan
@MapperScan("com.shiln.order.mapper")
public class OrderApplication implements EmbeddedServletContainerCustomizer {

    /**
     * 程序启动入口
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        configurableEmbeddedServletContainer.setPort(8082);


    }

}

