package org.project.portfolio.domain.user.exception;

import org.project.portfolio.global.error.BaseErrorException;

public class DuplicateNickname extends BaseErrorException {

    public static final DuplicateNickname EXCEPTION = new DuplicateNickname();

    public DuplicateNickname() {
        super(UserErrorCode.DUPLICATE_NICKNAME);
    }
}
