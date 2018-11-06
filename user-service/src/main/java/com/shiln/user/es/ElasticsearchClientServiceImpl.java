package com.shiln.user.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> 类描述：
 * <p> 创建人: baojunhu
 * <p> 创建时间: 2018/10/12 14:02
 * <p> 版权申明：Huobi All Rights Reserved
 */

@Configuration
public class ElasticsearchClientServiceImpl implements ElasticsearchClientService{

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchClientServiceImpl.class);

    private TransportClient transportClient;

    @Autowired
    private Environment configure;

    @Value("${elasticsearch.cluster-nodes}")
    private  String clusterNodes;

    @Value("${elasticsearch.cluster-name}")
    private  String clusterName;

    @Override
    public TransportClient getClient() {
        if(null != transportClient){
            return transportClient;
        }
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        List<Map<String,String>> addressList = parseClusterNode();
        try {
            Settings settings = Settings.builder().put("cluster.name", clusterName)
                    .put("client.transport.sniff", true).build();
            transportClient = new PreBuiltTransportClient(settings);
            for(Map<String,String> address : addressList){
                transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(address.get("host")), Integer.valueOf(address.get("port"))));
            }
            logger.info("elasticsearch transportClient 连接成功");
            System.out.println("elasticsearch transportClient 连接成功");
        }catch (Exception e){
            logger.info("elasticsearch transportClient 连接失败");
            e.printStackTrace();
        }
        return transportClient;
    }




    @Override
    public void close() {
        if(transportClient!=null){
            transportClient.close();
        }
    }


    private List<Map<String,String>> parseClusterNode(){
        List<Map<String,String>> addressList = new ArrayList<>();
        String[] hosts = clusterNodes.split(",");
        for(int i=0;i<hosts.length;i++){
            String[] hostPort = hosts[i].split(":");
            Map<String,String> map = new HashMap<>();
            map.put("host",hostPort[0]);
            map.put("port",hostPort[1]);
            addressList.add(map);
        }
        return addressList;
    }

    public static void main(String[] args) {
        ElasticsearchClientServiceImpl elasticsearchClientService = new ElasticsearchClientServiceImpl();
        elasticsearchClientService.getClient();
    }
}
