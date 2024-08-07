package org.project.portfolio.global.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.project.portfolio.domain.user.dto.request.UserSignInRequestDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public LoginAuthenticationFilter(final String defaultFilterProcessesUrl,
            final AuthenticationManager authManager) {
        super(defaultFilterProcessesUrl, authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        String method = request.getMethod();

        if(!method.equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + method);
        }

        ServletInputStream inputStream = request.getInputStream();

        UserSignInRequestDTO signInDTO = new ObjectMapper().readValue(inputStream, UserSignInRequestDTO.class);

        return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                signInDTO.email(),
                signInDTO.password()
        ));

    }

}
