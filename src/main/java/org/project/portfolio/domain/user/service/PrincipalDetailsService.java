package org.project.portfolio.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.project.portfolio.domain.user.domain.PrincipalDetails;
import org.project.portfolio.domain.user.domain.User;
import org.project.portfolio.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    public UserDetails loadUserByUserId(Long id) throws UsernameNotFoundException {

        return userRepository.findById(id)
                .map(user-> createUser(id, user))
                .orElseThrow(() -> new UsernameNotFoundException(id.toString()));
    }

    private PrincipalDetails createUser(Long id, User user) {
        return new PrincipalDetails(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
