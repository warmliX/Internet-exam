package com.zzkk.internet.dao;

import com.zzkk.internet.build.tables.User;
import com.zzkk.internet.build.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author warmli
 */
@Repository
public class UserDao {
    private final DSLContext dslContext;

    private User user = User.USER;

    @Autowired
    public UserDao(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void insert(String uid, String userName, String password, String email){
        dslContext.insertInto(user,
            user.UID,
            user.UNAME,
            user.PWORD,
            user.EMAIL)
            .values(uid,
                userName,
                password,
                email)
            .execute();
    }

    public UserRecord selectByEmail(String email){
        return dslContext.selectFrom(user)
            .where(user.EMAIL.eq(email))
            .fetchOne();
    }

    public UserRecord selectByUname(String uname){
        return dslContext.selectFrom(user)
            .where(user.UNAME.eq(uname))
            .fetchOne();
    }

    public String selectUidByUname(String uname) {
        return dslContext.select(user.UID)
            .from(user)
            .where(user.UNAME.eq(uname))
            .fetchOne()
            .value1();
    }

    public long selectUserNum() {
        return dslContext.selectCount()
            .from(user)
            .fetchOne()
            .value1();
    }
}
