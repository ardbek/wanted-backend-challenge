package org.project.portfolio.domain.post.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.project.portfolio.global.common.dto.ErrorReason;
import org.project.portfolio.global.error.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostErrorCode implements BaseErrorCode {
    LONG_TITLE(BAD_REQUEST, "POST_400_1", "제목은 최대 200자까지 입력 가능합니다."),
    LONG_CONTENT(BAD_REQUEST, "POST_400_2", "게시글은 최대 1000자까지 입력 가능합니다."),
    POST_NOT_FOUND(NOT_FOUND, "POST_404_1", "게시글이 존재하지 않습니다."),
    ;

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);
    }
}
