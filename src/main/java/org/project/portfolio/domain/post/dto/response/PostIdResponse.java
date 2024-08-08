package org.project.portfolio.domain.post.dto.response;

public record PostIdResponse(
        Long id
) {
    public static PostIdResponse of(Long postId) {
        return new PostIdResponse(postId);
    }
}
