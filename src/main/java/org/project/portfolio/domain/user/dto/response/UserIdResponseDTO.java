package org.project.portfolio.domain.user.dto.response;

public record UserIdResponseDTO(
        Long userId
) {
    public static UserIdResponseDTO of(Long userId) {
        return new UserIdResponseDTO(userId);
    }
}
