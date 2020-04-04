package com.zzkk.internet.api.impl;

import com.zzkk.internet.api.SignResource;
import com.zzkk.internet.biz.service.SignService;
import com.zzkk.internet.common.annotation.RestResource;
import com.zzkk.internet.pojo.Result;
import com.zzkk.internet.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Response;

/**
 * @author warmli
 */
@RestResource
public class SignResourceImpl implements SignResource {
    private final SignService signService;

    @Autowired
    public SignResourceImpl(SignService signService) {
        this.signService = signService;
    }

    @Override
    public Result<Boolean> signUp(User user) {
        if (checkUser(user))
            return new Result<>(false, Response.Status.BAD_REQUEST.getStatusCode(), "Registration information cannot be empty");

        return signService.signUp(user.getUname(), user.getPword(), user.getEmail());
    }

    @Override
    public Result<String> signIn(User user) {
        System.out.println("sign in");

        System.out.println(user.getUname()+" "+user.getPword());
        if (StringUtils.isBlank(user.getUname()) ||
                StringUtils.isBlank(user.getPword()))
            return new Result<>("", Response.Status.BAD_REQUEST.getStatusCode(), "Login information cannot be empty");

        return signService.signIn(user.getUname(), user.getPword());
    }

    @Override
    public Result<Boolean> sendVerifyCode(User user) {
        if (StringUtils.isBlank(user.getUname()))
            return new Result<>(false, Response.Status.BAD_REQUEST.getStatusCode(), "email cannot be empty");

        return signService.sendVerifyCode(user.getUname());
    }

    @Override
    public Result<Boolean> verifyCode(String code) {
        if (StringUtils.isBlank(code))
            return new Result<>(false, Response.Status.BAD_REQUEST.getStatusCode(), "Verification code cannot be empty");

        return null;
    }

    private boolean checkUser(User user){
        return StringUtils.isBlank(user.getUname()) ||
                StringUtils.isBlank(user.getPword()) ||
                StringUtils.isBlank(user.getEmail());
    }
}
