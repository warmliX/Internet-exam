package com.zzkk.internet.common.filter;

import com.zzkk.internet.common.annotation.Compress;
import com.zzkk.internet.common.JedisUtil;
import com.zzkk.internet.common.TokenResolve;
import com.zzkk.internet.common.exception.MissingTokenException;
import com.zzkk.internet.common.exception.MultiUserRepetitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;

/**
 * @author warmli
 */
@Compress
@Component
public class AuthRequestFilter implements ContainerRequestFilter {

    private TokenResolve resolve;

    private JedisUtil jedisUtil;

    @Autowired
    public AuthRequestFilter(JedisUtil jedisUtil, TokenResolve resolve){
        this.jedisUtil = jedisUtil;
        this.resolve = resolve;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        System.out.println("1");
        MultivaluedMap<String, String> headers = containerRequestContext.getHeaders();
        String token = headers.getFirst("token");
        System.out.println(token);
        String user = resolve.getUser(token);
        if (user == null)
            throw new MissingTokenException();

        String localToken = jedisUtil.get(token, 1);
        System.out.println("local token: " + localToken);
        if (!localToken.equals(user))
            throw new MultiUserRepetitionException();
    }
}
