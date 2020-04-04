package com.zzkk.internet.biz.service;

import com.zzkk.internet.pojo.Record;
import com.zzkk.internet.pojo.Result;
import com.zzkk.internet.pojo.StatisticsInfo;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Set;

/**
 * @author warmli
 */
public interface ExamInfoService {
    Result<StatisticsInfo> statistics(
        String token
    );

    Result<Set<Tuple>> ranking(
        String token
    );

    Result<List<Record>> recordOfPass(
        String token
    );

    Result<List<Record>> recordOfUnpass(
        String token
    );
}
