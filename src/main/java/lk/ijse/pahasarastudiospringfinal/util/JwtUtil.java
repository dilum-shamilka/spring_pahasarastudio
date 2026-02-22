package lk.ijse.pahasarastudiospringfinal.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lk.ijse.pahasarastudiospringfinal.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60; // 24 hours

    // If you set a key in application.properties, it must be at least 64 bytes for HS512
    @Value("${jwt.secret:defaultSuperLongSecureSecretKeyForHS512AlgorithmWhichIsAtLeast64BytesLong!@#1234567890}")
    private String secretKey;

    // Generate HS512 key correctly
    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        if (keyBytes.length < 64) { // HS512 requires at least 64 bytes
            throw new RuntimeException("JWT secret key is too short for HS512! Must be at least 64 bytes");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public String generateToken(UserDTO userDTO) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDTO.getRole());
        claims.put("userId", userDTO.getUserId());
        return doGenerateToken(claims, userDTO.getEmail());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512) // safe key length now
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = getUsernameFromToken(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    public String getRoleFromToken(String token) {
        return getAllClaimsFromToken(token).get("role", String.class);
    }

    public Long getUserIdFromToken(String token) {
        Object userIdClaim = getAllClaimsFromToken(token).get("userId");
        if (userIdClaim instanceof Integer) return ((Integer) userIdClaim).longValue();
        if (userIdClaim instanceof Long) return (Long) userIdClaim;
        if (userIdClaim instanceof String) return Long.parseLong((String) userIdClaim);
        throw new RuntimeException("Invalid userId format in JWT token");
    }
}
