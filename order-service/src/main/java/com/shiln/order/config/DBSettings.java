package com.shiln.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by baojunhu on 2017/3/28.
 */
@Component
@ConfigurationProperties(prefix="datasource")
public class DBSettings {
    private String url;
    private String username;
    private String password;
}
