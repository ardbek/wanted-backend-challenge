package org.project.portfolio.domain.user.dto.response;

import lombok.Builder;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
    @Builder
    public TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static TokenResponse of(String accessToken, String refreshToken) {
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
