package com.spheretech.taxisphere.shared.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(JwtProperties.class)
public class JwtTokenService {

    private final JwtProperties jwtProperties;

    public JwtTokenService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String createAccessToken(AuthenticatedUser user) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(jwtProperties.accessTokenMinutes() * 60);

        return Jwts.builder()
                .issuer(jwtProperties.issuer())
                .subject(user.username())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .claim("userId", user.userId().toString())
                .claim("tenantId", user.tenantId() == null ? null : user.tenantId().toString())
                .claim("roles", user.roles())
                .signWith(secretKey())
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey())
                .requireIssuer(jwtProperties.issuer())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public AuthenticatedUser parseUser(String token) {
        Claims claims = parseClaims(token);
        UUID userId = UUID.fromString(claims.get("userId", String.class));
        String tenant = claims.get("tenantId", String.class);
        List<String> roles = claims.get("roles", List.class);

        return new AuthenticatedUser(
                userId,
                tenant == null ? null : UUID.fromString(tenant),
                claims.getSubject(),
                roles
        );
    }

    private SecretKey secretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.secret().getBytes(StandardCharsets.UTF_8));
    }
}
