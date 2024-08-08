package org.project.portfolio.domain.post.exception;

import org.project.portfolio.global.error.BaseErrorException;

public class InvalidTitleException extends BaseErrorException {

    public static final InvalidTitleException EXCEPTION = new InvalidTitleException();

    public InvalidTitleException() {
        super(PostErrorCode.LONG_TITLE);
    }
}
