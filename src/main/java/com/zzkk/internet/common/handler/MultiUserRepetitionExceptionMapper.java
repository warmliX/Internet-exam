package com.zzkk.internet.common.handler;

import com.zzkk.internet.common.exception.MultiUserRepetitionException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author warmli
 */
@Provider
public class MultiUserRepetitionExceptionMapper implements ExceptionMapper<MultiUserRepetitionException> {
    @Override
    public Response toResponse(MultiUserRepetitionException e) {
        return null;
    }
}
