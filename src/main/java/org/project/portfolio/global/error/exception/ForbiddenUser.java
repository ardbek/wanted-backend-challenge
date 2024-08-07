package org.project.portfolio.global.error.exception;

import org.project.portfolio.global.error.BaseErrorException;
import org.project.portfolio.global.error.GlobalErrorCode;

public class ForbiddenUser extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new ForbiddenUser();

    private ForbiddenUser() {
        super(GlobalErrorCode.FORBIDDEN_USER);
    }
}