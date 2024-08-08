package org.project.portfolio.global.config;

import java.util.Optional;
import org.project.portfolio.domain.user.domain.PrincipalDetails;
import org.project.portfolio.domain.user.domain.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();
        if(principal instanceof PrincipalDetails) {
            PrincipalDetails details = (PrincipalDetails) principal;
            User user = details.getUser();
            return Optional.of(user.getNickname());
        }

        return Optional.empty();
    }
}
