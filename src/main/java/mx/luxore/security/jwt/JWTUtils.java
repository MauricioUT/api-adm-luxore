package mx.luxore.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JWTUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    public String generateAccessToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", null)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getSignatureKety(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * validar el token de acceso
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignatureKety())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token".concat(e.getMessage()));
            return false;
        }
    }

    /**
     * obtener todos los claims del token
     * Claim: es toda los parametros que vienen del token
     */
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignatureKety())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * obtener un solo claim
     */
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * obtener usernmae del token
     */
    public String getUsernameFromToken(String username) {
        return getClaim(username, Claims::getSubject);
    }

    /**
     * obtener firma del token
     */
    public Key getSignatureKety() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
