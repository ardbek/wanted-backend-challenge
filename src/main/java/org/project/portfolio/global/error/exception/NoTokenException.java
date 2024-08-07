package org.project.portfolio.global.error.exception;

import org.project.portfolio.global.error.BaseErrorException;
import org.project.portfolio.global.error.GlobalErrorCode;

public class NoTokenException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new NoTokenException();

    private NoTokenException() {
        super(GlobalErrorCode.NO_TOKEN);
    }
}
