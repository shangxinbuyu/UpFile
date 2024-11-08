package io.github.sgangxinbuyu.upfile.util;

import io.github.sgangxinbuyu.upfile.properties.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    final JwtProperties jwtProperties;


    // 生成 Token
    public String generateToken(String subject) {

        return Jwts.builder()
                .setSubject(subject) // 设置主题
                .setIssuedAt(new Date(System.currentTimeMillis())) // 设置Jwt生效时间
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration())) // 设置Jwt过期时间
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret()) // 使用 HMAC SHA-256 算法签名
                .compact();
    }

    // 判断 Token 是否过期

    public boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getExpiration().before(new Date());
    }
}
