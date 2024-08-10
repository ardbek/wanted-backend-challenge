package org.project.portfolio.domain.post.vo;

import java.time.LocalDateTime;

public record PostVo(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt

) {

}
