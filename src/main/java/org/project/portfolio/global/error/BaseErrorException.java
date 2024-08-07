package org.project.portfolio.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.project.portfolio.global.common.dto.ErrorReason;

@Getter
@AllArgsConstructor
public class BaseErrorException extends RuntimeException {

    private BaseErrorCode errorCode;

    public ErrorReason getErrorReason() {
        return this.errorCode.getErrorReason();
    }

}
