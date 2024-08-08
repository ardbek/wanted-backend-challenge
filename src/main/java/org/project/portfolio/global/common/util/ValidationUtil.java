package org.project.portfolio.global.common.util;

import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String MOBILE_REGEX = "^010-\\d{4}-\\d{4}$";
    private static final String NICKNAME_REGEX = "^[a-zA-Z0-9]+$";
    private static final String PASSWORD_REGEX = "^(?=(?:[^0-9]*[0-9]){5})(?=(?:[^!@#$%^&*()_+]*[!@#$%^&*()_+]){2})(?=.*[a-z])(?=.*[A-Z]).{5,}$";

    private static final int MAX_TITLE_LENGTH = 200;
    private static final int MAX_CONTENT_LENGTH = 1000;

    public static boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static boolean isValidMobile(String mobile) {
        return mobile.matches(MOBILE_REGEX);
    }

    public static boolean isValidPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean isValidNickname(String nickname) {
        return nickname.matches(NICKNAME_REGEX);
    }

    public static boolean isValidTitle(String title) {
        return title.length() <= MAX_TITLE_LENGTH;
    }

    public static boolean isValidContent(String content) {
        return content.length() <= MAX_CONTENT_LENGTH;
    }

}
