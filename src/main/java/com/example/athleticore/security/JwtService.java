package com.example.athleticore.security;

import com.example.athleticore.dto.user.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {
    @Value("${spring.security.jwtToken}")
    private String jwtSecret;

    public JwtAuthDTO generateAuthToken(UserDto user) {
        return JwtAuthDTO.builder()
                .token(generateJwtToken(user))
                .refreshToken(generateRefreshToken(user)).build();
    }

    public JwtAuthDTO refreshBaseToken(UserDto user, String refreshToken) {
        return JwtAuthDTO.builder()
                .token(generateJwtToken(user))
                .refreshToken(refreshToken).build();
    }

    public String getEmailFromToken(String token){
        return getClaims(token).get("email").toString();
    }

    public String getRolesFromToken(String token){
        return getClaims(token).get("role").toString();
    }

    public void validateJwtToken(String token) throws JwtException {
        getClaims(token).getSubject();
    }

    private String generateJwtToken(UserDto user) {
        Date date = Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant());

        return generateToken(user, date);
    }

    private String generateRefreshToken(UserDto user) {
        Date date = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());

        return generateToken(user, date);
    }

    private String generateToken(UserDto user, Date expiration) {
        Map<String, String> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());

        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .expiration(expiration)
                .signWith(getSingInKey())
                .compact();
    }

    private SecretKey getSingInKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(getSingInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
