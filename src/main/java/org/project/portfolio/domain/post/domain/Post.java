package org.project.portfolio.domain.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.project.portfolio.domain.post.dto.request.PostCreateDTO;
import org.project.portfolio.domain.user.domain.User;
import org.project.portfolio.global.common.entity.BaseEntity;

@Getter
@Entity
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    private String content;

    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public Post(String title, String content, LocalDateTime deletedAt, User user) {
        this.title = title;
        this.content = content;
        this.deletedAt = deletedAt;
        this.user = user;
    }

    public static Post of(PostCreateDTO postCreateDTO, User user) {
        return Post.builder()
                .title(postCreateDTO.title())
                .content(postCreateDTO.content())
                .user(user)
                .build();
    }


}
