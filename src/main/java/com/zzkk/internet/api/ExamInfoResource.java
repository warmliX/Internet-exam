package com.zzkk.internet.api;

import com.zzkk.internet.common.annotation.Compress;
import com.zzkk.internet.pojo.Record;
import com.zzkk.internet.pojo.Result;
import com.zzkk.internet.pojo.StatisticsInfo;
import com.zzkk.internet.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import redis.clients.jedis.Tuple;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

@Api(tags = "user_sign", value = "考试信息")
@Path("/user/exam/info")
@Consumes({MediaType.APPLICATION_JSON})
@Produces(MediaType.APPLICATION_JSON)
public interface ExamInfoResource {
    @ApiOperation("获取用户信息")
    @Path("/statistics")
    @GET
    @Compress
    Result<StatisticsInfo> statistics(
        @ApiParam(value = "凭证", required = true)
        @HeaderParam("token")
        String token
    );

    @ApiOperation("获取排名")
    @Path("/ranking")
    @GET
    @Compress
    Result<Set<Tuple>> ranking(
        @ApiParam(value = "凭证", required = true)
        @HeaderParam("token")
        String token
    );

    @ApiOperation("获取通过的考试记录")
    @Path("/record/pass")
    @GET
    @Compress
    Result<List<Record>> recordOfPass(
        @ApiParam(value = "凭证", required = true)
        @HeaderParam("token")
        String token
    );

    @ApiOperation("获取未通过的考试记录")
    @Path("/record/unpass")
    @GET
    @Compress
    Result<List<Record>> recordOfUnpass(
        @ApiParam(value = "凭证", required = true)
        @HeaderParam("token")
        String token
    );
}
