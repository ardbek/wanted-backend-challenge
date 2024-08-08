package org.project.portfolio.domain.post.exception;

import org.project.portfolio.global.error.BaseErrorException;

public class InvalidContentException extends BaseErrorException {

    public static final InvalidTitleException EXCEPTION = new InvalidTitleException();

    public InvalidContentException() {
        super(PostErrorCode.LONG_CONTENT);
    }
}
