package com.zzkk.internet.common.handler;

import com.zzkk.internet.common.exception.MissingTokenException;
import com.zzkk.internet.pojo.Result;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author warmli
 */
@Provider
public class MissingTokenExceptionMapper implements ExceptionMapper<MissingTokenException> {
    @Override
    public Response toResponse(MissingTokenException e) {
        return Response.status(Response.Status.BAD_REQUEST)
            .type(MediaType.APPLICATION_JSON_TYPE)
            .entity(new Result<Void>(null, Response.Status.BAD_REQUEST.getStatusCode(), "missing token in request"))
            .build();
    }
}
