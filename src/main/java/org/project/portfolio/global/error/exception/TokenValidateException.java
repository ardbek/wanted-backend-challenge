package org.project.portfolio.global.error.exception;

import org.project.portfolio.global.error.BaseErrorException;
import org.project.portfolio.global.error.GlobalErrorCode;

public class TokenValidateException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new TokenValidateException();

    public TokenValidateException() {
        super(GlobalErrorCode.INVALID_AUTH_TOKEN);
    }
}

