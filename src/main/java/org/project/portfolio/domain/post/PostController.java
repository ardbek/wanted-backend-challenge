package org.project.portfolio.domain.post;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.project.portfolio.domain.post.dto.request.PostCreateDTO;
import org.project.portfolio.domain.post.dto.request.PostUpdateDTO;
import org.project.portfolio.domain.post.dto.response.PostIdResponse;
import org.project.portfolio.domain.post.service.PostService;
import org.project.portfolio.domain.user.domain.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Post")
@RequestMapping("/api/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 작성")
    @PostMapping
    public PostIdResponse createPost(@RequestBody PostCreateDTO postCreateDTO,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return postService.createPost(postCreateDTO, principalDetails);
    }

    @Operation(summary = "게시글 수정")
    @PatchMapping
    public PostIdResponse updatePost(@RequestBody PostUpdateDTO postUpdateDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return postService.updatePost(postUpdateDTO, principalDetails);
    }

}
