package com.dev.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;
import javax.crypto.SecretKey;


@Component
@Slf4j
public class JwtProvider {


	@Value("${app.jwt-secret}")
	private String jwtSecret;

	@Value("${app.jwt-expiration-milliseconds}")
	private long jwtExpirationInMs;


    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date expirationDate = new Date(System.currentTimeMillis() + jwtExpirationInMs);

        // Convertir la clave secreta en un SecretKey
        byte[] apiKeySecretBytes = Decoders.BASE64.decode(jwtSecret);
        SecretKey key = Keys.hmacShaKeyFor(apiKeySecretBytes);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

//    public String generateToken(String username) {
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000);
//
//        JwtBuilder jwtBuilder = Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith(SECRET_KEY);
//
//        return jwtBuilder.compact();
//    }

    public String getUsernameFromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token);

        return claimsJws.getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token);
            return true;

        }
        
        catch (MalformedJwtException e){
            log.error("token mal formado");
        }catch (UnsupportedJwtException e){
            log.error("token no soportado");
        }catch (ExpiredJwtException e){
            log.error("token expirado");
        }catch (IllegalArgumentException e){
            log.error("token vac�o");
        }catch (SignatureException e){
            log.error("fail en la firma"+ e.getMessage());
        }
        catch (JwtException e) {
            log.error("Error al validar el token: {}", e.getMessage());
        }
        return false;
    }


}