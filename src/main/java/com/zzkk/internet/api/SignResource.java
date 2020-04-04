package com.zzkk.internet.api;

import com.zzkk.internet.pojo.Result;
import com.zzkk.internet.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Api(tags = "user_sign", value = "用户账号模板")
@Path("/user/sign")
@Consumes({MediaType.APPLICATION_JSON})
@Produces(MediaType.APPLICATION_JSON)
public interface SignResource {
    @ApiOperation("注册")
    @Path("/up")
    @POST
    Result<Boolean> signUp(
        @ApiParam(value = "用户信息", required = true)
        User user
    );

    @ApiOperation("登录")
    @Path("/in")
    @POST
    Result<String> signIn(
        @ApiParam(value = "用户信息", required = true)
        User user
    );

    @ApiOperation("发送验证码")
    @Path("/send_code")
    @POST
    Result<Boolean> sendVerifyCode(
        @ApiParam(value = "用户信息", required = true)
        User user
    );

    @ApiOperation("验证")
    @Path("/verify")
    @POST
    Result<Boolean> verifyCode(
        @ApiParam(value = "邮箱验证码", required = true)
        String code
    );
}
