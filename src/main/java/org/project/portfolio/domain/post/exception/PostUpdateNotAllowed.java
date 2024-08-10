package org.project.portfolio.domain.post.exception;

import org.project.portfolio.global.error.BaseErrorException;

public class PostUpdateNotAllowed extends BaseErrorException {

    public static final PostUpdateNotAllowed EXCEPTION = new PostUpdateNotAllowed();

    public PostUpdateNotAllowed() {
        super(PostErrorCode.POST_NOT_ALLOWED_UPDATE);
    }
}
