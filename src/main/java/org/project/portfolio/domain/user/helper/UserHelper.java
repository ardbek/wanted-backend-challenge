package org.project.portfolio.domain.user.helper;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.project.portfolio.domain.user.domain.User;
import org.project.portfolio.domain.user.dto.request.UserSignInRequestDTO;
import org.project.portfolio.domain.user.exception.DuplicateNickname;
import org.project.portfolio.domain.user.exception.DuplicateUser;
import org.project.portfolio.domain.user.exception.MismatchPassword;
import org.project.portfolio.domain.user.exception.UserNotFound;
import org.project.portfolio.domain.user.repository.UserRepository;
import org.project.portfolio.domain.user.service.PrincipalDetailsService;
import org.project.portfolio.global.common.util.ValidationUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHelper {

    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;
    private final PasswordEncoder passwordEncoder;
    private final PrincipalDetailsService principalDetailsService;

    public void isEmailDuplicate(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw DuplicateUser.EXCEPTION;
        }
    }

    public void isNicknameDuplicate(String nickname) {
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw DuplicateNickname.EXCEPTION;
        }
    }

    public void validateEmail(String email) {
        if (!validationUtil.isValidEmail(email)) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
    }

    public void validateNickname(String nickname) {
        if (!validationUtil.isValidNickname(nickname)) {
            throw new IllegalArgumentException("닉네임 형식이 올바르지 않습니다.");
        }
    }

    public void validatePassword(String password) {
        if (!validationUtil.isValidPassword(password)) {
            throw new IllegalArgumentException("비밀번호 형식이 올바르지 않습니다.");
        }
    }

    public void validateMobile(String mobile) {
        if (!validationUtil.isValidMobile(mobile)) {
            throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다.");
        }
    }

    public User findForSignIn(UserSignInRequestDTO signinRequest) {
        Optional<User> findUser = userRepository.findByEmail(signinRequest.email());
        if (findUser.isEmpty()) {
            throw UserNotFound.EXCEPTION;
        }

        if (!matchesPassword(signinRequest.password(), findUser.get().getPassword())) {
            throw MismatchPassword.EXCEPTION;
        }

        return findUser.get();
    }


    public boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public Authentication userAuthorizationInput(User user) {
        UserDetails userDetails = principalDetailsService.loadUserByUsername(user.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
