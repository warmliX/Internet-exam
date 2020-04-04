package com.zzkk.internet.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author warmli
 */
@Component
public class TokenResolve {
    @Value("${auth.secret}")
    private String secret;

    @Value("${auth.issuer}")
    private String issuer;

    @Value("${auth.subproject}")
    private String subProject;

    public String getUser(String token){
        if (token == null || StringUtils.isBlank(token))
            return null;

        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();

        DecodedJWT decodedJWT = verifier.verify(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        Claim uName = claims.get("uname");
        return uName.asString();
    }

    public String generateToken(String uName){
        Date nowDate = new Date();
        long time = System.currentTimeMillis();
        time += 30*1000*60;
        Date expireDate = new Date(time);

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create().withHeader(map)
            /* 设置 载荷 Payload */
            .withClaim("uname", uName)
            .withIssuer(issuer)// 签名是有谁生成 例如 服务器
            .withSubject(subProject)// 签名的主题
            .withAudience(uName)// 签名的观众
            .withIssuedAt(nowDate) // 生成签名的时间
            .withExpiresAt(expireDate)// 签名过期的时间
            /* 签名 Signature */
            .sign(algorithm);
    }
}
