package com.example.engagement_platform.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    //@Value("${security.jwt.expiration}")
    private long jwtExpiration = 8640000;
    //@Value("${security.jwt.secret-key}")
    private String secretKey = "8c6c32b90a58f606d3ab96c10a7e2e5684eab1e15a72332842dbac503ed3cbbcdfbd8942f5a6eabc299ce704cc0c409db1635612ae8922219a511da8afd86a559652180295bd61a408119ced81b772b5a677156579d86e579cbaf62c59d2b9a82fe106e0b5f5f677ec44a5a0e6819cd85c652f08e2996dcfe4764fbbf394128c3a836e6f6b9c85873c57c2fd58eef0cbf17032c2770046684a4509afd9f7d87905c90201c31e97662f276e7976f468672f87ad09074391f1775dbecc9c32cd6e9f0c41475d663f5f2af018da98d886eb5e47ee7a46a224c60b4811e2edb205da5ad4cd070230ced55d11961221275630f26099731d739f6e78370575770f8017";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                ;
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        if (claims == null) {
            claims = new HashMap<>();
        }
        return buildToken(claims, userDetails, jwtExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long jwtExpiration
    ) {
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .claim("authorities", authorities)
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
