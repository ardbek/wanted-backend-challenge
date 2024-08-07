package org.project.portfolio.global.error;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.project.portfolio.global.common.dto.ErrorReason;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {
        log.error("HandleInternalException", ex);
        final HttpStatus status = (HttpStatus) statusCode;
        final ErrorReason errorReason = ErrorReason.of(status.value(), status.name(),
                ex.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.from(errorReason);
        return super.handleExceptionInternal(ex, errorResponse, headers, statusCode, request);
    }

    //Valid Exception 예외 처리
    @Nullable
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final HttpStatus httpStatus = (HttpStatus)status;
        final List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        final Map<String, Object> fieldAndErrorMessages =
                errors.stream()
                        .collect(
                                Collectors.toMap(
                                        FieldError::getField, FieldError::getDefaultMessage));
        final String errorsToJsonString = fieldAndErrorMessages.entrySet().stream().map(e -> e.getKey() + " : " + e.getValue())
                .collect(Collectors.joining("|"));
        final ErrorReason errorReason = ErrorReason.of(status.value(), httpStatus.name(), errorsToJsonString);
        final ErrorResponse errorResponse = ErrorResponse.from(errorReason);
        return ResponseEntity.status(HttpStatus.valueOf(errorReason.status()))
                .body(errorResponse);
    }

    // 비즈니스 로직 에러 처리
    @ExceptionHandler(BaseErrorException.class)
    public ResponseEntity<ErrorResponse> handleBaseErrorException(BaseErrorException e,
            HttpServletRequest request) {
        log.error("BaseErrorException", e);
        final ErrorReason errorReason = e.getErrorReason();
        final HttpStatus status;
        try {
            status = HttpStatus.valueOf(errorReason.status());
        } catch (IllegalArgumentException ex) {
            log.error("Invalid HTTP status code: " + errorReason.status(), ex);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                    ErrorResponse.from(ErrorReason.of(INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR", "Invalid status code"))
            );
        }
        final ErrorResponse errorResponse = ErrorResponse.from(errorReason);
        return ResponseEntity.status(status).body(errorResponse);

    }

    // 나머지 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e,
            HttpServletRequest request) {
        log.error("GlobalExceptionHandler :: handleException == ", e);

        final GlobalErrorCode globalErrorCode = GlobalErrorCode._INTERNAL_SERVER_ERROR;
        final ErrorReason errorReason = globalErrorCode.getErrorReason();
        final ErrorResponse errorResponse = ErrorResponse.from(errorReason);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
