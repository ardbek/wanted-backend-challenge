package org.project.portfolio.domain.post.mapper;

import lombok.RequiredArgsConstructor;
import org.project.portfolio.domain.post.domain.Post;
import org.project.portfolio.domain.post.dto.request.PostCreateDTO;
import org.project.portfolio.domain.post.dto.request.PostUpdateDTO;
import org.project.portfolio.domain.post.dto.response.PostIdResponse;
import org.project.portfolio.domain.user.domain.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    public Post toEntity(PostCreateDTO postCreateDTO, User user) {
        return Post.of(postCreateDTO, user);
    }

    public Post toEntity(PostUpdateDTO postUpdateDTO, User user) {
        return Post.of(postUpdateDTO, user);
    }

    public PostIdResponse toPostIdResponse(Long postId) {
        return PostIdResponse.of(postId);
    }
}
