package com.bbrick.auth.comn.utils;

import com.bbrick.auth.comn.properties.JwtProperties;
import com.bbrick.auth.core.auth.dto.LogoutAccessToken;
import com.bbrick.auth.core.auth.dto.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenUtil {

    private final JwtProperties jwtProperties;

    // private final Long tokenExpirationHour = 30 * 60 * 1000L;
    private static final String JWT_TOKEN_EXCEPT_STRING = "Bearer ";
    private static final int JWT_TOKEN_STRING_START = 7;
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000L * 60 * 30; // 30분
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 7; // 7일

    public String createAccessToken(String email) {
        return this.generateToken(email, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public RefreshToken createRefreshToken(String email) {
        String token = this.generateToken(email, REFRESH_TOKEN_EXPIRATION_TIME);

        return RefreshToken.createRefreshToken(email, token, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public LogoutAccessToken createLogoutToken(String email, String accessToken) {
        return LogoutAccessToken.of(email, accessToken, getRemainMilliSeconds(accessToken));
    }

    private String generateToken(String email, long expiration) {

        Claims claims = Jwts.claims();
        claims.put("email", email);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmail(String token) {
       Claims claims = extractClaims(token);

       String email = claims.get("email", String.class);

        return email;
    }

    public boolean isExpiredToken(String token) {
        Date expiration = extractClaims(token).getExpiration();
       return expiration.before(new Date());
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Key getSignKey() {
        byte[] keyBytes = jwtProperties.getSecret().getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public long getRemainMilliSeconds(String token) {
        Date expiration = extractClaims(token).getExpiration();
        Date now = new Date();

        return expiration.getTime() - now.getTime();
    }

    public String getToken(String authorization) {
        if (!(StringUtils.hasText(authorization) && authorization.startsWith(JWT_TOKEN_EXCEPT_STRING))) { return null; }

        return authorization.substring(JWT_TOKEN_STRING_START);
    }

}
