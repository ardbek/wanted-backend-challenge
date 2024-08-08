package org.project.portfolio.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.project.portfolio.domain.post.domain.Post;
import org.project.portfolio.domain.user.dto.request.UserSignUpRequestDTO;
import org.project.portfolio.global.common.entity.BaseEntity;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String email;

    private String mobile;

    private String nickname;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @Builder
    private User(String email, String mobile, String nickname, String password) {
        this.email = email;
        this.mobile = mobile;
        this.nickname = nickname;
        this.password = password;
        this.role = UserRole.ROLE_USER;
    }

    public static User of(UserSignUpRequestDTO userSignUpRequestDTO, String encodedPassword) {
        return User.builder()
                .email(userSignUpRequestDTO.userVo().email())
                .mobile(userSignUpRequestDTO.userVo().mobile())
                .nickname(userSignUpRequestDTO.userVo().nickname())
                .password(encodedPassword)
                .build();
    }
}
