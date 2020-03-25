package com.zzkk.internet.biz.service;

import com.zzkk.internet.pojo.Result;
import org.springframework.stereotype.Service;

/**
 * @author warmli
 */
public interface SignService {

    Result<Boolean> signUp(
        String userName,
        String password,
        String email
    );

    Result<String> signIn(
        String userName,
        String password
    );

    Result<Boolean> sendVerifyCode(
        String userName
    );

    Result<Boolean> verifyCode(
        String code
    );
}
