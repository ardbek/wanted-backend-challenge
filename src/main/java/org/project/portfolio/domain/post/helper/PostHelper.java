package org.project.portfolio.domain.post.helper;

import lombok.RequiredArgsConstructor;
import org.project.portfolio.domain.post.domain.Post;
import org.project.portfolio.domain.post.dto.request.PostCreateDTO;
import org.project.portfolio.domain.post.exception.InvalidContentException;
import org.project.portfolio.domain.post.exception.InvalidTitleException;
import org.project.portfolio.domain.post.mapper.PostMapper;
import org.project.portfolio.domain.post.repository.PostRepository;
import org.project.portfolio.domain.user.domain.PrincipalDetails;
import org.project.portfolio.global.common.util.ValidationUtil;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostHelper {

    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final ValidationUtil validationUtil;

    public Long createPost(PostCreateDTO postCreateDTO, PrincipalDetails principalDetails) {
        Post newPost = postMapper.toEntity(postCreateDTO, principalDetails.getUser());
        return postRepository.save(newPost).getId();
    }

    public void validateTitle(String title) {
        if(!validationUtil.isValidTitle(title)) {
            throw InvalidTitleException.EXCEPTION;
        }
    }

    public void validateContent(String content) {
        if(!validationUtil.isValidContent(content)) {
            throw InvalidContentException.EXCEPTION;
        }
    }
}
