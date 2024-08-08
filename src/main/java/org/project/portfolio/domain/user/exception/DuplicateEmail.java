package org.project.portfolio.domain.user.exception;

import org.project.portfolio.global.error.BaseErrorException;

public class DuplicateEmail extends BaseErrorException {

    public static final DuplicateEmail EXCEPTION = new DuplicateEmail();

    public DuplicateEmail() {
        super(UserErrorCode.DUPLICATE_EMAIL);
    }
}
