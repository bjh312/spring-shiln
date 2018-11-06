package com.shiln.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.shiln.user.bean.AccountActionVo;
import com.shiln.user.bean.User;
//import com.shiln.user.dao.UserDao;
import com.shiln.user.es.ElasticsearchClientService;
import com.shiln.user.exception.BusinessException;
import com.shiln.user.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by baojunhu on 2018/7/20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Environment env;

//    @Autowired
//    private UserDao userDao;

    @Autowired
    private ElasticsearchClientService elasticsearchClientService;

    /**
     * 获取es连接
     * @return
     */
    private TransportClient getTransportClient(){
        TransportClient transportClient = elasticsearchClientService.getClient();
        return transportClient;
    }

    @RequestMapping("/getList")
    public List<User> getList(){
        String index = env.getProperty("elasticsearch.order.index");
        String type = env.getProperty("elasticsearch.order.type");
        //查询总数
        SearchResponse totalResponse = getTransportClient().prepareSearch(index).setTypes(type)
                .setQuery(QueryBuilders.boolQuery().filter(matchFlowQueryBuilder())).setSize(0).get();
        long total = totalResponse.getHits().getTotalHits();
        SearchResponse response = getTransportClient().prepareSearch(index).setTypes(type)
                .setQuery(QueryBuilders.boolQuery().filter(matchFlowQueryBuilder()))
                //.addSort("volume", SortOrder.DESC)
                //.setFrom(1).setSize(6)
                .execute().actionGet();
        SearchHits searchHits = response.getHits();
        AccountActionVo accountActionVo = null;
        for(SearchHit hit : searchHits){
            JSONObject object = JSONObject.parseObject(hit.getSourceAsString());
            accountActionVo = JSONObject.toJavaObject(object, AccountActionVo.class);
        }
        System.out.println(accountActionVo);
        List<User> list = userService.getList();
        return list.subList(0,3);
    }

    @RequestMapping("/addUser")
    public void addUser(@RequestBody @Valid User user,BindingResult result){
        //参数校验
        if(result.hasErrors()){
            String errorInfo= result.getFieldError().getDefaultMessage();
            throw new BusinessException("001",errorInfo);
        }
        userService.add(user);
        //写入es
        //userDao.save(user);
    }

    /**
     * 查询一个
     * @param
     * @return
     */
//    @RequestMapping("/selectOne")
//    public User getOne(@Param("id") String id){
//        return userDao.findOne(id);
//    }

    private BoolQueryBuilder matchFlowQueryBuilder(){
        //拼接filter查询
        BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
        //mustQuery.must(QueryBuilders.fuzzyQuery("canceled_at","153820008"));
        //mustQuery.must(QueryBuilders.termQuery("canceled_at","1538200051272"));
        //mustQuery.must(QueryBuilders.termQuery("id","15887"));
        return mustQuery;
    }

    //20181101
    @RequestMapping("/addUserEs")
    public void testIndexJson(){
        String source = "{\"address\":\"深圳市\",\"sex\":1,\"createTime\":\"20181101102721\",\"alias\":\"小明\",\"telephone\":\"132121212121\",\"userName\":\"呵呵\",\"userID\":\"121\",\"card\":\"420624199003123333\"}";
        String index = env.getProperty("elasticsearch.order.index");
        String type = env.getProperty("elasticsearch.order.type");
        IndexResponse indexResponse = getTransportClient().prepareIndex(index, type, "1111").setSource(source).get();
        System.out.println(indexResponse.getVersion());
    }

}
