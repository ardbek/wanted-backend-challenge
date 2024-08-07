package org.project.portfolio.domain.user.repository;

import java.util.Optional;
import org.project.portfolio.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);
}
