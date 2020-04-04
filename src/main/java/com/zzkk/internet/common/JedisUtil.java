package com.zzkk.internet.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.SetParams;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    public Long zSet(String user, double score) {
        Map<String, Double> sourceMember = new HashMap<>();
        try (Jedis jedis = pool.getResource()) {
            sourceMember.put(user, score);
            return jedis.zadd(Constant.RANKING, sourceMember);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Long.valueOf(0);
        }
    }

    public Double zAdd(String user, double score) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.zincrby(Constant.RANKING, score, user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Double.valueOf(0);
        }
    }

    public Long zRank(String user){
        try (Jedis jedis = pool.getResource()) {
            return jedis.zrank(Constant.RANKING, user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Set<Tuple> zrangeWithScores(long start, long stop) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.zrangeWithScores(Constant.RANKING, start, stop);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Long zCard(){
        try (Jedis jedis = pool.getResource()) {
            return jedis.zcard(Constant.RANKING);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
