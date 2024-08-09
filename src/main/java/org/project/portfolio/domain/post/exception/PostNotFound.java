package org.project.portfolio.domain.post.exception;

import org.project.portfolio.global.error.BaseErrorException;

public class PostNotFound extends BaseErrorException {

    public static final PostNotFound EXCEPTION = new PostNotFound();

    public PostNotFound() {
        super(PostErrorCode.POST_NOT_FOUND);
    }
}
