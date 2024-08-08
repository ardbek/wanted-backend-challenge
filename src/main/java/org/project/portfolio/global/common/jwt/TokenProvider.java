package org.project.portfolio.global.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.portfolio.domain.user.domain.PrincipalDetails;
import org.project.portfolio.domain.user.exception.InvalidRefreshToken;
import org.project.portfolio.domain.user.service.PrincipalDetailsService;
import org.project.portfolio.global.common.util.RedisUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    private final PrincipalDetailsService principalDetailsService;
    private final RedisUtil redisUtil;

    private static final String AUTHORITIES_KEY = "auth";
    private static final String ACCESS_KEY = "access";
    private static final String REFRESH_KEY = "refresh";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration-time}")
    private int accessTokenExpirationTime;

    @Value("${jwt.refresh-token-expiration-time}")
    private int refreshTokenExpirationTime;

    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte keyBytes[] = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(Long id, Authentication authentication) {
        return createToken(id, ACCESS_KEY, accessTokenExpirationTime, authentication);
    }

    public String createRefreshToken(Long id, Authentication authentication) {
        return createToken(id, REFRESH_KEY, refreshTokenExpirationTime, authentication);
    }

    private String createToken(Long id, String type, int expirationTime,
            Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, expirationTime);

        final Date issuedAt = new Date();
        final Date validity = new Date(calendar.getTimeInMillis());

        String token = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(id.toString())
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(issuedAt)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        if (type.equals(REFRESH_KEY)) {
            redisUtil.setRefreshToken(id, token, expirationTime);
        }

        return token;
    }

    public void deleteRefreshToken(Long id) {
        redisUtil.delete(id.toString());
    }

    public String getTokenUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Authentication getAuthentication(String token) {
        PrincipalDetails principalDetails = (PrincipalDetails) principalDetailsService.loadUserByUserId(
                Long.parseLong(getTokenUserId(token)));
        return new UsernamePasswordAuthenticationToken(principalDetails, token,
                principalDetails.getAuthorities());
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info(e.toString());
            log.info("만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("토큰이 잘못되었습니다.");
        }
        return false;
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public void validateRefreshToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
                .getBody();
        String typeValue = claims.get("type", String.class);
        if (!typeValue.equals(REFRESH_KEY)) {
            throw InvalidRefreshToken.EXCEPTION;
        }
    }

    public long getExpiration(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
                .getBody().getExpiration();
        Long now = new Date().getTime();

        return (expiration.getTime() - now);
    }
}
