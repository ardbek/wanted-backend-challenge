package org.project.portfolio.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.project.portfolio.domain.post.dto.request.PostCreateDTO;
import org.project.portfolio.domain.post.dto.response.PostIdResponse;
import org.project.portfolio.domain.post.helper.PostHelper;
import org.project.portfolio.domain.post.mapper.PostMapper;
import org.project.portfolio.domain.user.domain.PrincipalDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostHelper postHelper;
    private final PostMapper postMapper;

    public PostIdResponse createPost(PostCreateDTO postCreateDTO, PrincipalDetails principalDetails) {
        //유효성 검사
        postHelper.validateTitle(postCreateDTO.title());
        postHelper.validateContent(postCreateDTO.content());
        
        return postMapper.toPostIdResponse(postHelper.createPost(postCreateDTO, principalDetails));
    }
}
