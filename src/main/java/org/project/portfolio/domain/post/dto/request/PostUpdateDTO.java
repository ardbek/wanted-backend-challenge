package org.project.portfolio.domain.post.dto.request;

public record PostUpdateDTO(
    Long id,
    String title,
    String content
) {
}
