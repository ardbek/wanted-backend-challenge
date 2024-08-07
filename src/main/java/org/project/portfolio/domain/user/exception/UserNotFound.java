package org.project.portfolio.domain.user.exception;

import org.project.portfolio.global.error.BaseErrorException;

public class UserNotFound extends BaseErrorException {

    public static final UserNotFound EXCEPTION = new UserNotFound();

    public UserNotFound() {
        super(UserErrorCode.USER_NOT_FOUND);
    }
}
