package org.project.portfolio.domain.user.mapper;

import org.project.portfolio.domain.user.domain.User;
import org.project.portfolio.domain.user.dto.request.UserSignUpRequestDTO;
import org.project.portfolio.domain.user.dto.response.TokenResponse;
import org.project.portfolio.domain.user.dto.response.UserIdResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserSignUpRequestDTO userSignUpRequestDTO, String encodedPassword) {
        return User.of(userSignUpRequestDTO, encodedPassword);
    }

    public UserIdResponseDTO toUserIdResponseDTO(Long userId) {
        return UserIdResponseDTO.of(userId);
    }

    public TokenResponse toTokenResponse(String accessToken, String refreshToken) {
        return TokenResponse.of(accessToken, refreshToken);
    }
}
