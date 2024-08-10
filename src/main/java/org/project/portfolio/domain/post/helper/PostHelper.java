package org.project.portfolio.domain.post.helper;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.project.portfolio.domain.post.domain.Post;
import org.project.portfolio.domain.post.dto.request.PostCreateDTO;
import org.project.portfolio.domain.post.dto.request.PostSearchRequestDTO;
import org.project.portfolio.domain.post.dto.request.PostUpdateDTO;
import org.project.portfolio.domain.post.exception.InvalidContentException;
import org.project.portfolio.domain.post.exception.InvalidTitleException;
import org.project.portfolio.domain.post.exception.PostNotFound;
import org.project.portfolio.domain.post.exception.PostUpdateNotAllowed;
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

    public Long updatePost(PostUpdateDTO postUpdateDTO, PrincipalDetails principalDetails) {
        Post updatePost = postMapper.toEntity(postUpdateDTO, principalDetails.getUser());
        return postRepository.save(updatePost).getId();
    }

    public void isUpdatable(Long id) {
        Optional<Post> findPost = postRepository.findById(id);
        Post post = findPost.orElseThrow(() -> PostNotFound.EXCEPTION);

        if (ValidationUtil.isUpdatable(post.getCreateAt())) {
            throw PostUpdateNotAllowed.EXCEPTION;
        }
    }

    public Object getPostList(PostSearchRequestDTO searchRequestDTO) {
        return postRepository.getPostsVoList(searchRequestDTO);
    }
}
