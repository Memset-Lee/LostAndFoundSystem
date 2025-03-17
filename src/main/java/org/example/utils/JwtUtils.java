package org.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    private static String signKey;
    private static Long expire;

    @Value("${jwt.signKey}")
    private String tempSignKey;
    @Value("${jwt.expiration}")
    private Long tempExpire;

    @PostConstruct
    public void init() {
        signKey = tempSignKey;
        expire = tempExpire;
    }

    //生成Jwt令牌
    public static String generateJwt(Map<String, Object> claims) {
        String jwt = Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, signKey)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
        return jwt;
    }

    //解析Jwt令牌
    public static Claims parseJwt(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }
}
