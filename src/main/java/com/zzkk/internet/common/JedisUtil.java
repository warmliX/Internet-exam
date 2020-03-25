package com.zzkk.internet.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

/**
 * @author warmli
 */
@Component
public class JedisUtil {
    private final JedisPool pool;

    @Autowired
    public JedisUtil(JedisPool pool) {
        this.pool = pool;
    }

    public String get(String key, int index){
        String value = null;
        try (Jedis jedis = pool.getResource()) {
            jedis.select(index);
            value = jedis.get(key);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return value;
    }

    public String set(String key, String value, int index, int ext){
        SetParams params = new SetParams();
        try (Jedis jedis = pool.getResource()) {
            jedis.select(index);
            // 设置过期时间（秒）
            params.ex(ext);
            return jedis.set(key, value, params);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "0";
        }
    }
}
