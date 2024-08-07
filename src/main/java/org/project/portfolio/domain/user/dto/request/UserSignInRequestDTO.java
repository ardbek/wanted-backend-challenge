package org.project.portfolio.domain.user.dto.request;

public record UserSignInRequestDTO(
        String email,
        String password
) {

}
