package org.project.portfolio.domain.user.exception;

import org.project.portfolio.global.error.BaseErrorException;

public class InvalidRefreshToken extends BaseErrorException {

    public static final InvalidRefreshToken EXCEPTION = new InvalidRefreshToken();

    public InvalidRefreshToken() {
        super(UserErrorCode.INVALID_REFRESH_TOKEN);
    }
}
