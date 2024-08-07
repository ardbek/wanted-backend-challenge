package org.project.portfolio.domain.user.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.project.portfolio.global.common.dto.ErrorReason;
import org.project.portfolio.global.error.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    USER_NOT_FOUND(NOT_FOUND, "USER_404_1", "존재하지 않는 유저입니다."),
    DUPLICATE_USER(BAD_REQUEST, "USER_400_1", "이미 존재하는 유저입니다."),
    DUPLICATE_EMAIL(BAD_REQUEST, "USER_400_2","중복된 이메일입니다."),
    DUPLICATE_NICKNAME(BAD_REQUEST, "USER_400_3","중복된 이름입니다."),
    MISMATCH_PASSWORD(BAD_REQUEST, "USER_400_4","중복된 이름입니다."),
    INVALID_REFRESH_TOKEN(BAD_REQUEST,"ADMIN_400_1","리프레시 토큰이 아닙니다.")
    ;

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);
    }
}
