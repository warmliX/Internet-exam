package com.zzkk.internet.dao;

import com.zzkk.internet.build.tables.Record;
import com.zzkk.internet.build.tables.records.RecordRecord;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author warmli
 */
@Repository
public class RecordDao {
    private final DSLContext dslContext;

    private final Record record = Record.RECORD;

    @Autowired
    public RecordDao(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public int selectNumOfExam(String uid) {
        return dslContext.selectCount()
            .from(record)
            .where(record.UID.eq(uid))
            .fetchOne()
            .value1();
    }

    public int selectNumOfPassExam(String uid) {
        return dslContext.selectCount()
            .from(record)
            .where(record.UID.eq(uid))
            .and(record.FRACTION.ge(60))
            .fetchOne()
            .value1();
    }

    public Result<RecordRecord> selectPassRecordByUid(String uid) {
        return dslContext.selectFrom(record)
            .where(record.UID.eq(uid))
            .and(record.FRACTION.ge(60))
            .fetch();
    }

    public Result<RecordRecord> selectUnpassRecordByUid(String uid) {
        return dslContext.selectFrom(record)
            .where(record.UID.eq(uid))
            .and(record.FRACTION.lt(60))
            .fetch();
    }
}
