package org.project.portfolio.global.common.dto;

public record ErrorReason(
        Integer status,
        String code,
        String reason
) {

    public static ErrorReason of(Integer status, String code, String reason) {
        return new ErrorReason(status, code, reason);
    }
}
