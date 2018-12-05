package com.lwx.mystory.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 缓存单例类
 */
public class MapCache {

    private static final int DEFAULT_CACHES = 1024;

    private static final MapCache INS =  new MapCache();

    public static MapCache single(){
        return INS;
    }
    /**
     *缓存容器
     */
    private Map<String,CacheObject> cachePool;

    public MapCache(){
        this(DEFAULT_CACHES);
    }
    public MapCache(int cacheCount){
        cachePool = new ConcurrentHashMap<>(cacheCount);
    }

    /**
     * 获取缓存
     * @param key
     * @param <T>
     * @return
     */
    public <T> T get(String key){
        CacheObject cacheObject = cachePool.get(key);
        if(null != cacheObject){
            long cur = System.currentTimeMillis()/1000;
            if(cacheObject.getExpired() <=0 || cacheObject.getExpired() > cur ){
                Object result = cacheObject.getValue();
                return (T) result ;
            }
        }
        return null;
    }

    /**
     * 获取一个hash类型缓存
     * @param key
     * @param field
     * @param <T>
     * @return
     */
    public <T> T hget(String key,String field){
        key = key + ":" + field;
        return this.get(key);
    }

    public void set(String key,Object value){
        this.set(key,value,-1);
    }

    /**
     * 设置缓存并带过期时间
     * @param key  缓存key
     * @param value 缓存value
     * @param expired 过期时间 ，单位 ：S
     */
    public void set(String key,Object value,long expired){
        expired = expired > 0 ? System.currentTimeMillis()/1000 + expired : expired;
        CacheObject cacheObject = new CacheObject(key,value,expired);
        cachePool.put(key,cacheObject);
    }

    /**
     * 不带过期时间的hash缓存
     * @param key
     * @param field
     * @param value
     */
    public  void hset(String key,String field,Object value ){
        this.hset(key,field,value,-1);
    }

    /**
     * 设置一个hash缓存并带过期时间
     * @param key
     * @param field
     * @param value
     * @param expired
     */
    public void hset(String key,String field,Object value,long expired){
        key = key +":" + field ;
        //当前时间+过期时长
        expired = expired > 0 ? System.currentTimeMillis()/1000 + expired : expired;
        CacheObject cacheObject = new CacheObject(key,value,expired);
        cachePool.put(key,cacheObject);
    }

    /**
     * 根据key删除缓存
     * @param key
     */
    public void del(String key){
        cachePool.remove(key);
    }

    /**
     * 删除hash的缓存
     * @param key
     * @param field
     */
    public void hdel(String key,String field){
        key = key + ":" + field;
        cachePool.remove(key);
    }

    public void clean(){
        cachePool.clear();
    }

    /**
     *静态缓存对象
     */
    static class CacheObject{
        private String key;
        private Object value;
        private long expired;

        public CacheObject(String key,Object value,long expired){
            this.key = key;
            this.value = value;
            this.expired = expired;
        }

        public String getKey(){
            return key;
        }
        public Object getValue(){
            return value;
        }
        public long getExpired(){
            return expired;
        }
    }
}
