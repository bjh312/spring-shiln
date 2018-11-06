package com.shiln.user.es;

import org.elasticsearch.client.transport.TransportClient;

/**
 * <p> 类描述：
 * <p> 创建人: baojunhu
 * <p> 创建时间: 2018/10/12 14:01
 * <p> 版权申明：Huobi All Rights Reserved
 */
public interface ElasticsearchClientService {

    TransportClient getClient();

    void close();
}
