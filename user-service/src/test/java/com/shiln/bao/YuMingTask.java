package com.shiln.bao;

import com.alibaba.fastjson.JSONObject;
import com.shiln.user.util.HttpClientUtils;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * <p> 类描述：
 * <p> 创建人: baojunhu
 * <p> 创建时间: 2018/9/10 10:04
 * <p> 版权申明：Huobi All Rights Reserved
 */
public class YuMingTask implements Callable<String> {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public YuMingTask(String url) {
        this.url = url;
    }

    @Override
    public String call(){
        String url = "https://checkapi.aliyun.com/check/checkdomain?domain="+this.url+".com&command&token=Y189b3b23a78aaf10779d24c59692f0ca&ua&currency&site&bid&_csrf_token&callback=jsonp_1536403350582_9145";
        try {
            Thread.sleep(300);
            String str = HttpClientUtils.get(url);
            int begin = str.indexOf("[");
            int end = str.indexOf("]");
            JSONObject obj = JSONObject.parseObject(str.substring(begin+1,end));
            String reString = obj.get("avail").toString();
            if (reString.equals("1")){
                return this.url;
            }else {
                return "";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
