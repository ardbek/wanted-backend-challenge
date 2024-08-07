package org.project.portfolio.domain.user.domain;

import lombok.Getter;

@Getter
public enum UserRole {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER"),
    ROLE_ANONYMOUS("ROLE_ANONYMOUS");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String value() {
        return role;
    }
}
