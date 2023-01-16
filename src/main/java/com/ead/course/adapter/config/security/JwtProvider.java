package com.ead.course.adapter.config.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@Log4j2
public class JwtProvider {

    private final String jwtSecret;

    public JwtProvider(@Value("${ead.auth.jwt-secret}") final String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public String getSubjectJwt(final String token){
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(UTF_8))).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String getClaimNameJwt(final String token, final String claimName){
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(UTF_8))).build().parseClaimsJws(token).getBody().get(claimName, String.class);
    }

    public boolean validateJwt(final String authToken){
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(UTF_8))).build().parseClaimsJws(authToken);
            return true;
        }catch (SignatureException e){
            log.error("Invalid JWT signature: {}", e.getMessage());
        }catch (MalformedJwtException e){
            log.error("Invalid JWT token: {}", e.getMessage());
        }catch (ExpiredJwtException e){
            log.error("JWT token is expired: {}", e.getMessage());
        }catch (UnsupportedJwtException e){
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}
