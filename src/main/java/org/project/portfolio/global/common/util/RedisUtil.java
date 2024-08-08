package org.project.portfolio.global.common.util;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;

    public void setRefreshToken(Long id, String refreshToken, int expirationTime) {
        redisTemplate.opsForValue().set(id.toString(),refreshToken,expirationTime, TimeUnit.SECONDS);
    }

    public String getRefreshToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setLogoutAccessToken(String accessToken, int expirationTime) {
        redisTemplate.opsForValue().set(accessToken, "logout", Duration.ofMillis(expirationTime));
    }

    public String getLogoutAccessToken(String accessToken) {
        return redisTemplate.opsForValue().get(accessToken);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

}
