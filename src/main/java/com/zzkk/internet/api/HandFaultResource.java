package com.zzkk.internet.api;

import com.zzkk.internet.common.annotation.Compress;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Api(value = "通用操作接口")
@Path("/problem")
@Consumes({MediaType.APPLICATION_JSON})
@Produces(MediaType.APPLICATION_JSON)
public interface HandFaultResource {
    @ApiOperation(value="获取故障", notes="获取小程序首页的基本设置")
    @GET
    @Compress
    @Path("/layout")
    String handFault(
        @ApiParam
        @HeaderParam("token")
        String token
    );
}
