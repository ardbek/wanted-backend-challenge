package org.project.portfolio.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.project.portfolio.domain.user.domain.User;
import org.project.portfolio.domain.user.dto.request.UserSignInRequestDTO;
import org.project.portfolio.domain.user.dto.request.UserSignUpRequestDTO;
import org.project.portfolio.domain.user.dto.response.TokenResponse;
import org.project.portfolio.domain.user.dto.response.UserIdResponseDTO;
import org.project.portfolio.domain.user.helper.UserHelper;
import org.project.portfolio.domain.user.mapper.UserMapper;
import org.project.portfolio.domain.user.repository.UserRepository;
import org.project.portfolio.global.common.jwt.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserHelper userHelper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public UserIdResponseDTO signup(UserSignUpRequestDTO signupRequest) {
        // 닉네임 중복 확인
        userHelper.isNicknameDuplicate(signupRequest.userVo().nickname());
        // 이메일 중복 확인
        userHelper.isEmailDuplicate(signupRequest.userVo().email());

        // 필수값 유효성 검사
        userHelper.validateEmail(signupRequest.userVo().email());
        userHelper.validateMobile(signupRequest.userVo().mobile());
        userHelper.validatePassword(signupRequest.userVo().password());
        userHelper.validateNickname(signupRequest.userVo().nickname());
        
        String encodedPassword = passwordEncoder.encode(signupRequest.userVo().password());
        User user = userMapper.toEntity(signupRequest, encodedPassword);

        return userMapper.toUserIdResponseDTO(userRepository.save(user).getId());
    }

    public TokenResponse signin(UserSignInRequestDTO signinRequest) {
        User user = userHelper.findForSignIn(signinRequest);
        Authentication authentication = userHelper.userAuthorizationInput(user);

        String accessToken = tokenProvider.createAccessToken(user.getId(), authentication);
//        final String refreshToken = tokenProvider.createRefreshToken(user.getId(), authentication);

        return userMapper.toTokenResponse(accessToken, accessToken);
    }
}
