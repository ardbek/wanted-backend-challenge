package org.project.portfolio.domain.post.dto.response;

import java.util.List;
import org.project.portfolio.domain.post.vo.PostVo;

public record PostListResponse(
        List<PostVo> postList
) {

}
