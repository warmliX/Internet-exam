package com.zzkk.internet.api.impl;

import com.zzkk.internet.api.ExamInfoResource;
import com.zzkk.internet.biz.service.ExamInfoService;
import com.zzkk.internet.common.annotation.RestResource;
import com.zzkk.internet.pojo.Record;
import com.zzkk.internet.pojo.Result;
import com.zzkk.internet.pojo.StatisticsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Set;

/**
 * @author warmli
 */
@RestResource
public class ExamInfoResourceImpl implements ExamInfoResource {
    private ExamInfoService examInfoService;

    @Autowired
    public ExamInfoResourceImpl(ExamInfoService examInfoService){
        this.examInfoService = examInfoService;
    }

    @Override
    public Result<StatisticsInfo> statistics(String token) {
        return examInfoService.statistics(token);
    }

    @Override
    public Result<Set<Tuple>> ranking(String token) {
        return examInfoService.ranking(token);
    }

    @Override
    public Result<List<Record>> recordOfPass(String token) {
        return examInfoService.recordOfPass(token);
    }

    @Override
    public Result<List<Record>> recordOfUnpass(String token) {
        return examInfoService.recordOfUnpass(token);
    }
}
