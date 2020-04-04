package com.zzkk.internet.biz.service.impl;

import com.zzkk.internet.biz.service.ExamInfoService;
import com.zzkk.internet.build.tables.records.RecordRecord;
import com.zzkk.internet.common.JedisUtil;
import com.zzkk.internet.common.TokenResolve;
import com.zzkk.internet.dao.RecordDao;
import com.zzkk.internet.dao.UserDao;
import com.zzkk.internet.pojo.Record;
import com.zzkk.internet.pojo.Result;
import com.zzkk.internet.pojo.StatisticsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author warmli
 */
@SuppressWarnings("Duplicates")
@Service
public class ExamInfoServiceImpl implements ExamInfoService {
    private final JedisUtil jedisUtil;

    private final TokenResolve resolve;

    private final RecordDao recordDao;

    private final UserDao userDao;

    @Autowired
    public ExamInfoServiceImpl(JedisUtil jedisUtil, TokenResolve resolve, RecordDao recordDao, UserDao userDao) {
        this.jedisUtil = jedisUtil;
        this.resolve = resolve;
        this.recordDao = recordDao;
        this.userDao = userDao;
    }

    @Override
    public Result<StatisticsInfo> statistics(String token) {
        String user = resolve.getUser(token);
        String uid = userDao.selectUidByUname(user);
        int sum = recordDao.selectNumOfExam(uid);
        int pass = recordDao.selectNumOfPassExam(uid);
        double passRate = sum == 0? 0 : (double) pass/(double) sum;
        long userNum = userDao.selectUserNum();
        passRate = ((double)Math.round(passRate*100)/100) * 100;
        System.out.println("passRate: "+passRate);
        return new Result<>(new StatisticsInfo(sum, pass, passRate, userNum));
    }

    @Override
    public Result<Set<Tuple>> ranking(String token) {
        String user = resolve.getUser(token);
        // 获取排名
        Long rank = jedisUtil.zRank(user);
        // 获取榜单总数
        Long card = jedisUtil.zCard();
        System.out.println("user:"+user);
        System.out.println("rank: "+rank);
        System.out.println("card: "+card);
        System.out.println(getTop(rank, card)+" "+getBottom(rank, card));
        Set<Tuple> sort = jedisUtil.zrangeWithScores(getTop(rank, card), getBottom(rank, card));
        sort.forEach(t -> System.out.println(t.getElement()+" "+t.getScore()));
        return new Result<>(sort);
    }

    @Override
    public Result<List<Record>> recordOfPass(String token) {
        String user = resolve.getUser(token);
        String uid = userDao.selectUidByUname(user);
        org.jooq.Result<RecordRecord> res =  recordDao.selectPassRecordByUid(uid);
        List<Record> passRecords = new ArrayList<>();
        res.forEach(v -> {
            passRecords.add(new Record(v.getRid(), v.getRid(), v.getFraction(), v.getBegintime(), v.getEndtime()));
        });
        return new Result<>(passRecords);
    }

    @Override
    public Result<List<Record>> recordOfUnpass(String token) {
        String user = resolve.getUser(token);
        String uid = userDao.selectUidByUname(user);
        org.jooq.Result<RecordRecord> res =  recordDao.selectUnpassRecordByUid(uid);
        List<Record> unPassRecords = new ArrayList<>();
        res.forEach(v -> {
            unPassRecords.add(new Record(v.getRid(), v.getRid(), v.getFraction(), v.getBegintime(), v.getEndtime()));
        });
        return new Result<>(unPassRecords);
    }

    private long getTop(long rank, long card){
        if (rank - 3 < 0)
            return 0;

        if (rank + 4 > card)
            return card - 7;

        return rank - 3;
    }

    private long getBottom(long rank, long card){
        if (rank + 4 > card)
            return card;

        if (rank - 3 < 0)
            return 2 * rank + 1;

        return rank + 4;
    }
}
