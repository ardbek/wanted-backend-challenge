package org.project.portfolio.domain.user.exception;

import org.project.portfolio.global.error.BaseErrorException;

public class MismatchPassword extends BaseErrorException {

    public static final MismatchPassword EXCEPTION = new MismatchPassword();

    private MismatchPassword() {
        super(UserErrorCode.MISMATCH_PASSWORD);
    }
}
