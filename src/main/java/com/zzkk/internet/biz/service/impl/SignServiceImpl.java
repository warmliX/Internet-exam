package com.zzkk.internet.biz.service.impl;

import com.zzkk.internet.biz.service.SignService;
import com.zzkk.internet.common.JedisUtil;
import com.zzkk.internet.common.TokenResolve;
import com.zzkk.internet.dao.UserDao;
import com.zzkk.internet.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Random;
import java.util.UUID;

import static com.zzkk.internet.common.Constant.VERIFY_CODE_KEY;

/**
 * @author warmli
 */
@Service
public class SignServiceImpl implements SignService {
    private final UserDao userDao;

    private final JedisUtil jedisUtil;

    private final TokenResolve resolve;

    @Autowired
    public SignServiceImpl(UserDao userDao, JedisUtil jedisUtil, TokenResolve resolve) {
        this.userDao = userDao;
        this.jedisUtil = jedisUtil;
        this.resolve = resolve;
    }

    @Override
    public Result<Boolean> signUp(String userName, String password, String email) {
        System.out.println("sign up");
        if (userDao.selectByEmail(email).size() == 0)
            return new Result<>(false, Response.Status.BAD_REQUEST.getStatusCode(), "This email has already registered an account");

        if (userDao.selectByUname(userName).size() == 0)
            return new Result<>(false, Response.Status.BAD_REQUEST.getStatusCode(), "Account already exists");

        String uid = UUID.randomUUID().toString().replaceAll("-", "");
        userDao.insert(uid, userName, password, email);
        return new Result<>(true);
    }

    @Override
    public Result<String> signIn(String userName, String password) {
        String pWord = userDao.selectByUname(userName).getPword();
        System.out.println(pWord);
        if (!pWord.equals(password))
            return new Result<>("", Response.Status.BAD_REQUEST.getStatusCode(), "userName or password incorrect");

        String token = resolve.generateToken(userName);
        jedisUtil.set(token, token, 1, 30 *60);
        return new Result<>();
    }

    @Override
    public Result<Boolean> sendVerifyCode(String userName) {
        System.out.println(userDao.selectByUname(userName));
        if (userDao.selectByUname(userName).size() == 0)
            return new Result<>(false, Response.Status.BAD_REQUEST.getStatusCode(), "user name does not exist");

        Random random = new Random();
        String code = String.valueOf(random.nextInt(99999));
        jedisUtil.set(VERIFY_CODE_KEY + userName, code, Response.Status.OK.getStatusCode(), 10);
        return new Result<>(true);
    }

    @Override
    public Result<Boolean> verifyCode(String code) {
        return null;
    }
}
