package com.shiln.user.util;

/**
 * Created by IntelliJ IDEA.
 * User: baojunhu
 * Date: 2017/2/25
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 通用缓存工具类
 * @author lh
 * @version 3.0
 * @since 2016-6-22
 *
 */
@Component
public class CacheUtils {

    private static StringRedisTemplate stringRedisTemplate;

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        CacheUtils.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 删除缓存<br>
     * 根据key精确匹配删除
     * @param key
     */
    @SuppressWarnings("unchecked")
    public static void del(String... key){
        if(key!=null && key.length > 0){
            if(key.length == 1){
                stringRedisTemplate.delete(key[0]);
            }else{
                stringRedisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 删除缓存<br>
     * 根据key精确匹配删除
     * @param key
     */
    @SuppressWarnings("unchecked")
    public static void del(String key){
         stringRedisTemplate.delete(key);
    }

    /**
     * 批量删除<br>
     * （该操作会执行模糊查询，请尽量不要使用，以免影响性能或误删）
     * @param pattern
     */
    public static void batchDel(String... pattern){
        for (String kp : pattern) {
            stringRedisTemplate.delete(stringRedisTemplate.keys(kp + "*"));
        }
    }

    /**
     * 批量删除<br>
     * （该操作会执行模糊查询，请尽量不要使用，以免影响性能或误删）
     * @param pattern
     */
    public static void batchDel(String pattern){
        stringRedisTemplate.delete(stringRedisTemplate.keys(pattern + "*"));
    }

    /**
     * 取得缓存（int型）
     * @param key
     * @return
     */
    public static Integer getInt(String key){
        String value = stringRedisTemplate.boundValueOps(key).get();
        if(StringUtils.isNotBlank(value)){
            return Integer.valueOf(value);
        }
        return null;
    }

    /**
     * 取得缓存（字符串类型）
     * @param key
     * @return
     */
    public static String getStr(String key){
        return stringRedisTemplate.boundValueOps(key).get();
    }

    /**
     * 取得缓存（字符串类型）
     * @param key
     * @return
     */
    public static String getStr(String key, boolean retain){
        String value = stringRedisTemplate.boundValueOps(key).get();
        if(!retain){
            stringRedisTemplate.delete(key);
        }
        return value;
    }

    /**
     * 获取缓存<br>
     * 注：基本数据类型(Character除外)，请直接使用get(String key, Class<T> clazz)取值
     * @param key
     * @return
     */
    public static Object getObj(String key){
        return stringRedisTemplate.boundValueOps(key).get();
    }

    /**
     * 获取缓存<br>
     * 注：java 8种基本类型的数据请直接使用get(String key, Class<T> clazz)取值
     * @param key
     * @param retain	是否保留
     * @return
     */
    public static Object getObj(String key, boolean retain){
        Object obj = stringRedisTemplate.boundValueOps(key).get();
        if(!retain){
            stringRedisTemplate.delete(key);
        }
        return obj;
    }

    /**
     * 获取缓存<br>
     * 注：该方法暂不支持Character数据类型
     * @param key	key
     * @param clazz	类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Class<T> clazz) {
        return (T)stringRedisTemplate.boundValueOps(key).get();
    }

    /**
     * 获取缓存json对象<br>
     * @param key	key
     * @param clazz	类型
     * @return
     */
    public static <T> T getJson(String key, Class<T> clazz) {
        return JsonMapper.toObject(stringRedisTemplate.boundValueOps(key).get(), clazz);
    }

    /**
     * 将value对象写入缓存
     * @param key
     * @param value
     */
    public static void set(String key,String value){
       stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 将value对象写入缓存
     * @param key
     * @param value
     */
    public static void set(String key,String value,Long time){
        stringRedisTemplate.opsForValue().set(key, value);
        if(time> 0){
            stringRedisTemplate.expire(key, time, TimeUnit.DAYS);
        }
    }

    /**
     * 将value对象写入缓存
     * @param key
     * @param value
     */
    public static void set(String key,String value,Long time,TimeUnit unit){
        stringRedisTemplate.opsForValue().set(key, value);
        if(time> 0){
            stringRedisTemplate.expire(key, time, unit);
        }
    }

    /**
     * 将value对象以JSON格式写入缓存
     * @param key
     * @param value
     * @param time 失效时间(秒)
     */
    public static void setJson(String key,Object value,long time){
        stringRedisTemplate.opsForValue().set(key, JsonMapper.toJsonString(value));
        if(time> 0){
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 更新key对象field的值
     * @param key	缓存key
     * @param field	缓存对象field
     * @param value	缓存对象field值
     */
    public static void setJsonField(String key, String field, String value){
        JSONObject obj = JSON.parseObject(stringRedisTemplate.boundValueOps(key).get());
        obj.put(field, value);
        stringRedisTemplate.opsForValue().set(key, obj.toJSONString());
    }


    /**
     * 递减操作
     * @param key
     * @param by
     * @return
     */
    public static double decr(String key, double by){
        return stringRedisTemplate.opsForValue().increment(key, -by);
    }

    /**
     * 递增操作
     * @param key
     * @param by
     * @return
     */
    public static double incr(String key, double by){
        return stringRedisTemplate.opsForValue().increment(key, by);
    }

    /**
     * 获取double类型值
     * @param key
     * @return
     */
    public static double getDouble(String key) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if(StringUtils.isNotBlank(value)){
            return Double.valueOf(value);
        }
        return 0d;
    }

    /**
     * 设置double类型值
     * @param key
     * @param value
     * @param time 失效时间(秒)
     */
    public static void setDouble(String key, double value, long time) {
        stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
        if(time > 0){
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 设置double类型值
     * @param key
     * @param value
     * @param time 失效时间(秒)
     */
    public static void setInt(String key, int value, long time) {
        stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
        if(time > 0){
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 将map写入缓存
     * @param key
     * @param map
     * @param time 失效时间(秒)
     */
    public static <T> void setMap(String key, Map<String, T> map, long time){
        stringRedisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 将map写入缓存
     * @param key
     * @param time 失效时间(秒)
     */
    @SuppressWarnings("unchecked")
    public static <T> void setMap(String key, T obj, long time){
        Map<String, String> map = (Map<String, String>)JsonMapper.parseObject(obj, Map.class);
        stringRedisTemplate.opsForHash().putAll(key, map);
    }



    /**
     * 向key对应的map中添加缓存对象
     * @param key
     * @param map
     */
    public static <T> void addMap(String key, Map<String, T> map){
        stringRedisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 向key对应的map中添加缓存对象
     * @param key	cache对象key
     * @param field map对应的key
     * @param value 	值
     */
    public static void addMap(String key, String field, String value){
        stringRedisTemplate.opsForHash().put(key, field, value);
    }

    public static void delMap(String key, String field){
        stringRedisTemplate.opsForHash().delete(key, field);
    }

    /**
     * 向key对应的map中添加缓存对象
     * @param key	cache对象key
     * @param field map对应的key
     * @param obj 	对象
     */
    public static <T> void addMap(String key, String field, T obj){
        stringRedisTemplate.opsForHash().put(key, field, obj);
    }

    /**
     * 向key对应的map中添加缓存对象
     * @param key	cache对象key
     * @param field map对应的key
     * @param obj 	对象
     */
    public static  void addMap(String key, String field, Long obj){
        stringRedisTemplate.opsForHash().put(key,field,obj);
    }


    /**
     * 获取map缓存
     * @param key
     * @param clazz
     * @return
     */
    public static <T> Map<String, T> mget(String key, Class<T> clazz){
        BoundHashOperations<String, String, T> boundHashOperations = stringRedisTemplate.boundHashOps(key);
        return boundHashOperations.entries();
    }

    /**
     * 获取map缓存
     * @param key
     * @param clazz
     * @return
     */
    public static <T> T getMap(String key, Class<T> clazz){
        BoundHashOperations<String, String, String> boundHashOperations = stringRedisTemplate.boundHashOps(key);
        Map<String, String> map = boundHashOperations.entries();
        return JsonMapper.parseObject(map, clazz);
    }

    /**
     * 获取map缓存中的某个对象
     * @param key
     * @param field
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getMapField(String key, String field, Class<T> clazz){
        return (T)stringRedisTemplate.boundHashOps(key).get(field);
    }

    /**
     * 删除map中的某个对象
     * @author lh
     * @date 2016年8月10日
     * @param key	map对应的key
     * @param field	map中该对象的key
     */
    public void delMapField(String key, String... field){
        BoundHashOperations<String, String, ?> boundHashOperations = stringRedisTemplate.boundHashOps(key);
        boundHashOperations.delete(field);
    }

    /**
     * 指定缓存的失效时间
     *
     * @author FangJun
     * @date 2016年8月14日
     * @param key 缓存KEY
     * @param time 失效时间(秒)
     */
    public static void expire(String key, long time) {
        if(time > 0){
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    public static <T> List<T> parseJsonToList(String jsonString, Class<T> clazz){
        return JSON.parseArray(jsonString,clazz);
    }

    public static String parseListToJson(List list){
        return JSON.toJSONString(list,true);
    }

    /**
     * 添加set
     * @param key
     * @param value
     */
    public static void sadd(String key, String... value) {
        stringRedisTemplate.boundSetOps(key).add(value);
    }

    /**
     * 删除set集合中的对象
     * @param key
     * @param value
     */
    public static void srem(String key, String... value) {
        stringRedisTemplate.boundSetOps(key).remove(value);
    }

    /**
     * set重命名
     * @param oldkey
     * @param newkey
     */
    public static void srename(String oldkey, String newkey){
        stringRedisTemplate.boundSetOps(oldkey).rename(newkey);
    }

    /**
     * 短信缓存
     * @author fxl
     * @date 2016年9月11日
     * @param key
     * @param value
     * @param time
     */
    public static void setIntForPhone(String key,Object value,int time){
        stringRedisTemplate.opsForValue().set(key, JsonMapper.toJsonString(value));
        if(time > 0){
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 模糊查询keys
     * @param pattern
     * @return
     */
    public static Set<String> keys(String pattern){
        return stringRedisTemplate.keys(pattern);
    }

    /**
     * 判断key是否存在
     * @param key
     * @param field
     * @return
     */
    public static boolean keyExists(String key,String field){
        return stringRedisTemplate.opsForHash().hasKey(key,field);
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return true 存在
     */
    public static boolean exists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 递增操作
     * @param key
     * @param by
     * @return
     */
    public static long increment(String key,String field, long by){
        return stringRedisTemplate.opsForHash().increment(key,field,by);
    }

}

