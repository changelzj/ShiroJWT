package com.example.shirojwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;

public class JwtUtil {
    
    private static final long EXPIRE = 7 * 24 * 60 * 60 * 1000;
    
    private static final String SECRET = "GUYgyuGYUPO59*/ghjGYJgdrtvbhjf3445^%$#^INB";

    public static String signToken(String username, Long userId) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT") // head
                .setHeaderParam("alg", "HS256") // head
                .setIssuer("admin") // 签发人
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE)) // 过期时间
                .setAudience("tt") // 受众
                .setNotBefore(new Date()) // 生效时间
                .setIssuedAt(new Date()) // 签发时间
                .setSubject("token") // 主题
                .setId("001") // 编号
                .claim("userId", userId) // 私有字段
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
    
    public static Claims getClaims(String token) throws Exception {
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("");
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            JwsHeader<?> header = claimsJws.getHeader();
            String signature = claimsJws.getSignature();
            return claimsJws.getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
}
