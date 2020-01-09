package com.baizhi.hyh.cache;

import com.baizhi.hyh.util.MyWebAware;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;

public class MybatiesCache implements Cache {
    //必须有id属性
    private final String id;

    //必须有一个id属性的构造
    public MybatiesCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    //添加缓存
    @Override
    public void putObject(Object key, Object value) {
        //通过工具类获取rediesTemplate对象
        RedisTemplate redisTemplate = (RedisTemplate) MyWebAware.getByName("redisTemplate");
        redisTemplate.opsForHash().put(this.id, key.toString(), value);
    }

    //取数据
    @Override
    public Object getObject(Object key) {
        RedisTemplate redisTemplate = (RedisTemplate) MyWebAware.getByName("redisTemplate");
        Object o = redisTemplate.opsForHash().get(this.id, key.toString());
        return o;
    }

    @Override
    public Object removeObject(Object o) {
        return null;
    }

    //删除缓存
    @Override
    public void clear() {
        RedisTemplate redisTemplate = (RedisTemplate) MyWebAware.getByName("redisTemplate");
        redisTemplate.opsForHash().delete(this.id);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
