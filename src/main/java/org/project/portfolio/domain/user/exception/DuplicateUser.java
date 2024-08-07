package org.project.portfolio.domain.user.exception;

import org.project.portfolio.global.error.BaseErrorException;

public class DuplicateUser extends BaseErrorException {

    public static final DuplicateUser EXCEPTION = new DuplicateUser();

    public DuplicateUser() {
        super(UserErrorCode.DUPLICATE_USER);
    }
}
