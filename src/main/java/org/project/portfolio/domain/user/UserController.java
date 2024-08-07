package org.project.portfolio.domain.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.project.portfolio.domain.user.dto.request.UserSignInRequestDTO;
import org.project.portfolio.domain.user.dto.request.UserSignUpRequestDTO;
import org.project.portfolio.domain.user.dto.response.TokenResponse;
import org.project.portfolio.domain.user.dto.response.UserIdResponseDTO;
import org.project.portfolio.domain.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User")
@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public UserIdResponseDTO signup(@RequestBody UserSignUpRequestDTO signupRequest) {
        return userService.signup(signupRequest);
    }

    @Operation(summary = "로그인")
    @PostMapping("/signin")
    public TokenResponse signin(@RequestBody UserSignInRequestDTO signinRequest) {
        return userService.signin(signinRequest);
    }
}
